package com.example.finalprm392.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalprm392.Constants.MyConstants;
import com.example.finalprm392.Database.RoomDB;
import com.example.finalprm392.Models.Items;
import com.example.finalprm392.R;

import java.util.List;

public class CheckListAdapter extends RecyclerView.Adapter<CheckListViewHolder> {

    Context context;
    List<Items> itemsList;
    RoomDB database;
    String show;

    public CheckListAdapter() {}

    public CheckListAdapter(Context context, List<Items> itemsList, RoomDB database, String show) {
        this.context = context;
        this.itemsList = itemsList;
        this.database = database;
        this.show = show;
        if (itemsList.size() == 0)
            Toast.makeText(context.getApplicationContext(), "Nothing to show", Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public CheckListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CheckListViewHolder(LayoutInflater.from(context).inflate(R.layout.check_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CheckListViewHolder holder, int position) {
        holder.checkBox.setText(itemsList.get(position).getItemname());
        holder.checkBox.setChecked(itemsList.get(position).getChecked());

        if (MyConstants.FALSE_STRING.equals(show)) {
            holder.btnDelete.setVisibility(View.GONE);
            holder.layout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.border_one));
        } else {
            if (itemsList.get(position).getChecked()) {
                holder.layout.setBackgroundColor(Color.parseColor("#8e546f"));
            } else {
                holder.layout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.border_one));
            }
        }

        holder.checkBox.setOnClickListener(view -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                boolean check = holder.checkBox.isChecked();
                database.mainDAO().checkUncheck(itemsList.get(adapterPosition).getId(), check);

                if (MyConstants.FALSE_STRING.equals(show)) {
                    itemsList = database.mainDAO().getAllSelected(true);
                    notifyDataSetChanged();
                } else {
                    itemsList.get(adapterPosition).setChecked(check);
                    notifyDataSetChanged();
                    String msg = check
                            ? "(" + holder.checkBox.getText() + ") Packed"
                            : "(" + holder.checkBox.getText() + ") Un-Packed";
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.btnDelete.setOnClickListener(view -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete (" + itemsList.get(adapterPosition).getItemname() + ")")
                        .setMessage("Are you sure ?")
                        .setPositiveButton("Confirm", (dialogInterface, i) -> {
                            database.mainDAO().delete(itemsList.get(adapterPosition));
                            itemsList.remove(adapterPosition);
                            notifyDataSetChanged();
                        })
                        .setNegativeButton("Cancel", (dialogInterface, i) ->
                                Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show())
                        .setIcon(R.drawable.ic_delete)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }
}

class CheckListViewHolder extends RecyclerView.ViewHolder {

    LinearLayout layout;
    CheckBox checkBox;
    ImageButton btnDelete;

    public CheckListViewHolder(@NonNull View itemView) {
        super(itemView);
        layout = itemView.findViewById(R.id.linearLayout);
        checkBox = itemView.findViewById(R.id.checkbox);
        btnDelete = itemView.findViewById(R.id.btnDelete);
    }
}
