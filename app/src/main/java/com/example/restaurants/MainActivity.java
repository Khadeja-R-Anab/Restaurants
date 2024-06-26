package com.example.restaurants;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvList;
    MyAdapter myAdapter, filteredAdapter;
    Button btnAddNew;
    EditText etSearch;
    TextView tvNoResults;
    ArrayList<RestaurantInfo> list;
    ArrayList<RestaurantInfo> filteredList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        // Initialize the list
        list = new ArrayList<>();
        filteredList = new ArrayList<>();

        // Retrieve restaurant data from SharedPreferences and sort by rating
        retrieveAndSortRestaurantData();

        // Create and set adapter after retrieving and sorting data
        myAdapter = new MyAdapter(list);
        filteredAdapter = new MyAdapter(filteredList);

        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(myAdapter);

        btnAddNew.setOnClickListener(v -> {
            Toast.makeText(this, "Add Button Clicked", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, Registration.class);
            startActivity(intent);

        });

        // Add a text change listener to the search bar
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterAndSortRestaurants(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    // Storing the filtered list in a separate SharedPreferences file
    private void retrieveAndSortFilteredData() {
        filteredList.clear(); // Clear the list before retrieving data
        SharedPreferences sharedPreferences = getSharedPreferences("filtered_prefs", MODE_PRIVATE);

        // Retrieve the JSON string for the filtered list
        String filteredListJson = sharedPreferences.getString("filteredList", "");

        // Deserialize the JSON array string into a list of RestaurantInfo objects
        RestaurantInfo[] restaurantArray = new Gson().fromJson(filteredListJson, RestaurantInfo[].class);
        if (restaurantArray != null) {
            filteredList.addAll(Arrays.asList(restaurantArray));
        }

        // Sort the list by rating in descending order
        filteredList.sort((restaurant1, restaurant2) -> {
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


    // Clearing the filtered list from SharedPreferences
    private void clearFilteredList() {
        SharedPreferences sharedPreferences = getSharedPreferences("filtered_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        filteredList.clear();
    }

    private void storeFilteredList(List<RestaurantInfo> filteredList) {
        SharedPreferences sharedPref = getSharedPreferences("filtered_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        // Convert filteredList to JSON array using Gson
        String filteredListJson = new Gson().toJson(filteredList);

        // Store the JSON array string in SharedPreferences
        editor.putString("filteredList", filteredListJson);
        editor.apply();
    }

    //filterAndSortRestaurants method to use the SharedPreferences methods
    private void filterAndSortRestaurants(String searchText) {
        // Clear the filtered list
        clearFilteredList();

        // Check if the search text is empty or null
        if (searchText == null || searchText.isEmpty()) {
            rvList.setAdapter(myAdapter);
            tvNoResults.setVisibility(View.GONE); // Hide the TextView
        } else {
            // Filter restaurants based on search text
            for (RestaurantInfo restaurant : list) {
                if (restaurant.getName().trim().toLowerCase().contains(searchText.trim().toLowerCase())) {
                    filteredList.add(restaurant);
                }
            }

            if (filteredList.isEmpty()) {
                tvNoResults.setVisibility(View.VISIBLE); // Show the TextView
                rvList.setAdapter(null); // Clear RecyclerView adapter
            } else {
                tvNoResults.setVisibility(View.GONE); // Hide the TextView
                storeFilteredList(filteredList);
                retrieveAndSortFilteredData();
                rvList.setAdapter(filteredAdapter);
            }
        }
    }


    // Method to retrieve restaurant data from SharedPreferences and sort by rating
    private void retrieveAndSortRestaurantData() {
        list.clear(); // Clear the list before retrieving data
        SharedPreferences sharedPreferences = getSharedPreferences("restaurant_prefs", MODE_PRIVATE);

        //retrieves all key-value pairs stored in the sharedPreferences and stores them in a Map. key is string, value is not known
        Map<String, ?> allEntries = sharedPreferences.getAll();

        //add all entries in the map to the list
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String restaurantJson = entry.getValue().toString();
            //Deserializes the JSON string restaurantJson into a RestaurantInfo object using Gson.
            RestaurantInfo restaurant = new Gson().fromJson(restaurantJson, RestaurantInfo.class);
            list.add(restaurant);
        }

        // Sort the list by rating in descending order
        list.sort((restaurant1, restaurant2) -> {
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
        etSearch = findViewById(R.id.etSearch);
        rvList = findViewById(R.id.rvList);
        btnAddNew = findViewById(R.id.btnAddNewRestaurant);
        tvNoResults = findViewById(R.id.tvNoResults);
    }
}
