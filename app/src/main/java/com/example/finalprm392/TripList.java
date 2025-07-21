package com.example.finalprm392;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalprm392.Adapter.TripAdapter;
import com.example.finalprm392.DAO.TripDAO;
import com.example.finalprm392.Database.RoomDB;
import com.example.finalprm392.Models.Trip;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class TripList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TripAdapter adapter;
    private TripDAO tripDAO;
    private List<Trip> tripList;

    Button btnBag, btnTrip, btnCreateTrip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);
        getSupportActionBar().hide();

        btnBag = findViewById(R.id.btn_bag2);
        btnTrip = findViewById(R.id.btn_trip2);
        btnCreateTrip = findViewById(R.id.btn_create_trip);
        btnBag.setEnabled(true);
        btnTrip.setEnabled(false);

        btnBag.setOnClickListener(v -> {
            Intent intent = new Intent(TripList.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        recyclerView = findViewById(R.id.recyclerTrip);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tripDAO = RoomDB.getInstance(this).tripDAO();

        loadTrips();

        btnCreateTrip.setOnClickListener(v -> {
            Intent intent = new Intent(TripList.this, CreateTripActivity.class);
            startActivity(intent);
        });
    }


    private void loadTrips() {
        new Thread(() -> {
            tripList = tripDAO.getAllTrips();
            runOnUiThread(() -> {
                adapter = new TripAdapter(this, tripList, tripDAO, this::loadTrips);
                recyclerView.setAdapter(adapter);
            });
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTrips();
    }
}