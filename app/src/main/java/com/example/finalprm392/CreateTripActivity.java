package com.example.finalprm392;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalprm392.Constants.MyConstants;
import com.example.finalprm392.Database.RoomDB;
import com.example.finalprm392.Models.Items;
import com.example.finalprm392.Models.Trip;
import com.example.finalprm392.Models.TripItems;

import java.time.LocalDate;
import java.util.List;

public class CreateTripActivity extends AppCompatActivity {

    private Button btnPickDate, btnCreateTrip;
    private TextView tvSelectedDate;
    private RadioGroup radioGroup;
    private LocalDate selectedDate = null;
    private RoomDB db;
    private EditText etTripName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);
        getSupportActionBar().hide();

        btnPickDate = findViewById(R.id.btn_pick_date);
        btnCreateTrip = findViewById(R.id.btn_create_trip);
        tvSelectedDate = findViewById(R.id.tv_selected_date);
        radioGroup = findViewById(R.id.radio_bag_group);
        etTripName = findViewById(R.id.et_trip_name);
        db = RoomDB.getInstance(this);

        btnPickDate.setOnClickListener(v -> {
            showDatePickerDialog();
        });

        btnCreateTrip.setOnClickListener(v -> {
            if (selectedDate == null) {
                Toast.makeText(this, "Please select a departure date", Toast.LENGTH_SHORT).show();
                return;
            }

            int selectedId = radioGroup.getCheckedRadioButtonId();
            String bagType = (selectedId == R.id.radio_my_list) ?
                    MyConstants.MY_LIST_CAMEL_CASE : MyConstants.MY_SELECTIONS_CAMEL_CASE;

            List<Items> items = db.mainDAO().getAll(bagType);

            String tripName = etTripName.getText().toString().trim();
            if (tripName.isEmpty()) {
                Toast.makeText(this, "Please enter a trip name", Toast.LENGTH_SHORT).show();
                return;
            }

            Trip trip = new Trip();
            trip.setName(tripName);
            trip.setDate(selectedDate);
            long tripId = db.tripDAO().insertTrip(trip);

            for (Items item : items) {
                TripItems ti = new TripItems();
                ti.setTripId((int) tripId);
                ti.setItemId(item.getId());
                ti.setPacked(false);
                db.tripItemsDAO().insert(ti);
            }

            Toast.makeText(this, "Trip created!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void showDatePickerDialog() {
        LocalDate now = LocalDate.now();
        DatePickerDialog dialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    selectedDate = LocalDate.of(year, month + 1, dayOfMonth);
                    tvSelectedDate.setText("Departure: " + selectedDate.toString());
                },
                now.getYear(), now.getMonthValue() - 1, now.getDayOfMonth());

        dialog.show();
    }
}