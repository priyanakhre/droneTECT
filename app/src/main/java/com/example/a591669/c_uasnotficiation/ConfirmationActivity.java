package com.example.a591669.c_uasnotficiation;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ConfirmationActivity extends AppCompatActivity {

    Button backToHome;
    Button viewReport;
    private String userId;
    SharedPreferences sp;
    int numReports;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);


        backToHome = (Button) findViewById(R.id.backToHome);
        viewReport = (Button) findViewById(R.id.viewReport);

        Bundle bundle = getIntent().getExtras();
        userId = bundle.getString("message");

        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConfirmationActivity.this, SignupActivity.class));
                finish();
            }
        });

        viewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);

                startActivity(new Intent(ConfirmationActivity.this, ViewReport.class).putExtra("message", userId));
                finish();
            }
        });
    }





}
