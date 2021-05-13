package com.diets.dietplans.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.diets.dietplans.Config;
import com.diets.dietplans.utils.FBProvider;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.diets.dietplans.Fragments.FragmentSections;
import com.diets.dietplans.Fragments.FragmentSplash;
import com.diets.dietplans.POJOS.Global;
import com.diets.dietplans.R;
import com.google.firebase.messaging.FirebaseMessaging;


public class MainActivity extends AppCompatActivity {
    private final String NAME_DB = "adb";
    private Global global = new Global();
    private SharedPreferences countOfRun;
    private int COUNT_OF_RUN = 0, COUNT_OF_BACK_PRESSED = 0;
    private final String TAG_OF_COUNT_RUN = "TAG_OF_COUNT_RUN";
    private InterstitialAd mInterstitialAd;
    private static AdView adView;

    @Override
    public void onBackPressed() {
        if (Config.INDEX_ADMOB > 2) {
            checkPermissionForShowInter();
        }
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.admob_inter));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        super.onBackPressed();
    }

    private void checkPermissionForShowInter() {
        if (mInterstitialAd.isLoaded()) {
            Config.INDEX_ADMOB = 0;
            mInterstitialAd.show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FBProvider.Companion.getFCMToken();

        adView = findViewById(R.id.bannerMainActivity);

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        adView.setVisibility(View.GONE);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.admob_inter));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fragmentContainer, new FragmentSplash()).commit();

        if (!hasConnection(this)) {
            Toast.makeText(this, R.string.check_your_connect, Toast.LENGTH_SHORT).show();
        }
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(NAME_DB);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                global = dataSnapshot.getValue(Global.class);
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer, FragmentSections.newInstance(global)).commit();
                additionOneToSharedPreference();
                checkFirstRun();
                adView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        COUNT_OF_RUN = getPreferences(MODE_PRIVATE).getInt(TAG_OF_COUNT_RUN, 0);


        FirebaseMessaging
                .getInstance()
                .subscribeToTopic("news")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e("LOL", "onSuccess");
                    }
                });
    }


    public static boolean hasConnection(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        return false;
    }

    private void additionOneToSharedPreference() {
        countOfRun = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = countOfRun.edit();
        editor.putInt(TAG_OF_COUNT_RUN, COUNT_OF_RUN + 1);
        editor.commit();
    }

    private void checkFirstRun() {
        if (countOfRun.getInt(TAG_OF_COUNT_RUN, COUNT_OF_RUN) == 4) {
            AlertDialog.Builder alertDialogGrade = new AlertDialog.Builder(this);
            alertDialogGrade.setTitle(R.string.titleAlertDialog);
            alertDialogGrade.setMessage(R.string.bodyAlertDialog);
            alertDialogGrade.setIcon(R.drawable.evaluate_app);
            alertDialogGrade.setNeutralButton(R.string.laterButtonAlert, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    countOfRun = getPreferences(MODE_PRIVATE);
                    SharedPreferences.Editor editor = countOfRun.edit();
                    editor.putInt(TAG_OF_COUNT_RUN, 0);
                    editor.commit();
                }
            });
            alertDialogGrade.setNegativeButton(R.string.neverButtonAlert, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //nothing to do
                }
            });
            alertDialogGrade.setPositiveButton(R.string.evaluateButtonAlert, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("market://details?id=" + MainActivity.this.getPackageName()));
                    startActivity(intent);
                }
            });
            alertDialogGrade.show();

        }
    }

}
