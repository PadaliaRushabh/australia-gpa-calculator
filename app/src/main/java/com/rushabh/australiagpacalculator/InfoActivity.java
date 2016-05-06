package com.rushabh.australiagpacalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    TextView supportFuture, support;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        supportFuture = (TextView) findViewById(R.id.support_future);
        supportFuture.setMovementMethod(LinkMovementMethod.getInstance());

        support = (TextView) findViewById(R.id.support);
        support.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
