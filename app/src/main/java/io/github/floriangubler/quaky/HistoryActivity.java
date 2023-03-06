package io.github.floriangubler.quaky;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import io.github.floriangubler.quaky.entity.Earthquake;
import io.github.floriangubler.quaky.service.EveryEarthquakeAPIService;

public class HistoryActivity extends AppCompatActivity {

    EditText startInput;
    EditText limitInput;
    EditText startDateInput;
    EditText endDateInput;

    LinearLayout resultContainer;

    EveryEarthquakeAPIService apiService;
    boolean apiServiceBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        startInput = findViewById(R.id.searchStartInput);
        limitInput = findViewById(R.id.searchLimitInput);
        startDateInput = findViewById(R.id.searchStartDateInput);
        endDateInput = findViewById(R.id.searchEndDateInput);
        resultContainer = findViewById(R.id.resultContainer);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Bind services
        // Bind to API Service
        Intent connIntent = new Intent(this, EveryEarthquakeAPIService.class);
        bindService(connIntent, APIconnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(APIconnection);
        apiServiceBound = false;
    }

    /* Method used by search submit button */
    public void submitSearch(View view) throws Exception {
        if(apiServiceBound){
            ValidationState state = validateSearch();
            Earthquake[] result;
            if(state == ValidationState.SEARCH_NORMAL){
                result = apiService.search(Integer.parseInt(startInput.getText().toString()), Integer.parseInt(limitInput.getText().toString()));
            } else if(state == ValidationState.SEARCH_DATE){
                result = apiService.search(Integer.parseInt(startInput.getText().toString()), Integer.parseInt(limitInput.getText().toString()));
            } else{
                throw new Exception("Input validation failed");
            }
            try{
                showResults(result);
            } catch (JsonProcessingException e){
                Toast.makeText(this, "Could not show data on screen, see logs for detail", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } else{
            Toast.makeText(this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
            throw new Exception("API Service not bound");
        }
    }

    private void showResults(Earthquake[] result) throws JsonProcessingException {
        //Clear result
        resultContainer.removeAllViews();
        //Show new results
        for(Earthquake earthquake : result){
            createEarthquakeElement(earthquake);
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void createEarthquakeElement(Earthquake earthquake){
        //Create container
        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams lpContainer = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 100);
        lpContainer.setMargins(0, 0, 0, 30);
        container.setLayoutParams(lpContainer);
        container.setGravity(Gravity.CENTER);
        container.setBackground(getResources().getDrawable(R.drawable.border));
        container.setPadding(10, 10, 10, 10);

        //Create date text
        TextView dateText = new TextView(this);
        dateText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        dateText.setText(earthquake.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
        LinearLayout.LayoutParams lpDateText = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 3);
        dateText.setLayoutParams(lpDateText);
        dateText.setPadding(2, 2, 2, 2);
        container.addView(dateText);

        //Create date text
        TextView locationText = new TextView(this);
        locationText.setText(earthquake.getLocation());
        locationText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        locationText.setBackground(getResources().getDrawable(R.drawable.border));
        LinearLayout.LayoutParams lpLocationText = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 2);
        locationText.setLayoutParams(lpLocationText);
        locationText.setPadding(2, 2, 2, 2);
        locationText.setTypeface(locationText.getTypeface(), Typeface.BOLD);
        container.addView(locationText);

        container.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                try{
                    Intent intent = new Intent(HistoryActivity.this, DetailActivity.class);
                    intent.putExtra("data", objectMapper.writeValueAsString(earthquake));
                    startActivity(intent);
                } catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Could not load data from clicked earthquake, see logs for details", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        resultContainer.addView(container);
    }

    /* Validate search inputs and calculate search type */
    private ValidationState validateSearch() {
        if(!startInput.getText().toString().isEmpty()
        && !limitInput.getText().toString().isEmpty()
        && !startDateInput.getText().toString().isEmpty()
        && !endDateInput.getText().toString().isEmpty()){
            return ValidationState.SEARCH_DATE;
        } else if(!startInput.getText().toString().isEmpty()
                && !limitInput.getText().toString().isEmpty()){
            return ValidationState.SEARCH_NORMAL;
        } else{
            return ValidationState.INVALID;
        }
    }

    /* Enum for validation state */
    private enum ValidationState{
        INVALID,
        SEARCH_NORMAL,
        SEARCH_DATE;
    }

    /* API Service connection for binding the service */
    ServiceConnection APIconnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            EveryEarthquakeAPIService.LocalBinder binder = (EveryEarthquakeAPIService.LocalBinder) service;
            apiService = binder.getService();
            apiServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            apiServiceBound = false;
        }
    };
}