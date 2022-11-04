package com.example.weatherproject;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

public class WeatherCard extends AppCompatActivity implements Utilities {
    String[] httpCoords;
    private RecyclerView daysRecView;
    private ImageView image;
    private TextView maxTemp;
    private TextView currentDay;
    private TextView minTemp;
    private TextView weatherStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_weather_card);
        image = findViewById(R.id.weatherImg);
        maxTemp = findViewById(R.id.maxTemp);
        minTemp = findViewById(R.id.minTemp);
        currentDay = findViewById(R.id.currentDay);
        weatherStatus = findViewById(R.id.weatherStatus);
        ImageButton backBtn = findViewById(R.id.backBtn);
        daysRecView = findViewById(R.id.weatherDaysRecView);

        Intent intent = getIntent();
        httpCoords = intent.getStringArrayExtra("coords");
        
        new HTTPReqTask().execute();

        backBtn.setOnClickListener(view -> openCardView());
    }

    private void interpretResponse(String res) throws JSONException {
        JSONObject container = new JSONObject(res);
        String suffixMax = container.getJSONObject("daily_units").getString("temperature_2m_max");
        String suffixMin = container.getJSONObject("daily_units").getString("temperature_2m_min");

        HashMap<String, JSONArray> daily = new HashMap<>();

        daily.put("time", container.getJSONObject("daily").getJSONArray("time"));
        daily.put("weathercode", container.getJSONObject("daily").getJSONArray("weathercode"));
        daily.put("temp_max", container.getJSONObject("daily").getJSONArray("temperature_2m_max"));
        daily.put("temp_min", container.getJSONObject("daily").getJSONArray("temperature_2m_min"));

        // current status
        String currentMinTemperature = daily.get("temp_min").getString(0);
        String currentMaxTemperature = daily.get("temp_max").getString(0);

        // grab dynamic image from our weatherDictionary
        String dynamicIcon = weatherDictionary.get(daily.get("weathercode").get(0).toString()).get("icon");

        // grab status from our weatherDictionary
        String dynamicStatus = weatherDictionary.get(daily.get("weathercode").get(0).toString()).get("text");

        runOnUiThread(() -> {
            currentDay.setText("Astazi");
            minTemp.setText(String.format("%s %s", currentMinTemperature, suffixMin));
            maxTemp.setText(String.format("%s %s", currentMaxTemperature, suffixMax));
            weatherStatus.setText(dynamicStatus);

            //set out recyclerview for the future days
            // grab data we need and populate our ArraList for our adapter
            ArrayList<DayItem> days = new ArrayList<>();

            // skip out first item aka our current item
            for (int i = 1; i < daily.get("temp_min").length(); i++) {
                // Adding to our DayItem class
                // String minTemp, String maxTemp, String day, String img
                try {
                    String forMinTemp = daily.get("temp_min").getString(i);
                    String forMaxTemp = daily.get("temp_max").getString(i);
                    String forDay = daily.get("time").getString(i);
                    String forImg = weatherDictionary.get(daily.get("weathercode").get(i).toString()).get("icon");
                    int forImgId = WeatherCard.this.getResources().getIdentifier(forImg, "drawable", getPackageName());
                    String finalTemp = "Min " + forMaxTemp + " " + suffixMax + " Max " + forMinTemp + " " + suffixMin;

                    days.add(new DayItem(finalTemp, forDay, forImgId));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                DaysRecyclerViewAdapter adapter = new DaysRecyclerViewAdapter();
                adapter.setFutureDays(days);

                daysRecView.setAdapter(adapter);
                daysRecView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            }
            // end future days

            // init dynamic gif and animate it
            int id = WeatherCard.this.getResources().getIdentifier(dynamicIcon, "drawable", getPackageName());
            image.setImageResource(id);

            Drawable d = image.getDrawable();
            if (d instanceof AnimatedVectorDrawable) {
                AnimatedVectorDrawable animation = (AnimatedVectorDrawable) d;
                animation.start();
            }
        });

    }

    public class HTTPReqTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;

            // GET
            try {
                URL url = new URL("https://api.open-meteo.com/v1/forecast?latitude=" + httpCoords[0] + "&longitude=" + httpCoords[1] + "&daily=weathercode,temperature_2m_max,temperature_2m_min&timezone=Europe%2FBucharest");
                urlConnection = (HttpURLConnection) url.openConnection();

                int code = urlConnection.getResponseCode();
                if (code != 200) {
                    throw new IOException("Invalid response from server: " + code);
                }

                BufferedReader rd = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream()));

                StringBuilder total = new StringBuilder();
                for (String line; (line = rd.readLine()) != null; ) {
                    total.append(line).append('\n');
                }

                interpretResponse(total.toString());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }
    }

    public void openCardView() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}