package io.github.floriangubler.quaky;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /* OnClick history link */
    public void goHistory(View view){
        Intent myIntent=new Intent(MainActivity.this, HistoryActivity.class);
        startActivity(myIntent);
    }

    /* OnClick last earthquake link */
    public void goDetail(View view){
        Intent myIntent = new Intent(MainActivity.this, DetailActivity.class);
        startActivity(myIntent);
    }
}