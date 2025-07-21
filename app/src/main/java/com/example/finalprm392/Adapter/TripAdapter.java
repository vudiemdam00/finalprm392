package com.example.finalprm392.Adapter;
import com.example.finalprm392.CheckList;
import com.example.finalprm392.DAO.TripDAO;
import com.example.finalprm392.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalprm392.Models.Trip;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> {
    private Context context;
    private List<Trip> tripList;
    private TripDAO tripDAO;
    private OnTripDeletedListener deleteListener;

    public interface OnTripDeletedListener {
        void onTripDeleted(); // Callback để Activity reload danh sách
    }

    public TripAdapter(Context context, List<Trip> tripList, TripDAO tripDAO, OnTripDeletedListener deleteListener) {
        this.context = context;
        this.tripList = tripList;
        this.tripDAO = tripDAO;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public TripAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_trip, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripAdapter.ViewHolder holder, int position) {
        Trip trip = tripList.get(position);
        holder.tvTripName.setText(trip.getName());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault());
        holder.tvTripDate.setText("Khởi hành: " + trip.getDate().format(formatter));

        // 👉 Sự kiện click để chuyển sang CheckListActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CheckList.class);
            intent.putExtra("tripId", trip.getId()); // truyền ID hoặc đối tượng Trip nếu cần
            context.startActivity(intent);
        });

        // 👉 Xử lý nút xóa
        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xóa chuyến đi")
                    .setMessage("Bạn có chắc chắn muốn xóa chuyến đi này?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        new Thread(() -> {
                            tripDAO.deleteTrip(trip);
                            if (deleteListener != null) {
                                ((Activity) context).runOnUiThread(deleteListener::onTripDeleted);
                            }
                        }).start();
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTripName, tvTripDate;
        ImageButton btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTripName = itemView.findViewById(R.id.tvTripName);
            tvTripDate = itemView.findViewById(R.id.tvTripDate);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}

