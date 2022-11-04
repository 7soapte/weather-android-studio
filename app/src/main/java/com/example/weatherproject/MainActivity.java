package com.example.weatherproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

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

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                localities.keySet().toArray(new String[0])
        );
        AutoCompleteTextView autoComplete = findViewById(R.id.autoComplete);
        autoComplete.setAdapter(adapter);

        Button grabWeather = findViewById(R.id.grabWeather);
        TextView errMsg = findViewById(R.id.errMsg);

        grabWeather.setOnClickListener(view -> {
            String inputText = autoComplete.getText().toString();

            if (localities.containsKey(inputText)) {
                errMsg.setText("");
                autoComplete.setText("");
                openCardView();
            } else {
                errMsg.setText("Localitatea introdusa nu exista, te rugam introdu o localitate valida.");
            }
        });
    }

    public void openCardView() {
        Intent intent = new Intent(this, WeatherCard.class);
        startActivity(intent);
    }
}