package com.example.weatherproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements Utilities {

    // We make a hashmap so we can access the coordinates of a given locality
    // Hashmap skeleton: "Bucuresti":[lat,lng], "Timisoare":[lat,lng]
    HashMap<String, String[]> localities = new HashMap<>();
    TextView myElem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myElem = findViewById(R.id.textElem);

        readJson(getApplicationContext(), localities);

        myElem.setText(localities.get("Timişoara")[0]);
    }
}