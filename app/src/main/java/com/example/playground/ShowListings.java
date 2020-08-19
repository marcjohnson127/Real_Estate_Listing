package com.example.playground;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.ArrayLinkedVariables;

import java.util.ArrayList;

public class ShowListings extends AppCompatActivity {

    public mySQLiteDBHandler dbHandler;
    public SQLiteDatabase sqLiteDatabase;

    public ListView dispList;
    public ListView dispArea;
    public ArrayList arrayList;
    public ArrayList arrayList2;
    public ArrayAdapter arrayAdapter;
    public ArrayAdapter arrayAdapter2;

    public static String chosenItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_all_listings);
        //Declare Variables
        dispList = (ListView) findViewById(R.id.displayListings);
        dispArea = (ListView) findViewById(R.id.displayArea);

        //Initialize dbHandler
        dbHandler = new mySQLiteDBHandler(ShowListings.this);

        //Add Database Values to ArrayList
        arrayList = dbHandler.allData();
        arrayList2 = dbHandler.allData2();

        //Initialize ArrayAdapter
        arrayAdapter = new ArrayAdapter(ShowListings.this,android.R.layout.simple_list_item_1,arrayList);
        arrayAdapter2 = new ArrayAdapter(ShowListings.this,android.R.layout.simple_list_item_1,arrayList2);

        //Set ArrayAdapter to List View
        dispList.setAdapter(arrayAdapter);
        dispArea.setAdapter(arrayAdapter2);

        dispList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chosenItem =  dispList.getItemAtPosition(position).toString();
                Intent intent = new Intent(ShowListings.this,PropertyInfo.class);

                startActivity(intent);
            }
        });

    }


}
