package com.example.a591669.c_uasnotficiation;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Category extends AppCompatActivity {

    private ImageButton fixedWing;
    private ImageButton idk;
    int numReports;
    private ImageButton multiRotor;
    private ImageButton quadCop;
    private Button continueButton;

    private DatabaseReference ref;

    SharedPreferences sp;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        ref = FirebaseDatabase.getInstance().getReference();

        fixedWing = (ImageButton) findViewById(R.id.fixedWing);
        idk = (ImageButton) findViewById(R.id.idk);
        multiRotor = (ImageButton) findViewById(R.id.multiRotor);
        quadCop = (ImageButton) findViewById(R.id.quadCop);

        continueButton = (Button) findViewById(R.id.button);

        Bundle bundle = getIntent().getExtras();
        userId = bundle.getString("message");

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);

                numReports = sp.getInt(userId, 0);
                ref.child("users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("reports")
                        .child(Integer.toString(numReports))
                        .child("Category").setValue("Not Selected");

                startActivity(new Intent(Category.this, Compass.class).putExtra("message", userId));
                finish();


            }
        });

        fixedWing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);

                numReports = sp.getInt(userId, 0);
                ref.child("users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("reports")
                        .child(Integer.toString(numReports))
                        .child("Category").setValue("Fixed Wing");
                startActivity(new Intent(Category.this, Compass.class).putExtra("message", userId));
                finish();


            }
        });

        idk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);

                numReports = sp.getInt(userId, 0);
                ref.child("users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("reports")
                        .child(Integer.toString(numReports))
                        .child("Category").setValue("Unknown");
                startActivity(new Intent(Category.this, Compass.class).putExtra("message", userId));
                finish();

            }
        });

        multiRotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);

                numReports = sp.getInt(userId, 0);
                ref.child("users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("reports")
                        .child(Integer.toString(numReports))
                        .child("Category").setValue("Multi Rotor");
                startActivity(new Intent(Category.this, Compass.class).putExtra("message", userId));
                finish();

            }
        });

        quadCop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);

                numReports = sp.getInt(userId, 0);
                ref.child("users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("reports")
                        .child(Integer.toString(numReports))
                        .child("Category").setValue("Quadcoptor");
                startActivity(new Intent(Category.this, Compass.class).putExtra("message", userId));
                finish();

            }
        });
    }
}
