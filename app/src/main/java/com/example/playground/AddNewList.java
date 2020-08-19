package com.example.playground;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddNewList extends AppCompatActivity {

    public SQLiteDatabase sqLiteDatabase;
    public mySQLiteDBHandler dbHandler;
    
    public EditText edtStreet;
    public EditText edtCity;
    public EditText edtArea;
    public EditText edtType;
    public EditText edtRoom;
    public EditText edtBath;
    public EditText edtPrice;
    public EditText edtSQFT;
    public EditText edtHOA;
    public EditText edtTax;

    public Button btnSubmit;
    
    public String street;
    public String city;
    public String area;
    public String type;
    public int room;
    public double bath;
    public int price;
    public int sqft;
    public int hoa;
    public int tax;
    

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_list);

        //Declare Variables
        edtStreet = (EditText) findViewById(R.id.edtStreet);
        edtCity = (EditText) findViewById(R.id.edtCity);
        edtArea = (EditText) findViewById(R.id.edtArea);
        edtType = (EditText) findViewById(R.id.edtType);
        edtRoom = (EditText) findViewById(R.id.edtRoom);
        edtBath = (EditText) findViewById(R.id.edtBath);
        edtPrice = (EditText) findViewById(R.id.edtPrice);
        edtSQFT = (EditText) findViewById(R.id.edtSQFT);
        edtHOA = (EditText) findViewById(R.id.edtHOA);
        edtTax = (EditText) findViewById(R.id.edtTax);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        dbHandler = new mySQLiteDBHandler(this);
        
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNewList.this,MainActivity.class);
                
                street = edtStreet.getText().toString();
                city = edtCity.getText().toString();
                area = edtArea.getText().toString();
                type = edtType.getText().toString();
                room = Integer.parseInt(edtRoom.getText().toString());
                bath = Double.parseDouble(edtBath.getText().toString());
                price = Integer.parseInt(edtPrice.getText().toString());
                sqft = Integer.parseInt(edtSQFT.getText().toString());
                hoa = Integer.parseInt(edtHOA.getText().toString());
                tax = Integer.parseInt(edtTax.getText().toString());
                
                addToDatabase();
                
                startActivity(intent);
            }
        });

    }
    
    public void addToDatabase() {
        dbHandler.addData(street, city, area, room, bath, sqft, price, hoa, tax, type);
        Toast.makeText(getApplicationContext(), "New Listing Added!", Toast.LENGTH_SHORT).show();
        
    }
    
}
