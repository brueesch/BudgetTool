package com.brueschgames.budgettool;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

import static java.lang.Math.round;

public class MainActivity extends AppCompatActivity {

    public static final String REMAINING_MONEY_FILE_NAME = "remainingMoney";
    private TextView remainingMoney;
    private TextView inputSpendMoney;
    private ProgressBar spendMoneyProgressBar;
    private String initialValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        remainingMoney = findViewById(R.id.remainingMoney);
        inputSpendMoney = findViewById(R.id.inputSpendMoney);
        spendMoneyProgressBar = findViewById(R.id.spendMoneyProgressBar);
        initialValue = getResources().getString(R.string.initial_value);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);





        try {
            FileInputStream fin = openFileInput(REMAINING_MONEY_FILE_NAME);
            int c;
            StringBuilder temp = new StringBuilder();
            while ((c = fin.read()) != -1) {
                temp.append(Character.toString((char) c));
            }

            fin.close();
            String remainingMoneyString = temp.length() == 0?initialValue:temp.toString();
            remainingMoney.setText(remainingMoneyString);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateProgressBar();

    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void addSpendMoney(View view) {
        if(inputSpendMoney.getText().length() != 0) {
            double value = Double.parseDouble(inputSpendMoney.getText().toString());
            double remaining = Double.parseDouble(remainingMoney.getText().toString());
            remaining -= value;
            if (remaining <= 0) {
                remaining = 0;
            }

            DecimalFormat df = new DecimalFormat("#.##");
            String format = df.format(remaining);

            remainingMoney.setText(format);
            inputSpendMoney.setText(null);
            saveRemainingValue();
            updateProgressBar();
        }
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

    public void resetRemainingMoney(MenuItem menuItem) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        remainingMoney.setText(initialValue);
                        saveRemainingValue();
                        updateProgressBar();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to reset the amount to your initial value? (" + initialValue +")").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();



    }

    private void updateProgressBar() {
        Double remaining = Double.parseDouble(remainingMoney.getText().toString());
        int i = remaining.intValue();
        spendMoneyProgressBar.setProgress(i*2);
    }
}
