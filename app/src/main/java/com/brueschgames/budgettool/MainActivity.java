package com.brueschgames.budgettool;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

import static com.brueschgames.budgettool.R.id.spendMoneyProgressBar;
import static java.lang.Math.round;

public class MainActivity extends AppCompatActivity {

    public static final String REMAINING_MONEY_FILE_NAME = "remainingAmount";
    private TextView remainingAmount;
    private String initialValue;
    private TextView inputSpendMoney;
    private DialogInterface.OnClickListener dialogClickListener;

    /**
     * Method is called when the App is started.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        remainingAmount = findViewById(R.id.remainingAmount);
        initialValue = getResources().getString(R.string.initial_value);
        inputSpendMoney = findViewById(R.id.inputSpendMoney);
        dialogClickListener = initializeResetDialog();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        loadSavedDataAndApply();
    }

    private void loadSavedDataAndApply() {
        try {
            tryLoadSaveDataAndApply();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void tryLoadSaveDataAndApply() throws IOException {
        applyData(loadSaveData());
    }

    private void applyData(StringBuilder temp) {
        setRemainingAmount((temp.length() == 0) ? initialValue : temp.toString());
    }

    @NonNull
    private StringBuilder loadSaveData() throws IOException {
        FileInputStream fin = openFileInput(REMAINING_MONEY_FILE_NAME);
        StringBuilder temp = new StringBuilder();
        int c;

        while ((c = fin.read()) != -1) {
            temp.append(Character.toString((char) c));
        }
        fin.close();
        return temp;
    }


    /**
     * Creates Toolbar
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    /**
     * Method is called by the 'Add' Button to add a new amount which was spend.
     *
     * @param view
     */
    public void addSpendMoney(View view) {
        if (isSpendMoneyEntered()) {
            double remaining = calculateNewRemainingAmount();
            setRemainingAmount(formatRemainingAmount(remaining));
        }
    }

    private boolean isSpendMoneyEntered() {
        return inputSpendMoney.getText().length() != 0;
    }

    private double calculateNewRemainingAmount() {
        double value = Double.parseDouble(inputSpendMoney.getText().toString());
        double remaining = Double.parseDouble(remainingAmount.getText().toString());
        return remaining > value ? remaining - value : 0;
    }

    private String formatRemainingAmount(double remaining) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(remaining);
    }


    /**
     * Resets the Remaining Amount
     *
     * @param menuItem
     */
    public void resetRemainingAmount(MenuItem menuItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Do you want to reset the amount to your initial value? (" + initialValue + ")")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    @NonNull
    private DialogInterface.OnClickListener initializeResetDialog() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        setRemainingAmount(initialValue);
                        break;
                }
            }
        };
    }


    private void setRemainingAmount(String value) {
        remainingAmount.setText(value);
        inputSpendMoney.setText(null);
        updateProgressBar();
        saveRemainingValue();
    }

    private void updateProgressBar() {
        int remainingIntAmount = ((Double) Double.parseDouble(remainingAmount.getText().toString())).intValue();
        this.<ProgressBar>findViewById(spendMoneyProgressBar).setProgress(remainingIntAmount * 2);
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
        fOut.write(remainingAmount.getText().toString().getBytes());
        fOut.close();
    }
}
