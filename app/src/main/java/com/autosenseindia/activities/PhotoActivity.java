package com.autosenseindia.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.autosenseindia.R;
import com.autosenseindia.models.User;

public class PhotoActivity extends AppCompatActivity {

    private ImageView photo;
    private TextView timeStamp;
    private User user;
    private Bitmap userPhoto;
    private String photoTimeStamp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_layout);

        userPhoto = (Bitmap) getIntent().getParcelableExtra("photo");
        photoTimeStamp = getIntent().getStringExtra("timestamp");

        photo = findViewById(R.id.fullPhoto);
        timeStamp = findViewById(R.id.timeStamp);


        photo.setImageBitmap(userPhoto);
        timeStamp.setText(photoTimeStamp);
    }
}
