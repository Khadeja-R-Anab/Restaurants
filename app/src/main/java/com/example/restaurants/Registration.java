package com.example.restaurants;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

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
            String name = tvName.getText().toString();
            String desc = tvDescription.getText().toString();
            String phone = tvPhone.getText().toString();
            String loc = tvLocation.getText().toString();
            String ratingStr = tvRating.getText().toString();

            if (name.isEmpty() || desc.isEmpty() || phone.isEmpty() || loc.isEmpty() || ratingStr.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check if the rating is within the valid range (0.0 to 5.0)
            float rating = Float.parseFloat(ratingStr);
            if (rating < 0.0 || rating > 5.0) {
                Toast.makeText(this, "Rating must be between 0.0 and 5.0", Toast.LENGTH_SHORT).show();
                return;
            }

            // Save restaurant information
            RestaurantInfo restaurant = new RestaurantInfo();
            restaurant.setName(name);
            restaurant.setDesc(desc);
            restaurant.setPhone(phone);
            restaurant.setLoc(loc);
            restaurant.setRating(ratingStr);
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