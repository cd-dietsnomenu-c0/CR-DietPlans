package com.diets.dietplans.presentation.calculators;

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

public class ActivityCalculatorLorenc extends AppCompatActivity {
    EditText edtLorencHeight;
    Button btnCalculate;
    //private AdView ban;
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
        setContentView(R.layout.activity_calculator_lorenc);
        Ampl.Companion.openCalcualtor("lorenc");
        //ban = findViewById(R.id.appodealBannerView);
        //ban.loadAd(new AdRequest.Builder().build());
        edtLorencHeight = findViewById(R.id.edtLorencHeight);
        btnCalculate = findViewById(R.id.btnLorencCalculate);
        ivBack = findViewById(R.id.ivBack);

        ivBack.setOnClickListener(v ->
                onBackPressed());


        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    createAlertDialog(calculate());
                } catch (Exception e) {
                    Toast.makeText(ActivityCalculatorLorenc.this, R.string.check_your_data, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void createAlertDialog(double calculate) {
        Ampl.Companion.openCalcualtor("lorenc");
        TextView tvAlertDialogWeight;
        FloatingActionButton btnOk;

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder.create();
        View view = getLayoutInflater().inflate(R.layout.alert_dialog_weight, null);
        tvAlertDialogWeight = view.findViewById(R.id.tvAlertDialogWeight);
        btnOk = view.findViewById(R.id.btnAlertDialogOk);
        tvAlertDialogWeight.setText(String.valueOf(calculate) + " " + getString(R.string.kg_brok));
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });
        alertDialog.setView(view);
        alertDialog.show();

    }

    private double calculate() {
        return Integer.parseInt(edtLorencHeight.getText().toString()) / 2 - 25;
    }
}
