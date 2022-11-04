package com.example.weatherproject;

import androidx.annotation.NonNull;

public class DayItem {
    private String futureDay;
    private String futureMinMax;
    private int img;

    public DayItem(String temperature, String day, int img) {
        this.futureDay = day;
        this.futureMinMax = temperature;
        this.img = img;
    }

    public String getFutureDay() {
        return futureDay;
    }

    public void setFutureDay(String futureDay) {
        this.futureDay = futureDay;
    }

    public String getFutureMinMax() {
        return futureMinMax;
    }

    public void setFutureMinMax(String futureMinMax) {
        this.futureMinMax = futureMinMax;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    @NonNull
    @Override
    public String toString() {
        return "DayItem{" +
                "futureDay='" + futureDay + '\'' +
                ", futureMinMax='" + futureMinMax + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
