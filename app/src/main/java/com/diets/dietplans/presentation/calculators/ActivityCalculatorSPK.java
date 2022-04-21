package com.diets.dietplans.presentation.calculators;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.diets.dietplans.R;
import com.diets.dietplans.utils.ad.AdWorker;
import com.diets.dietplans.utils.analytics.Ampl;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class ActivityCalculatorSPK extends AppCompatActivity {
    private EditText edtHeight, edtAge, edtWeight;
    private Button btnLevelLoad, btnCalculate;
    private RadioGroup rgFemaleOrMale;
    private TextView tvTitle;
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
        setContentView(R.layout.activity_calculator_spk);
        Ampl.Companion.openCalcualtor("spk");
        //ban = findViewById(R.id.appodealBannerView);
       // ban.loadAd(new AdRequest.Builder().build());
        edtHeight = findViewById(R.id.edtSpkGrowth);
        edtAge = findViewById(R.id.edtSpkAge);
        edtWeight = findViewById(R.id.edtSpkWeight);
        btnLevelLoad = findViewById(R.id.btnSpkChoiseLevel);
        btnCalculate = findViewById(R.id.btnSpkCalculate);
        rgFemaleOrMale = findViewById(R.id.rgFemaleOrMaleSpk);
        tvTitle = findViewById(R.id.tvTitleOfSPK);
        ivBack = findViewById(R.id.ivBack);

        ivBack.setOnClickListener(v ->
                onBackPressed());

        tvTitle.setText(getResources().getStringArray(R.array.titles_of_calculating_list)[3]);

        btnLevelLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAlertDialogAboutLevelLoad();
            }
        });

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkInputData()) {
                    calculate();
                }
            }
        });
    }

    private boolean checkInputData() {
        if (rgFemaleOrMale.getCheckedRadioButtonId() != -1) {
            if (!edtAge.getText().toString().equals("")
                    && Integer.parseInt(edtAge.getText().toString()) >= 18
                    && Integer.parseInt(edtAge.getText().toString()) <= 200) {
                if (!edtHeight.getText().toString().equals("")
                        && Integer.parseInt(edtHeight.getText().toString()) >= 100
                        && Integer.parseInt(edtHeight.getText().toString()) <= 300) {
                    if (!edtWeight.getText().toString().equals("")
                            && Double.parseDouble(edtWeight.getText().toString()) >= 30
                            && Double.parseDouble(edtWeight.getText().toString()) <= 300) {
                        return true;
                    } else {
                        Toast.makeText(ActivityCalculatorSPK.this, R.string.spk_check_weight, Toast.LENGTH_SHORT).show();
                        return false;
                    }
                } else {
                    Toast.makeText(ActivityCalculatorSPK.this, R.string.spk_check_your_height, Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else {
                Toast.makeText(ActivityCalculatorSPK.this, R.string.spk_check_your_age, Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(ActivityCalculatorSPK.this, R.string.spk_choise_your_gender, Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    /*Минимальные нагрузки (сидячая работа) - К=1.2
    Немного дневной активности и легкие упражнения 1-3 раза в неделю - К=1.375
    Тренировки 4-5 раз в неделю (или работа средней тяжести) - К= 1.4625
    Интенсивные тренировки 4-5 раз в неделю - К=1.550
    Ежедневные тренировки - К=1.6375
    Ежедневные интенсивные тренировки или тренировки 2 раза в день - К=1.725
    Тяжелая физическая работа или интенсивные тренировки 2 раза в день - К=1.9*/

    private void calculate() {
        Ampl.Companion.openCalcualtor("spk");
        String levelNone = getString(R.string.level_none);
        double BOO = 0, SDD = 0.1;
        double rateNone = 1.2, rateEasy = 1.375, rateMedium = 1.4625, rateHard = 1.55,
                rateUpHard = 1.6375, rateSuper = 1.725, rateUpSuper = 1.9;
        double weight = Double.parseDouble(edtWeight.getText().toString()), height = Double.parseDouble(edtHeight.getText().toString());
        int age = Integer.parseInt(edtAge.getText().toString());
        double SPK = 0, upLineSPK = 0, downLineSPK = 0;
        double forCountUpLine = 300, forCountDownLine = 500;
        double fat, protein, carbohydrate;


        switch (rgFemaleOrMale.getCheckedRadioButtonId()) {
            case R.id.rdSpkFemale:
                BOO = (9.99 * weight + 6.25 * height - 4.92 * age - 161) * 1.1;
                Log.i("LOL", String.valueOf(BOO));
                break;
            case R.id.rdSpkMale:
                BOO = (9.99 * weight + 6.25 * height - 4.92 * age + 5) * 1.1;
                Log.i("LOL", String.valueOf(BOO));
                break;
        }

        /*Check level load*/
        if (btnLevelLoad.getText().toString().equals(getString(R.string.level_none)))
            SPK = BOO * rateNone;
        if (btnLevelLoad.getText().toString().equals(getString(R.string.level_easy)))
            SPK = BOO * rateEasy;
        if (btnLevelLoad.getText().toString().equals(getString(R.string.level_medium)))
            SPK = BOO * rateMedium;
        if (btnLevelLoad.getText().toString().equals(getString(R.string.level_hard)))
            SPK = BOO * rateHard;
        if (btnLevelLoad.getText().toString().equals(getString(R.string.level_up_hard)))
            SPK = BOO * rateUpHard;
        if (btnLevelLoad.getText().toString().equals(getString(R.string.level_super)))
            SPK = BOO * rateSuper;
        if (btnLevelLoad.getText().toString().equals(getString(R.string.level_up_super)))
            SPK = BOO * rateUpSuper;

        upLineSPK = SPK - forCountUpLine;
        downLineSPK = SPK - forCountDownLine;

        fat = upLineSPK * 0.2 / 9;
        protein = upLineSPK * 0.3 / 4;
        carbohydrate = upLineSPK * 0.5 / 3.75;


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder.create();
        View view = View.inflate(this, R.layout.alert_dialog_spk, null);
        TextView tvCalInDay = view.findViewById(R.id.tvAlertDialogSPKCountOfSPK);
        TextView tvDownLine = view.findViewById(R.id.tvAlertDialogSPKDownLine);
        TextView tvBGU = view.findViewById(R.id.tvAlertDialogSPKBGU);
        FloatingActionButton btnOk = view.findViewById(R.id.btnAlertDialogSPKOk);

        tvCalInDay.setText(String.valueOf(((int) SPK)) + " " + getString(R.string.spk_kcal));
        tvDownLine.setText(String.valueOf(((int) downLineSPK)) + " " + getString(R.string.spk_kcal) + " - "
                + String.valueOf(((int) upLineSPK)) + " " + getString(R.string.spk_kcal));
        tvBGU.setText(getString(R.string.prot_spk) + " " + String.valueOf(((int) protein))
                +  getString(R.string.gramm) + "\n" + getString(R.string.fat_spk) + " " + String.valueOf(((int) fat))
                +  getString(R.string.gramm) + "\n" + getString(R.string.carbo_spk) + " " + String.valueOf(((int) carbohydrate)) +  getString(R.string.gramm));
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });
        alertDialog.setView(view);
        alertDialog.show();


    }

    private void createAlertDialogAboutLevelLoad() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View view = View.inflate(this, R.layout.alert_dialog_level, null);
        final RadioGroup rgLevelLoad = view.findViewById(R.id.rgLevelLoad);
        builder.setView(view);
        builder.setPositiveButton(R.string.ok_spk, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (rgLevelLoad.getCheckedRadioButtonId() != -1) {
                    RadioButton radioButton = view.findViewById(rgLevelLoad.getCheckedRadioButtonId());
                    btnLevelLoad.setText(radioButton.getText());
                }
            }
        });
        builder.setNeutralButton(R.string.cancel_spk, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }
}
