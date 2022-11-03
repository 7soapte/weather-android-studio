package com.example.weatherproject;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

interface Utilities {
    // We make a hashmap so we can access the coordinates of a given locality
    // Hashmap skeleton: "Bucuresti":[lat,lng], "Timisoare":[lat,lng]
    HashMap<String, String[]> localities = new HashMap<>();

    // Make hasmap to interpret API weather codes
    // EX: "1": "Partial noros"
    HashMap<String, String> weatherDictionary = new HashMap<>();

    default void initLocalities(Context context) {
        String json;

        try {
            InputStream is = context.getAssets().open("ro.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                localities.put(obj.getString("city"), new String[]{obj.getString("lat"), obj.getString("lng")});
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    default void initWeatherDictionary(Context context) {
        String json;

        try {
            InputStream is = context.getAssets().open("weather_codes.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                JSONArray code = obj.getJSONArray("code");

                for (int j = 0; j < code.length(); j++) {
                    weatherDictionary.put(code.getString(j), obj.getString("text"));
                }
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

}
