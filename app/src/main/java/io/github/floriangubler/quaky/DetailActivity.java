package io.github.floriangubler.quaky;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

import io.github.floriangubler.quaky.entity.Earthquake;
import io.github.floriangubler.quaky.entity.LocationDetail;

public class DetailActivity extends AppCompatActivity {

    Earthquake earthquake;

    TextView titleText;
    LinearLayout detailContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        titleText = findViewById(R.id.detailTitle);
        titleText.setMovementMethod(LinkMovementMethod.getInstance());
        detailContainer = findViewById(R.id.detailContainer);

        Intent intent = getIntent();
        String jsonEarthquake = intent.getStringExtra("data");
        if(jsonEarthquake == null || jsonEarthquake.isEmpty()) {
            Toast.makeText(this, "No data given", Toast.LENGTH_LONG).show();
        } else{
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            try {
                earthquake = objectMapper.readValue(jsonEarthquake, Earthquake.class);

                showEarthquake();
            } catch (JsonProcessingException e) {
                Toast.makeText(this, "Could not load last earthquake, see logs for details", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (IllegalAccessException e1){
                Toast.makeText(this, "Could not load earthquake data, see logs for details", Toast.LENGTH_SHORT).show();
                e1.printStackTrace();
            }
        }
    }

    private void showEarthquake() throws IllegalAccessException {
        titleText.setText(Html.fromHtml(getResources().getText(R.string.link_template).toString()
                .replace("%%TEXT%%", earthquake.getTitle())
                .replace("%%LINK%%", earthquake.getUrl()), Html.FROM_HTML_MODE_LEGACY));
        magicFieldLoader(earthquake);
    }

    private void magicFieldLoader(Object o) throws IllegalAccessException {
        //Create all UI Objects dynamically
        for(Field field : o.getClass().getDeclaredFields()){
            field.setAccessible(true);
            if(field.getType() == List.class){
                List l = (List) field.get(o);
                TextView textView = createTextView(field.getName());
                textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
                detailContainer.addView(textView);
                for(Object ol : l){
                    magicFieldLoader(ol);
                    detailContainer.addView(createTextView("--------"));
                }
            } else{
                detailContainer.addView(createTextView(field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1) + ": " + field.get(o)));
            }
        }
    }

    private TextView createTextView(String text){
        TextView textView = new TextView(this);
        textView.setText(text);
        return textView;
    }
}

