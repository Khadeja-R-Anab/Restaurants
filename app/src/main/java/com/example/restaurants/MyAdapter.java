package com.example.restaurants;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    ArrayList<RestaurantInfo> restaurants;
    public MyAdapter(ArrayList<RestaurantInfo> list)
    {
        restaurants = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int restaurants) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.single_item, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(restaurants.get(position).getName());
        holder.tvRating.setText(restaurants.get(position).getRating());
        holder.tvDescription.setText(restaurants.get(position).getDesc());
        holder.tvLocation.setText(restaurants.get(position).getLoc());
        holder.tvPhone.setText(restaurants.get(position).getPhone());
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public void sortListByRatingDescending() {
        Collections.sort(restaurants, (restaurant1, restaurant2) -> {
            // Convert string ratings to floats for comparison
            float rating1 = Float.parseFloat(restaurant1.getRating());
            float rating2 = Float.parseFloat(restaurant2.getRating());
            // Sort in descending order
            return Float.compare(rating2, rating1);
        });
        notifyDataSetChanged(); // Notify adapter that data has changed
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvRating, tvName, tvCount, tvDescription, tvLocation, tvPhone;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvName = itemView.findViewById(R.id.tvName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), tvName.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}