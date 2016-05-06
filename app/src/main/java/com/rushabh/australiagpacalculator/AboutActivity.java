package com.rushabh.australiagpacalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    TextView version, developers;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        version = (TextView) findViewById(R.id.about_app_version);
        version.setText("Version: " +  BuildConfig.VERSION_NAME);

        developers = (TextView) findViewById(R.id.about_app_developers);
        developers.setMovementMethod(LinkMovementMethod.getInstance());


    }
}
