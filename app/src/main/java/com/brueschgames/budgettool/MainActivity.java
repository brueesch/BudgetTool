package com.brueschgames.budgettool;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.brueschgames.budgettool.model.Payment;
import com.brueschgames.budgettool.service.PaymentService;
import com.brueschgames.budgettool.service.impl.PaymentServiceImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView balance;
    private TextView amountPerDay;
    private PaymentService paymentService;
    private ListView paymentList;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        balance = findViewById(R.id.balance);
        amountPerDay = findViewById(R.id.amountPerDay);
        paymentService = PaymentServiceImpl.getInstance();
        paymentList = findViewById(R.id.paymentList);
        sharedPreferences = getPreferences(MODE_PRIVATE);

        loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void addPayment(View view) {
        Intent intent = new Intent(this, AddPaymentActivity.class);
        startActivity(intent);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        balance.setText(paymentService.getBalance().toString());
        amountPerDay.setText(paymentService.getAmountPerDay().toString());
        ArrayList<String> listArray = new ArrayList<>();
        for (Payment payment : paymentService.getAllPayments()) {
            listArray.add(payment.getAmount().toString() + "\n" + payment.getDescription() + "\n" + payment.getDate().toString());
        }

        saveData();








        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listArray);
        paymentList.setAdapter(arrayAdapter);
        paymentList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                paymentService.removePayment(i);
                onResume();
                return true;
            }
        });


    }

    public void saveData() {
        ArrayList<Payment> payments = paymentService.getAllPayments();
        if (payments.size() != 0) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(payments);
            editor.putString("payments", json);
            editor.apply();
        }

    }

    public void loadData() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("payments", "");
        ArrayList<Payment> payments = gson.fromJson(json, new TypeToken<List<Payment>>() {
        }.getType());
        if (payments != null) {
            paymentService.addPayments(payments);
        }

    }

}
