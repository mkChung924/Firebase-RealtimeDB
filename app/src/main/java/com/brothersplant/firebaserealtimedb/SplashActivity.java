package com.brothersplant.firebaserealtimedb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import com.brothersplant.firebaserealtimedb.Model.App;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends AppCompatActivity {

    private DatabaseReference reference;

    private TextView first_name, last_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initFields();

        first_name.setText("Retrieving");
        last_name.setText("name...");

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                first_name.setText("Done");
                last_name.setText("!!");
            }
        },900);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                readAppDB();
            }
        },1400);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                moveToMainActivity();
            }
        },2100);
    }

    private void moveToMainActivity() {
        Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    private void initFields() {
        reference = FirebaseDatabase.getInstance().getReference();
        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
    }

    private void readAppDB() {
        reference.child("app").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    App app = snapshot.getValue(App.class);
                    String id = app.getId();
                    String firstName = app.getFirst_name();
                    String lastName = app.getLast_name();

                    Log.d("DEV", "id : " + id + ", first name: " + firstName + ", last_name : " + lastName);

                    first_name.setText(firstName);
                    last_name.setText(lastName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}