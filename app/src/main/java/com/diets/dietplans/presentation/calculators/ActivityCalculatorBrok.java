package com.diets.dietplans.presentation.calculators;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.diets.dietplans.R;
import com.diets.dietplans.utils.ad.AdWorker;
import com.diets.dietplans.utils.analytics.Ampl;


public class ActivityCalculatorBrok extends AppCompatActivity {
    private EditText edtBrokGrowth, edtBrokGirth, edtBrokAge;
    private RadioButton rbFemale, rbMale;
    private Button btnCalculate;
    private ImageView ivBack;
    private AdView ban;
    int growth, girth, age, femaleDownFlag = 14, femaleUpFlag = 18, maleDownFlag = 17, maleUpFlag = 20, minNumber = 0;
    double idealWeight;
    boolean ast = false, normo = false, hyper = false;

    @Override
    public void onBackPressed() {
        AdWorker.INSTANCE.showInter();
        super.onBackPressed();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AdWorker.INSTANCE.checkLoad();
        setContentView(R.layout.activity_calculator_brok);
        Ampl.Companion.openCalcualtor("brok");
        ban = findViewById(R.id.appodealBannerView);
        ban.loadAd(new AdRequest.Builder().build());
        edtBrokGrowth = findViewById(R.id.edtBrokGrowth);
        edtBrokGirth = findViewById(R.id.edtBrokGirth);
        edtBrokAge = findViewById(R.id.edtBrokAge);
        rbFemale = findViewById(R.id.rdBrokFemale);
        rbMale = findViewById(R.id.rdBrokMale);
        btnCalculate = findViewById(R.id.btnBrokCalculate);
        ivBack = findViewById(R.id.ivBack);

        ivBack.setOnClickListener(v ->
                onBackPressed());

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (parseFieldsInToInteger()) {
                    if (checkErrorFromFields()) {
                        calculate();
                        createAlertDialog(idealWeight);
                    }
                }
            }
        });
    }

    private void calculate() {
        Ampl.Companion.useCalcualtor("brok");
        if (age <= 40) {
            if (growth <= 165) {
                idealWeight = ((growth - 100) * 0.9 - ((growth - 100) * 0.1)) * checkTypeOfBody();
            }
            if (growth >= 166 && growth <= 175) {
                idealWeight = ((growth - 105) * 0.9 - ((growth - 105) * 0.1)) * checkTypeOfBody();
            }
            if (growth >= 176) {
                idealWeight = ((growth - 110) * 0.9 - ((growth - 110) * 0.1)) * checkTypeOfBody();
            }
        } else {
            if (growth <= 165) {
                idealWeight = ((growth - 100) * 0.9 + ((growth - 100) * 0.07)) * checkTypeOfBody();
            }
            if (growth >= 166 && growth <= 175) {
                idealWeight = ((growth - 105) * 0.9 + ((growth - 105) * 0.07)) * checkTypeOfBody();
            }
            if (growth >= 176) {
                idealWeight = ((growth - 110) * 0.9 + ((growth - 110) * 0.07)) * checkTypeOfBody();
            }
        }
    }

    private double checkTypeOfBody() {
        if ((rbFemale.isChecked() && girth <= femaleDownFlag) ||
                (rbMale.isChecked() && girth <= maleDownFlag)) {
            return 0.9;
        } else {
            if ((rbFemale.isChecked() && girth >= femaleUpFlag) ||
                    (rbMale.isChecked() && girth >= maleUpFlag)) {
                return 1.1;
            } else {
                if ((rbFemale.isChecked() && girth > femaleDownFlag && girth < femaleUpFlag) ||
                        (rbMale.isChecked() && girth > maleDownFlag && girth < maleUpFlag)) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }

    private boolean parseFieldsInToInteger() {
        try {
            growth = Integer.parseInt(edtBrokGrowth.getText().toString());
            girth = Integer.parseInt(edtBrokGirth.getText().toString());
            age = Integer.parseInt(edtBrokAge.getText().toString());
            return true;
        } catch (Exception e) {
            Toast.makeText(this, R.string.fill_all_fields, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean checkErrorFromFields() {
        if (growth <= minNumber || girth <= minNumber || !(rbMale.isChecked() || rbFemale.isChecked())) {
            Toast.makeText(this, R.string.check_your_data, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void createAlertDialog(double calculate) {
        TextView tvAlertDialogWeight;
        FloatingActionButton fab;

        ast = false;
        normo = false;
        hyper = false;

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder.create();
        View view = getLayoutInflater().inflate(R.layout.alert_dialog_weight, null);
        tvAlertDialogWeight = view.findViewById(R.id.tvAlertDialogWeight);
        fab = view.findViewById(R.id.btnAlertDialogOk);
        tvAlertDialogWeight.setText(String.valueOf((int) calculate) + getString(R.string.kg_brok));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });
        alertDialog.setView(view);
        alertDialog.show();

    }
}
