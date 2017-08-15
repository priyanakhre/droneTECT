package com.example.a591669.c_uasnotficiation;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.renderscript.Double2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class ViewReport extends AppCompatActivity {

    private TextView reportNum;
    private TextView date;
    private TextView time;
    private TextView lat;
    private TextView longit;
    private TextView type;
    private TextView height;
    private TextView direction;
    private TextView comments;
    private Button home;
    private ImageView picture;
    private String userId;
    SharedPreferences sp;


    int numReports;
    private DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);

        reportNum = (TextView) findViewById(R.id.report_num);
        date = (TextView) findViewById(R.id.date);
        time = (TextView) findViewById(R.id.time);
        longit = (TextView) findViewById(R.id.longit);
        lat = (TextView) findViewById(R.id.lat);
        type = (TextView) findViewById(R.id.type);
        height = (TextView) findViewById(R.id.height);
        direction = (TextView) findViewById(R.id.direction);
        comments = (TextView) findViewById(R.id.comments);
        picture = (ImageView) findViewById(R.id.picture);

        home = (Button) findViewById(R.id.home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewReport.this, SignupActivity.class));
                finish();
            }
        });

        ref = FirebaseDatabase.getInstance().getReference();

        Bundle bundle = getIntent().getExtras();
        userId = bundle.getString("message");

        sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
        numReports = sp.getInt(userId, 0);

        numReports--;
        reportNum.setText("# " +Integer.toString(numReports));
        ref.child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("reports")
                .child(Integer.toString(numReports))
                .child("Date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String val = dataSnapshot.getValue(String.class);
                date.setText(val);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ref.child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("reports")
                .child(Integer.toString(numReports))
                .child("currentTime").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String val = dataSnapshot.getValue(String.class);
                time.setText(val);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ref.child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("reports")
                .child(Integer.toString(numReports))
                .child("Latitude").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                double lat2 = dataSnapshot.getValue(Double.class);
                lat.setText(Double.toString(lat2));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ref.child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("reports")
                .child(Integer.toString(numReports))
                .child("Longitude").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                double longit2 = dataSnapshot.getValue(Double.class);

                longit.setText(Double.toString(longit2));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        ref.child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("reports")
                .child(Integer.toString(numReports))
                .child("Category").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String x = dataSnapshot.getValue(String.class);
                type.setText(x);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ref.child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("reports")
                .child(Integer.toString(numReports))
                .child("Height").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String y = dataSnapshot.getValue(String.class);
                height.setText(y);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ref.child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("reports")
                .child(Integer.toString(numReports))
                .child("Angle").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long z = dataSnapshot.getValue(Long.class);
                direction.setText(Long.toString(z) + (char) 0x00B0);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ref.child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("reports")
                .child(Integer.toString(numReports))
                .child("Comment").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String val = dataSnapshot.getValue(String.class);
                comments.setText(val);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ref.child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("reports")
                .child(Integer.toString(numReports))
                .child("imageUrl").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String url = dataSnapshot.getValue(String.class);
                try {
                    picture.setImageBitmap(decodeFromFirebaseBase64(url));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }

}
