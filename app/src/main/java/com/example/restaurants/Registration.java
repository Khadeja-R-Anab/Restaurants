package com.example.restaurants;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Registration extends AppCompatActivity {

    Button btnAdd, btnCancel;

    EditText tvName, tvDescription, tvPhone, tvLocation, tvRating;

    private void saveRestaurantInfo(RestaurantInfo r) {
        SharedPreferences sharedPreferences = getSharedPreferences("restaurant_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Generate a unique key for each restaurant using current timestamp
        String key = String.valueOf(System.currentTimeMillis());
        String restaurantJson = new Gson().toJson(r);

        // Save restaurant information using unique key
        editor.putString(key, restaurantJson);
        editor.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        init();

        // Method to handle saving restaurant information
        btnAdd.setOnClickListener(v -> {
            // Capture restaurant information from UI
            RestaurantInfo restaurant = new RestaurantInfo();
            restaurant.setName(tvName.getText().toString());
            restaurant.setDesc(tvDescription.getText().toString());
            restaurant.setPhone(tvPhone.getText().toString());
            restaurant.setLoc(tvLocation.getText().toString());
            restaurant.setRating(tvRating.getText().toString());

            // Save restaurant information
            saveRestaurantInfo(restaurant);

            Toast.makeText(this, "Added Successfully", Toast.LENGTH_SHORT).show();

            // Navigate back to MainActivity
            Intent intent = new Intent(Registration.this, MainActivity.class);
            startActivity(intent);
            finish(); // Finish current activity to prevent going back to it from MainActivity
        });

        btnCancel.setOnClickListener(v -> {
            Toast.makeText(this, "Cancel Button Clicked", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Registration.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }


    private void init() {
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);
        tvName = findViewById(R.id.etName);
        tvDescription = findViewById(R.id.etDescription);
        tvPhone = findViewById(R.id.etPhone);
        tvLocation = findViewById(R.id.etLocation);
        tvRating = findViewById(R.id.etRating);
    }
}