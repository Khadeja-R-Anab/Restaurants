package com.example.restaurants;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvList;
    MyAdapter myAdapter;
    Button btnAddNew, btnExit;

    ArrayList<RestaurantInfo> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        // Initialize the list
        list = new ArrayList<>();

        // Retrieve restaurant data from SharedPreferences and sort by rating
        retrieveAndSortRestaurantData();

        // Create and set adapter after retrieving and sorting data
        myAdapter = new MyAdapter(list);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(myAdapter);

        btnAddNew.setOnClickListener(v -> {
            Toast.makeText(this, "Add Button Clicked", Toast.LENGTH_SHORT).show();
            // Start RegistrationActivity to add a new restaurant
            Intent intent = new Intent(MainActivity.this, Registration.class);
            startActivity(intent);

        });
    }

    // Method to retrieve restaurant data from SharedPreferences and sort by rating
    private void retrieveAndSortRestaurantData() {
        list.clear(); // Clear the list before retrieving data
        SharedPreferences sharedPreferences = getSharedPreferences("restaurant_prefs", MODE_PRIVATE);

        //retrieves all key-value pairs stored in the sharedPreferences and stores them in a Map. key is string, value is not known
        Map<String, ?> allEntries = sharedPreferences.getAll();

        //add all enteirs in the map to the list
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String restaurantJson = entry.getValue().toString();
            //Deserializes the JSON string restaurantJson into a RestaurantInfo object using Gson.
            RestaurantInfo restaurant = new Gson().fromJson(restaurantJson, RestaurantInfo.class);
            list.add(restaurant);
        }

        // Sort the list by rating in descending order
        Collections.sort(list, (restaurant1, restaurant2) -> {
            String ratingStr1 = restaurant1.getRating();
            String ratingStr2 = restaurant2.getRating();

            // Handle null or empty ratings
            if (ratingStr1 == null || ratingStr1.isEmpty()) {
                return 1; // Move null/empty ratings to the end
            }
            if (ratingStr2 == null || ratingStr2.isEmpty()) {
                return -1; // Move null/empty ratings to the end
            }

            // Parse ratings to float and compare
            float rating1 = Float.parseFloat(ratingStr1);
            float rating2 = Float.parseFloat(ratingStr2);
            return Float.compare(rating2, rating1);
        });

    }

    // Initialize views and buttons
    private void initViews() {
        rvList = findViewById(R.id.rvList);
        btnAddNew = findViewById(R.id.btnAddNewRestaurant);
    }
}
