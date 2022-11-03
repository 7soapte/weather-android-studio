package com.example.weatherproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements Utilities {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // load localities variable with local json content
        initLocalities(getApplicationContext());

        // load weatherDictionary variable with local json content
        initWeatherDictionary(getApplicationContext());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                localities.keySet().toArray(new String[localities.size()])
        );
        AutoCompleteTextView textView = findViewById(R.id.autoComplete);
        textView.setAdapter(adapter);

        Button grabWeather = findViewById(R.id.grabWeather);
        AutoCompleteTextView autoComplete = findViewById(R.id.autoComplete);

        grabWeather.setOnClickListener(view -> {
            String inputText = autoComplete.getText().toString();

            if (localities.keySet().contains(inputText)) {
                openCardView();
                Toast.makeText(this, weatherDictionary.get("61"), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, weatherDictionary.get("61"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openCardView() {
        Intent intent = new Intent(this, WeatherCard.class);
        startActivity(intent);
    }
}