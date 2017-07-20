package com.example.a591669.c_uasnotficiation;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

public class CameraActivity extends AppCompatActivity {

    private Button btnCamera;
    private ImageView imageView;
    int numReports;
    private DatabaseReference ref;
    SharedPreferences sp;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        //btnCamera = (Button) findViewById(R.id.btnCamera);
        imageView = (ImageView) findViewById(R.id.imageView);


        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,0);


        ref = FirebaseDatabase.getInstance().getReference();

        Bundle bundle = getIntent().getExtras();
        userId = bundle.getString("message");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        encodeBitmapAndSaveToFirebase(bitmap);
        Intent intent = new Intent(CameraActivity.this, Category.class);
        intent.putExtra("message", userId);
        startActivity(intent);
        finish();
        }

    public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        //numReports = ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("reports").
        sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);


        numReports = sp.getInt(userId, 0);
        ref.child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("reports")
                .child(Integer.toString(numReports))
                .child("imageUrl").setValue(imageEncoded);

    }



}
