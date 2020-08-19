package com.example.playground;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class MortgageCalc extends AppCompatActivity {

    public static TextView Addr;
    public TextView pInterest;
    public TextView monPay;

    public double payAmount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mortgage_calc);
        //Declare Variables
        Addr = (TextView) findViewById(R.id.txtAddr);
        pInterest = (TextView) findViewById(R.id.pInterest);
        monPay = (TextView) findViewById(R.id.monPay);

        Addr.setText(ShowListings.chosenItem);

    }
}
