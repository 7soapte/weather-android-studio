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

    default void readJson(Context context, HashMap<String, String[]> localities) {
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

}
