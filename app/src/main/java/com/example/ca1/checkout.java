package com.example.ca1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.ca1.ShoppingListActivity.NO_OF_ITEMS;
import static com.example.ca1.ShoppingListActivity.TOTAL;

public class checkout extends AppCompatActivity {

    private TextView tvTotal;
    private TextView tvNoOfItems;
    private Button btnPay;
    private EditText etCardNumber;
    private EditText etCardName;
    private EditText etDate;
    private EditText etCsv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        etCardNumber = (EditText)findViewById(R.id.etCardNumber);
        etCardName = (EditText)findViewById(R.id.etCardName);
        etDate = (EditText)findViewById(R.id.etDate);
        etCsv = (EditText)findViewById(R.id.etCsv);
        tvTotal= (TextView)findViewById(R.id.tvTotal);
        tvNoOfItems = (TextView)findViewById(R.id.tvNoOfItems);
        btnPay = (Button)findViewById(R.id.btnPay);

        Intent intent = getIntent();
        double total = intent.getDoubleExtra(TOTAL, 0);
        int noOfItems = intent.getIntExtra(NO_OF_ITEMS, 0);

        tvTotal.setText("Total: €" + total);
        tvNoOfItems.setText("Number Of Items: " + noOfItems);

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etCardNumber.getText().toString().isEmpty() || etCardName.getText().toString().isEmpty() ||
                        etDate.getText().toString().isEmpty() || etCsv.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(), "Please ensure all card information are entered", Toast.LENGTH_SHORT).show();
                else {
                    tvNoOfItems.setText("Payment Successful!");
                    etCsv.setText("");
                    etDate.setText("");
                    etCardName.setText("");
                    etCardNumber.setText("");
                }

            }
        });
    }
}