package com.example.aplicationopa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;

public class DashboardActivity extends AppCompatActivity {

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
                Intent intent = new Intent(DashboardActivity.this, HomeActivity.class);
                startActivity(intent);
//                ToMap();
            }
        });
    }

    private void ToMap(){

    }

    public void profile(View view){
        Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
        startActivity(intent);
    }

}