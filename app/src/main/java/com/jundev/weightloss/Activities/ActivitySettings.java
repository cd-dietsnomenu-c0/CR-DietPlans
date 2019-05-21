package com.jundev.weightloss.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.jundev.weightloss.Config;
import com.jundev.weightloss.R;

public class ActivitySettings extends AppCompatActivity {
    CardView cvRate, cvPrivacy, cvShare;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Config.INDEX_ADMOB++;

        cvPrivacy = findViewById(R.id.cvPrivacy);
        cvRate = findViewById(R.id.cvRate);
        cvShare = findViewById(R.id.cvShare);

        cvPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPrivacyPage();
            }
        });

        cvRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rateApp();
            }
        });

        cvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareUrlOfApp();
            }
        });

    }

    private void shareUrlOfApp() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.accompanying_text)
                + "\n"
                + "https://play.google.com/store/apps/details?id="
                + this.getPackageName());
        startActivity(intent);
    }

    private void rateApp() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + this.getPackageName()));
        startActivity(intent);
    }

    private void openPrivacyPage() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(getResources().getString(R.string.url_gdpr)));
        startActivity(intent);
    }

}
