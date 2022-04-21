package com.diets.dietplans.presentation.calculators;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.diets.dietplans.R;
import com.diets.dietplans.utils.ad.AdWorker;
import com.diets.dietplans.utils.analytics.Ampl;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ActivityCalculatorIMT extends AppCompatActivity {
    //private AdView ban;
    private Button btnCalculate;
    private EditText edtHeight, edtWeight;
    private double weight, height;
    private ImageView ivBack;

    @Override
    public void onBackPressed() {
        AdWorker.INSTANCE.showInter();
        super.onBackPressed();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AdWorker.INSTANCE.checkLoad();
        setContentView(R.layout.activity_calculator_imt);
        Ampl.Companion.openCalcualtor("imt");
        //ban = findViewById(R.id.appodealBannerView);
        //ban.loadAd(new AdRequest.Builder().build());
        btnCalculate = findViewById(R.id.btnIMTCalculate);
        edtHeight = findViewById(R.id.edtIMTHeight);
        edtWeight = findViewById(R.id.edtIMTWeight);
        ivBack = findViewById(R.id.ivBack);

        ivBack.setOnClickListener(v ->
                onBackPressed());

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (checkNull()) {
                        createAlertDialog(countIMT());
                    }
                } catch (Exception e) {
                    Toast.makeText(ActivityCalculatorIMT.this, R.string.unknown_error, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private ArrayList<String> countIMT() {
        double minIndex = 16.5, notEnoughIndex = 18.49, normalIndex = 24.99,
                upNormalIndex = 29.99, obesityIndex = 34.99, obesitySecondIndex = 39.99, nextIndexStep = 0.01;
        double coefficient = weight / ((height / 100) * (height / 100));
        int position = 0;
        ArrayList<String> mainIndicators = new ArrayList<>();
        String[] arrayOfBodyTypes = getResources().getStringArray(R.array.body_types);
        String[] arrayOfBodyTypesDescriptions = getResources().getStringArray(R.array.descriptions_bodytypes);

        if (coefficient < minIndex) {
            position = 0;
        }
        if (coefficient >= minIndex && coefficient < notEnoughIndex) {
            position = 1;
        }
        if (coefficient >= (notEnoughIndex + nextIndexStep) && coefficient <= normalIndex) {
            position = 2;
        }
        if (coefficient >= (normalIndex + nextIndexStep) && coefficient <= (upNormalIndex)) {
            position = 3;
        }
        if (coefficient >= (upNormalIndex + nextIndexStep) && coefficient <= (obesityIndex)) {
            position = 4;
        }
        if (coefficient >= (obesityIndex + nextIndexStep) && coefficient <= obesitySecondIndex) {
            position = 5;
        }
        if (coefficient >= (obesitySecondIndex + nextIndexStep)) {
            position = 6;
        }

        mainIndicators.add(arrayOfBodyTypes[position]);
        mainIndicators.add(String.valueOf(coefficient).substring(0, 5));
        mainIndicators.add(arrayOfBodyTypesDescriptions[position]);

        return mainIndicators;
    }

    private boolean checkNull() {
        if (edtWeight == null || edtHeight == null) {
            Toast.makeText(this, R.string.fill_all_fields, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            height = Double.parseDouble(edtHeight.getText().toString());
            weight = Double.parseDouble(edtWeight.getText().toString());
            return true;
        }
    }

    private void createAlertDialog(ArrayList<String> mainIndicators) {
        Ampl.Companion.useCalcualtor("imt");
        String bodyType = mainIndicators.get(0), IMT = mainIndicators.get(1), description = mainIndicators.get(2);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder.create();
        View view = View.inflate(this, R.layout.alert_dialog_imt, null);
        TextView tvIMTAdName = view.findViewById(R.id.tvIMTAdName);
        TextView tvIMTAdDescription = view.findViewById(R.id.tvIMTAdDescription);
        TextView tvIMTAdIndex = view.findViewById(R.id.tvIMTAdIndex);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingActionButton);
        tvIMTAdIndex.setText(getString(R.string.your_imt) + " " + IMT);
        tvIMTAdName.setText(bodyType);
        tvIMTAdDescription.setText(description);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        alertDialog.setView(view);
        alertDialog.show();
    }
}
