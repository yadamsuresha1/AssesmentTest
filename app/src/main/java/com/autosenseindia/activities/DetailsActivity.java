package com.autosenseindia.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.autosenseindia.R;
import com.autosenseindia.models.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailsActivity extends AppCompatActivity {

    private TextView name, designation, city, pincode, dateOfJoining, salary;

    private User user;

    private ImageView caputrePhoto;
    private ImageView editPhoto;

    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_REQUEST = 1888;

    private boolean isPhotoCaptured;

    private Button locateMe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_layout);

        user = (User) getIntent().getSerializableExtra("user");

        initViews();

        editPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(DetailsActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(DetailsActivity.this, new String[]{Manifest.permission.CAMERA},
                            MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });

        caputrePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isPhotoCaptured) {

                    Intent photoActivity = new Intent(DetailsActivity.this, PhotoActivity.class);
                    photoActivity.putExtra("photo", user.getUserPhoto());
                    photoActivity.putExtra("timestamp", user.getPhotoTimeStamp());
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(DetailsActivity.this,
                            caputrePhoto,
                            ViewCompat.getTransitionName(caputrePhoto));

                    startActivity(photoActivity, options.toBundle());

                } else {
                    Toast.makeText(DetailsActivity.this, "Please take photo", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (user.getUserPhoto() != null) {
            caputrePhoto.setImageBitmap(user.getUserPhoto());
        }

        locateMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent placesActivity = new Intent(DetailsActivity.this, PlacesActivity.class);
                placesActivity.putExtra("city", user.getCity());
                placesActivity.putExtra("pincode", user.getPincode());
                startActivity(placesActivity);
            }
        });
    }

    private void initViews() {
        name = findViewById(R.id.name);
        designation = findViewById(R.id.designation);
        city = findViewById(R.id.city);
        pincode = findViewById(R.id.pincode);
        dateOfJoining = findViewById(R.id.dateOfJoining);
        salary = findViewById(R.id.salary);

        caputrePhoto = findViewById(R.id.photo);
        editPhoto = findViewById(R.id.editPhoto);

        locateMe = findViewById(R.id.locateMe);

        name.setText(user.getName());
        designation.setText(user.getDesignation());
        city.setText(user.getCity());
        pincode.setText(user.getPincode());
        dateOfJoining.setText(user.getDateOfJoin());
        salary.setText(user.getSalary());
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new
                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }

        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            user.setUserPhoto(photo);
            String timeStamp =
                    new SimpleDateFormat("yyyyMMdd_HHmmss",
                            Locale.getDefault()).format(new Date());
            String imageFileName = "IMG_" + timeStamp;

            user.setPhotoTimeStamp(imageFileName);

            caputrePhoto.setImageBitmap(photo);
            isPhotoCaptured = true;
        }
    }
}
