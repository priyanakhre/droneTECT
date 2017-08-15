package com.example.a591669.c_uasnotficiation;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Compass extends Activity implements SensorEventListener {

    // define the display assembly compass picture
    private ImageView image;

    // record the compass picture angle turned
    private float currentDegree = 0f;

    // device sensor manager
    private SensorManager mSensorManager;

    private Button sendAngle;
    private Button next;

    private DatabaseReference ref;

    SharedPreferences sp;
    private String userId;
    int numReports;

    TextView tvHeading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        ref = FirebaseDatabase.getInstance().getReference();
        //
        image = (ImageView) findViewById(R.id.imageViewCompass);

        // TextView that will tell the user what degree is he heading
        tvHeading = (TextView) findViewById(R.id.tvHeading);

        // initialize your android device sensor capabilities
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        sendAngle = (Button) findViewById(R.id.send);
        next = (Button) findViewById(R.id.nextButton);

        Bundle bundle = getIntent().getExtras();
        userId = bundle.getString("message");

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Compass.this, Comments.class).putExtra("message", userId));
                finish();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // for the system's orientation sensor registered listeners
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // to stop the listener and save battery
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        // get the angle around the z-axis rotated
        final float degree = Math.round(event.values[0]);

        sendAngle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView showAngle = (TextView) findViewById(R.id.textView10);
                showAngle.setVisibility(View.VISIBLE);
                showAngle.setText("You have sent " + Float.toString(degree) + (char) 0x00B0);
                sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);

                numReports = sp.getInt(userId, 0);
                ref.child("users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("reports")
                        .child(Integer.toString(numReports))
                        .child("Angle").setValue(degree);
                //startActivity(new Intent(Compass.this, Compass.class));
                //finish();

            }
        });

        tvHeading.setText(Float.toString(degree) + (char) 0x00B0);

        // create a rotation animation (reverse turn degree degrees)
        RotateAnimation ra = new RotateAnimation(
                currentDegree,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

        // how long the animation will take place
        ra.setDuration(210);

        // set the animation after the end of the reservation status
        ra.setFillAfter(true);

        // Start the animation
        image.startAnimation(ra);
        currentDegree = -degree;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not in use
    }
}