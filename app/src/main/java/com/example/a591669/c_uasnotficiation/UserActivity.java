package com.example.a591669.c_uasnotficiation;



        import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ProgressBar;
        import android.widget.TextView;

        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import android.content.pm.PackageManager;
        import android.location.Location;
        import android.support.v4.app.ActivityCompat;
        import android.support.v4.app.FragmentActivity;
        import android.os.Bundle;
        import android.support.v4.content.ContextCompat;
        import android.util.Log;

        import com.google.android.gms.location.FusedLocationProviderClient;
        import com.google.android.gms.location.LocationServices;
        import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.SupportMapFragment;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.MarkerOptions;
        import com.google.android.gms.tasks.OnSuccessListener;

        import java.util.Calendar;

public class UserActivity extends AppCompatActivity {

    private Button  signOutButton;   //Sign out Button is actually Notify Button!!!!!!
    private TextView helloUserText;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private Calendar c;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_notify);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();
        signOutButton = (Button) findViewById(R.id.sign_out);
        //helloUserText = (TextView) findViewById(R.id.text_user);
        ref = FirebaseDatabase.getInstance().getReference();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // if user is null launch login activity
                    startActivity(new Intent(UserActivity.this, LoginActivity.class));
                    finish();
                }else{
                    //helloUserText.setText("Hello  " + user.getEmail() +"");
                }



            }
        };

     signOutButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             c = Calendar.getInstance();
             int hour = c.get(Calendar.HOUR_OF_DAY);
             int minute = c.get(Calendar.MINUTE);
             int seconds = c.get(Calendar.SECOND);
             String h = Integer.toString(hour);
             if (hour < 10) {
                 h = "0" + h;
             }
             String m = Integer.toString(minute);
             if (minute < 10) {
                 m = "0" + m;
             }
             String s = Integer.toString(seconds);
             if (seconds < 10) {
                 s = "0" + s;
             }

             String time = h + ":" + m + ":" + s;
             //int seconds = c.get(Calendar.SECOND);
             Intent intent = new Intent(UserActivity.this, CameraActivity.class);
             ref.child("users")
                     .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("reports")
                     .child(Integer.toString(0))
                     .child("currentTime").setValue(time);


             startActivity(intent);
             finish(); //IS THIS NEEDED?
         }
     });

    }



    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
        }
        auth.removeAuthStateListener(authListener);
    }
}