package com.brueschgames.budgettool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.brueschgames.budgettool.model.Payment;
import com.brueschgames.budgettool.service.PaymentService;
import com.brueschgames.budgettool.service.impl.PaymentServiceImpl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Objects;

public class AddPaymentActivity extends AppCompatActivity {

    private TextView description;
    private PaymentService paymentService;
    private TextView amount;
    private Switch incomeSwitch;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);
        description = findViewById(R.id.paymentDescription);
        amount = findViewById(R.id.paymentAmount);
        paymentService = PaymentServiceImpl.getInstance();
        incomeSwitch = findViewById(R.id.incomeSwitch);
        incomeSwitch.setText("Is spending");
        incomeSwitch.setChecked(true);

    }

    public void addPayment(View view) {
        if (Objects.equals(description.getText().toString(),"") || Objects.equals(amount.getText().toString(), "")) {
            showInfoToast();
        } else {
            BigDecimal amountBigDecimal = BigDecimal.valueOf(Double.parseDouble(amount.getText().toString()));
            if(incomeSwitch.isChecked()) {
                amountBigDecimal = amountBigDecimal.negate();
            }

            Payment payment = new Payment()
                    .setDescription(description.getText().toString())
                    .setDate(Calendar.getInstance().getTime())
                    .setAmount(amountBigDecimal);

            paymentService.addPayment(payment);
            finish();
        }
    }

    private void showInfoToast() {
        Context context = getApplicationContext();
        CharSequence text = "All fields must be filled";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}
