package com.example.weatherproject;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DaysRecyclerViewAdapter extends RecyclerView.Adapter<DaysRecyclerViewAdapter.ViewHolder>{

    private ArrayList<DayItem> futureDays = new ArrayList<>();

    public DaysRecyclerViewAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_day_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.futureDay.setText(futureDays.get(position).getFutureDay());
        holder.futureMinMax.setText(futureDays.get(position).getFutureMinMax());
        holder.recyclerImg.setImageResource(futureDays.get(position).getImg());



        Drawable d = holder.recyclerImg.getDrawable();
        if (d instanceof AnimatedVectorDrawable) {
            AnimatedVectorDrawable animation = (AnimatedVectorDrawable) d;
            animation.start();
        }

    }

    @Override
    public int getItemCount() {
        return futureDays.size();
    }

    public void setFutureDays(ArrayList<DayItem> futureDays) {
        this.futureDays = futureDays;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView futureDay;
        private final TextView futureMinMax;
        private final ImageView recyclerImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            futureDay = itemView.findViewById(R.id.futureDay);
            futureMinMax = itemView.findViewById(R.id.futureMinMax);
            recyclerImg = itemView.findViewById(R.id.recyclerImg);
        }
    }
}
