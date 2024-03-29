package com.example.restaurants;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvList;
    MyAdapter myAdapter;
    ArrayList<RestaurantInfo> list;
    Button btnAddNewContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        rvList.setHasFixedSize(true);
        myAdapter = new MyAdapter(list);
        myAdapter.sortListByRatingDescending(); // Sort the list before setting the adapter
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(myAdapter);

        btnAddNewContact.setOnClickListener(v -> {
            list.add(new RestaurantInfo("Restaurant X", "Desc Pending", "Unknown", "No Phone", "No Rating"));
            Toast.makeText(MainActivity.this, ""+list.size(), Toast.LENGTH_SHORT).show();
            myAdapter.notifyDataSetChanged();
        });

    }

    private void init()
    {
        list = new ArrayList<>();
        //public RestaurantInfo(String name, String desc, String loc, String phone, String rating, int count)
        list.add(new RestaurantInfo("Restaurant 1", "Fast Food 24/7", "Lahore", "03332361912", "4.5"));
        list.add(new RestaurantInfo("Restaurant 2", "Desi", "Lahore", "03332861912", "4.2"));
        list.add(new RestaurantInfo("Restaurant 3", "Reservations Only", "Karachi", "03338361912", "4.9"));
        list.add(new RestaurantInfo("Restaurant 4", "Chinese", "Islamabad", "03332836912", "3.6"));
        list.add(new RestaurantInfo("Restaurant 5", "Noodle Shop", "Karachi", "03332836191", "2.0"));
        list.add(new RestaurantInfo("Restaurant 6", "Cafe", "Islamabad", "03332861912", "1.9"));
        list.add(new RestaurantInfo("Restaurant 7", "Coffee and Tea", "Islamabad", "03338361912", "3.5"));
        list.add(new RestaurantInfo("Restaurant 8", "Fast Food", "Karachi", "03332361912", "4.5"));
        list.add(new RestaurantInfo("Restaurant 9", "Fast Food 24/7", "Islamabad", "03328361912", "2.5"));
        list.add(new RestaurantInfo("Restaurant 10", "Chinese", "Lahore", "03332361912", "4.1"));

        rvList = findViewById(R.id.rvList);
        btnAddNewContact = findViewById(R.id.btnAddNewRestaurant);

    }
}