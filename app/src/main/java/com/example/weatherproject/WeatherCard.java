package com.example.weatherproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

public class WeatherCard extends AppCompatActivity {
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_card);
        image = findViewById(R.id.weatherImg);

        // weatherDictionary.get("61").get("text")
        // weatherDictionary.get("61").get("icon")
    }

    @Override
    protected void onStart() {
        super.onStart();
        Drawable d = image.getDrawable();
        if (d instanceof AnimatedVectorDrawable) {
            AnimatedVectorDrawable animation = (AnimatedVectorDrawable) d;
            animation.start();
        }
    }
}