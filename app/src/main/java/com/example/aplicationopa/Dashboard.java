package com.example.aplicationopa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.material.bottomsheet.BottomSheetDialog;

import static android.widget.Toast.LENGTH_SHORT;

public class Dashboard extends AppCompatActivity {

    ProgressBar progressBar;
    private boolean isGPS = false;
//    private android.widget.Button btnstart;
    private boolean isContinue = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


//
//        new turnonlocation(this).turnGPSOn(new turnonlocation.onGpsListener() {
//            @Override
//            public void gpsStatus(boolean isGPSEnable) {
//                // turn on GPS
//                isGPS = isGPSEnable;
//            }
//        });

        Button btnstart = findViewById(R.id.btnStart);
        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!isGPS) {
//                Toast.makeText(Dashboard.this,"Please turn on locaation",Toast.LENGTH_LONG).show();
//                return;
//                }
                isContinue = false;
                Intent intent = new Intent(Dashboard.this,home.class);
                startActivity(intent);
//                ToMap();
            }
        });
    }

    private void ToMap(){

    }

    public void profile(View view){
        Intent intent = new Intent(getApplicationContext(),userprofile.class);
        startActivity(intent);
    }

}