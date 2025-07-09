package com.example.finalprm392;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import com.example.finalprm392.Adapter.CheckListAdapter;
import com.example.finalprm392.Constants.MyConstants;
import com.example.finalprm392.Data.AppData;
import com.example.finalprm392.Database.RoomDB;
import com.example.finalprm392.Models.Items;

import java.util.ArrayList;
import java.util.List;

public class CheckList extends AppCompatActivity {

    RecyclerView recyclerView;
    CheckListAdapter checkListAdapter;
    RoomDB database;
    List<Items> itemsList = new ArrayList<>();
    String header, show;

    EditText txtAdd;
    Button  btnAdd;
    LinearLayout linearLayout;

    @Override
    public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_one, menu);

        if(MyConstants.MY_SELECTIONS.equals(header)){
            menu.getItem(0).setVisible(false);
            menu.getItem(2).setVisible(false);
            menu.getItem(3).setVisible(false);
        } else if (MyConstants.MY_LIST_CAMEL_CASE.equals(header)) {
            menu.getItem(1).setVisible(false);
        }
        MenuItem menuItem = menu.findItem(R.id.btnSearch);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Items > finalList = new ArrayList<>();
                for (Items items : itemsList){
                    if (items.getItemname().toLowerCase().startsWith(newText.toLowerCase())){
                        finalList.add(items);
                    }
                }
                updateRecycler(finalList);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent intent = new Intent(this, CheckList.class);
        AppData appData = new AppData(database, this);

        if(id == R.id.btnMySelections ){
            intent.putExtra(MyConstants.HEADER_SMALL, MyConstants.MY_SELECTIONS);
            intent.putExtra(MyConstants.SHOW_SMALL, MyConstants.FALSE_STRING);
            startActivityForResult(intent, 101);
            return true;
        } else if (id == R.id.btnCustomList) {
            intent.putExtra(MyConstants.HEADER_SMALL, MyConstants.MY_LIST_CAMEL_CASE);
            intent.putExtra(MyConstants.SHOW_SMALL, MyConstants.TRUE_STRING);
            startActivity(intent);
            return true;
        } else if (id == R.id.btnDeleteDefault) {
            new AlertDialog.Builder(this)
                    .setTitle("Delete default data")
                    .setMessage("Are you sure?\n\n" +
                            "As this will delete the data provided by (Pack your bag) while instailling")
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            appData.persistDataByCategory(header, true);
                            itemsList = database.mainDAO().getAll(header);
                            updateRecycler(itemsList);
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setIcon(R.drawable.warning).show();
            return  true;
        } else if (id ==  R.id.btnReset) {
            new AlertDialog.Builder(this)
                    .setTitle("Reset to default")
                    .setMessage("Are you sure?\n\n" +
                            "As this will load the default data provided by (Pack your bag)" +
                            "and will delete your custom data you have in ("+header+")")
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            appData.persistDataByCategory(header, false);
                            itemsList = database.mainDAO().getAll(header);
                            updateRecycler(itemsList);
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setIcon(R.drawable.warning).show();
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        header = intent.getStringExtra(MyConstants.HEADER_SMALL);
        show = intent.getStringExtra(MyConstants.SHOW_SMALL);

        getSupportActionBar().setTitle(header);

        txtAdd = findViewById(R.id.txtAdd);
        btnAdd = findViewById(R.id.btnAdd);
        recyclerView = findViewById(R.id.recyclerView);
        linearLayout = findViewById(R.id.linearLayout);

        database = RoomDB.getInstance(this);

        if(MyConstants.FALSE_STRING.equals(show)){
            linearLayout.setVisibility(View.GONE);
            itemsList = database.mainDAO().getAllSelected(true);
        }else {
            itemsList = database.mainDAO().getAll(header);
        }

        updateRecycler(itemsList);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName = txtAdd.getText().toString();
                if(itemName != null && !itemName.isEmpty()){
                    addNewItem(itemName);
                    Toast.makeText(CheckList.this, "Item Added.", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(CheckList.this, "Empty cannot be added.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
         onBackPressed();
         return  true;
    }

    private void addNewItem(String itemName){
        Items items = new Items();
        items.setChecked(false);
        items.setCategory(header);
        items.setItemname(itemName);
        items.setAddedby(MyConstants.USER_SMALL);
        database.mainDAO().saveItem(items);
        itemsList = database.mainDAO().getAll(header);
        updateRecycler(itemsList);
        recyclerView.scrollToPosition(checkListAdapter.getItemCount()-1);
        txtAdd.setText("");
    }
    private void updateRecycler(List<Items> itemsList){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        checkListAdapter=new CheckListAdapter(CheckList.this,itemsList,database,show);
        recyclerView.setAdapter(checkListAdapter);

    }
}