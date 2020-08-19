package com.example.playground;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.Locale;

public class PropertyInfo extends AppCompatActivity {

    public mySQLiteDBHandler dbHandler;
    public SQLiteDatabase sqLiteDatabase;

    public TextView txtStreet;
    public TextView txtCity;
    public TextView txtArea;
    public TextView txtType;
    public TextView txtPrice;
    public TextView txtSQFT;
    public TextView txtRoom;
    public TextView txtBath;
    public TextView txtHOA;
    public TextView txtTax;

    public Button getMortgage;
    public EditText intPer;
    public EditText loanLength;
    public TextView monTotal;

    public Button btnDlt;
    public Button btnLoc;

    public static DecimalFormat d2curr = new DecimalFormat("###,###.##");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.property_info);
        //Declare Variables
        txtStreet = (TextView) findViewById(R.id.txtStreet);
        txtCity = (TextView) findViewById(R.id.txtcity);
        txtArea = (TextView) findViewById(R.id.txtArea);
        txtType = (TextView) findViewById(R.id.txtType);
        txtPrice = (TextView) findViewById(R.id.txtPrice);
        txtSQFT = (TextView) findViewById(R.id.txtSQFT);
        txtRoom = (TextView) findViewById(R.id.txtRoom);
        txtBath = (TextView) findViewById(R.id.txtBath);
        txtHOA = (TextView) findViewById(R.id.txtHOA);
        txtTax = (TextView) findViewById(R.id.txtTax);
        btnDlt = (Button) findViewById(R.id.btnDelete);
        btnLoc = (Button) findViewById(R.id.showLoc); 

        getMortgage = (Button) findViewById(R.id.getMortgage);
        intPer = (EditText) findViewById(R.id.intPercent);
        loanLength = (EditText) findViewById(R.id.loanLength);
        monTotal = (TextView) findViewById(R.id.monTotal);

        dbHandler = new mySQLiteDBHandler(this);

        popData();

        getMortgage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addr = ShowListings.chosenItem;
                double mPer = 0;
                int tPrice = 0;
                int mHOA = 0;
                double yTax = 0;
                int lLength = 0;

                Cursor cursor = dbHandler.getList(addr);
                if (cursor.getCount()==0) {
                    Toast.makeText(getApplicationContext(),"NO DATA",Toast.LENGTH_SHORT).show();
                }
                else {
                    while (cursor.moveToNext()) {
                        tPrice = Integer.parseInt(cursor.getString(cursor.getColumnIndex("price")));
                        mHOA = Integer.parseInt(cursor.getString(cursor.getColumnIndex("hoa")));
                        yTax = Integer.parseInt(cursor.getString(cursor.getColumnIndex("tax")));
                    }
                }

                yTax /= 12;
                mPer = Integer.parseInt(intPer.getText().toString());
                mPer /= 100;
                mPer /= 12;
                lLength = Integer.parseInt(loanLength.getText().toString());
                lLength *= 12;

                double mPay = tPrice*(mPer*Math.pow((1+mPer),lLength))/((Math.pow((1+mPer),lLength))-1);

                mPay = mPay + yTax + mHOA;

                Toast.makeText(getApplicationContext(), "Monthly Payment is:  $" + d2curr.format(mPay), Toast.LENGTH_SHORT).show();
                monTotal.setText("Monthly payment: $" + d2curr.format(mPay));

                intPer.setText(d2curr.format(mPer*12*100) + "%");
                loanLength.setText(lLength/12 + " years");
            }
        });


        btnDlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PropertyInfo.this,MainActivity.class);

                String addr = ShowListings.chosenItem;
                dbHandler.deleteEntry(addr);

                Toast.makeText(getApplicationContext(),"Entry for " + addr + " deleted!",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        
        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "geo:0,0?q=" + txtStreet.getText().toString().replaceAll( " ","+") + "+" + txtCity.getText().toString().replaceAll(" ","+");
                uri = uri.replace("#","%23");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });

    }

    public void popData() {
        String addr = ShowListings.chosenItem;
        Cursor cursor = dbHandler.getList(addr);
        if (cursor.getCount()==0) {
            Toast.makeText(getApplicationContext(),"NO DATA",Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(),addr,Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext()) {
                txtStreet.setText(cursor.getString(cursor.getColumnIndex("street")));
                txtCity.setText(cursor.getString(cursor.getColumnIndex("city")));
                txtArea.setText("Location: " + cursor.getString(cursor.getColumnIndex("area")));
                txtPrice.setText("Asking price: $" + d2curr.format(Integer.parseInt(cursor.getString(cursor.getColumnIndex("price")))));
                txtSQFT.setText("Sq. Ft.: " + d2curr.format(Integer.parseInt(cursor.getString(cursor.getColumnIndex("sqft")))));
                txtRoom.setText("Bed: " + cursor.getString(cursor.getColumnIndex("room")));
                txtBath.setText("Bath: " + cursor.getString(cursor.getColumnIndex("bath")));
                txtHOA.setText("HOA/Condo Fees: $" + cursor.getString(cursor.getColumnIndex("hoa")));
                txtTax.setText("Tax (year): $" + d2curr.format(Integer.parseInt(cursor.getString(cursor.getColumnIndex("tax")))));
                txtType.setText(cursor.getString(cursor.getColumnIndex("type")));
            }
        }
    }
    
    


}
