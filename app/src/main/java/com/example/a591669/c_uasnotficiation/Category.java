package com.example.a591669.c_uasnotficiation;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.a591669.c_uasnotficiation.R.id.imageView;

public class Category extends AppCompatActivity {

    private ImageButton fixedWing;
    private ImageButton idk;
    int numReports;
    private GestureDetector gestureDetector;
    private ImageButton multiRotor;
    private ImageButton other;
    private ImageButton human;
    private ImageButton building;

    private Button continueButton;

    private DatabaseReference ref;
    private boolean typePressed;
    private boolean heightPressed;

    SharedPreferences sp;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        gestureDetector = (new GestureDetector(this,new MyGestureListener(this)));

        ref = FirebaseDatabase.getInstance().getReference();


        typePressed = false;
        heightPressed = false;
        fixedWing = (ImageButton) findViewById(R.id.fixedWing);
        idk = (ImageButton) findViewById(R.id.idk);
        multiRotor = (ImageButton) findViewById(R.id.multiRotor);
        other = (ImageButton) findViewById(R.id.other);

        continueButton = (Button) findViewById(R.id.button);
        human = (ImageButton) findViewById(R.id.human);
        building = (ImageButton) findViewById(R.id.building);

        Bundle bundle = getIntent().getExtras();
        userId = bundle.getString("message");



        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);

                numReports = sp.getInt(userId, 0);
                /*
                ref.child("users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("reports")
                        .child(Integer.toString(numReports))
                        .child("Category").setValue("Not Selected");
*/
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
                fixedWing.setImageResource(R.drawable.fixedwing_pressed);
                idk.setImageResource(R.drawable.unknown);
                multiRotor.setImageResource(R.drawable.quadcop);

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
                fixedWing.setImageResource(R.drawable.fixedwing);
                idk.setImageResource(R.drawable.unknown_pressed);
                multiRotor.setImageResource(R.drawable.quadcop);

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
                fixedWing.setImageResource(R.drawable.fixedwing);
                idk.setImageResource(R.drawable.unknown);
                multiRotor.setImageResource(R.drawable.quadcop_pressed);

            }
        });

        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);

                numReports = sp.getInt(userId, 0);
                ref.child("users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("reports")
                        .child(Integer.toString(numReports))
                        .child("Height").setValue("other");
                other.setImageResource(R.drawable.unknown_pressed);
                human.setImageResource(R.drawable.human);
                building.setImageResource(R.drawable.building);

            }
        });

        human.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);

                numReports = sp.getInt(userId, 0);
                ref.child("users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("reports")
                        .child(Integer.toString(numReports))
                        .child("Height").setValue("Human Level");

                other.setImageResource(R.drawable.unknown);
                human.setImageResource(R.drawable.human_pressed);
                building.setImageResource(R.drawable.building);

            }
        });

        building.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);

                numReports = sp.getInt(userId, 0);
                ref.child("users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("reports")
                        .child(Integer.toString(numReports))
                        .child("Height").setValue("Building Level");

                other.setImageResource(R.drawable.unknown);
                human.setImageResource(R.drawable.human);
                building.setImageResource(R.drawable.building_pressed);

            }
        });


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
