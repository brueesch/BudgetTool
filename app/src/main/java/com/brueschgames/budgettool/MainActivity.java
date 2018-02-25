package com.brueschgames.budgettool;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static final String REMAINING_MONEY_FILE_NAME = "remainingMoney";
    private TextView remainingMoney;
    private TextView inputSpendMoney;
    private File saveFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        remainingMoney = findViewById(R.id.remainingMoney);
        remainingMoney.setText(R.string.initial_value);
        inputSpendMoney = findViewById(R.id.inputSpendMoney);

        try {
            FileInputStream fin = openFileInput(REMAINING_MONEY_FILE_NAME);
            int c;
            StringBuilder temp = new StringBuilder();
            while ((c = fin.read()) != -1) {
                temp.append(Character.toString((char) c));
            }

            fin.close();
            String remainingMoneyString = temp.length() == 0?"50.0":temp.toString();
            remainingMoney.setText(remainingMoneyString);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void spendMoney(View view) {
        double value = Double.parseDouble(inputSpendMoney.getText().toString());
        double remaining = Double.parseDouble(remainingMoney.getText().toString());
        remaining -= value;
        if (remaining <= 0) {
            remaining = 0;
        }
        remainingMoney.setText("" + remaining);
        saveRemainingValue();
    }

    private void saveRemainingValue() {
        try {
            trySaveRemainingValue();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void trySaveRemainingValue() throws IOException {
        FileOutputStream fOut = openFileOutput(REMAINING_MONEY_FILE_NAME, Context.MODE_PRIVATE);
        fOut.write(remainingMoney.getText().toString().getBytes());
        fOut.close();
    }

    public void resetRemainingMoney(View view) {
        remainingMoney.setText(R.string.initial_value);

    }
}
