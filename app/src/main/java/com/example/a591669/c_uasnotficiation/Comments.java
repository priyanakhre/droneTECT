package com.example.a591669.c_uasnotficiation;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Comments extends AppCompatActivity {


    private EditText comments;

    private DatabaseReference ref;

    private Button continueButton;
    SharedPreferences sp;
    private String userId;
    int numReports;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        ref = FirebaseDatabase.getInstance().getReference();

        comments = (EditText) findViewById(R.id.editText);
        continueButton = (Button) findViewById(R.id.button2);

        Bundle bundle = getIntent().getExtras();
        userId = bundle.getString("message");


        continueButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String comment = comments.getText().toString().trim();
                sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);

                numReports = sp.getInt(userId, 0);

                ref.child("users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("reports")
                        .child(Integer.toString(numReports))
                        .child("Comment").setValue(comment);

                numReports++;
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt(userId, numReports);
                editor.commit();
                startActivity(new Intent(Comments.this, ConfirmationActivity.class));
                finish();
            }
        });


    }
}
