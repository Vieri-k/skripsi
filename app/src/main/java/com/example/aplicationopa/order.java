package com.example.aplicationopa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class order extends AppCompatActivity {

    TextView loc, dist, Ototal, Tname, Tphone, Torigin, Tdestination, tx, tx2, UIDD;
    String l = "1";
    String li = "2";
    String O = "3";
    String D = "4";
    String location = "Source to ";
    double distance,  pay, org, destntn;
    Button btn;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String UserID;


    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<String>();
    private ArrayList permissions = new ArrayList();
    Timer timer1;

    private final static int ALL_PERMISSIONS_RESULT = 101;

    MyService servisku = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        //Customer
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users");
        UserID = user.getUid();

        Tname = (TextView) findViewById(R.id.namepf);
        Tphone = (TextView) findViewById(R.id.phonepf);

        reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if (user != null){
                    String fullname = user.getName();
                    String email = user.getEmail();
                    String phone = user.getPhone();

                    Tname.setText(fullname);
                    Tphone.setText(phone);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(order.this,"Error !",Toast.LENGTH_SHORT).show();
            }
        });


        // lokasi
        loc = findViewById(R.id.location);
        Intent i = getIntent();
        l = i.getStringExtra("total distance");
        if(l == null)
            l = "1";

        location = i.getStringExtra("start");
        loc.setText(location + "  " );

        //origin
//        org = Double.parseDouble(O);
//        O = i.getStringExtra("destination");
//        Torigin = findViewById(R.id.origin);
//        Torigin.setText(O);

        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        permissionsToRequest = findUnAskedPermissions(permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }


        tx = (TextView)findViewById(R.id.origin1);
        tx2 = (TextView)findViewById(R.id.origin2);
        UIDD = (TextView)findViewById(R.id.uid);

        Random rdm = new Random();
        UIDD.setText(String.valueOf(rdm.nextInt(10000000)));

        servisku = new MyService();

        int count = 100; //Declare as inatance variable

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {

                        servisku.getLocation(order.this);

                        if (servisku.canGetLocation()) {

                            double longitude = servisku.getLongitude();
                            double latitude = servisku.getLatitude();

                            tx.setText(Double.toString(longitude));
                            tx2.setText(Double.toString(latitude));

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {

                                @Override
                                public void run() {
//                                    tx.setText("Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude));
                                }
                            }, 1000);
                        } else {

                            servisku.showSettingsAlert();
                        }

                    }
                });
            }
        }, 0, 1000);


        // jarak
        distance = Double.parseDouble(l);
        dist = findViewById(R.id.km);
        dist.setText(String.format("%.2f",distance)+" KM");

        // harga total
//        pay = Double.parseDouble(li);
        li = i.getStringExtra("total_pay");
        Ototal = findViewById(R.id.total);
        Ototal.setText(li);

        loc = (TextView)findViewById(R.id.location);
        dist = (TextView)findViewById(R.id.km);
        Ototal = (TextView)findViewById(R.id.total);



        btn = findViewById(R.id.confirmbtnnn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                process();
                update();
            }
        });
    }

    private void process(){
        Map<String,Object> map=new HashMap<>();
        map.put("Name",Tname.getText().toString());
        map.put("Phone",Tphone.getText().toString());
        map.put("Lokasi",loc.getText().toString());
        map.put("Jarak",dist.getText().toString());
        map.put("Total",Ototal.getText().toString());
        map.put("Latitude",tx.getText().toString());
        map.put("Longitude",tx2.getText().toString());
        map.put("OrderID",UIDD.getText().toString());
        FirebaseDatabase.getInstance().getReference().child("order")
                .push()
                .setValue(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Tname.setText("");
                        Tphone.setText("");
                        loc.setText("");
                        dist.setText("");
                        Ototal.setText("");
                        tx.setText("");
                        tx2.setText("");
                        UIDD.setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Order Failed",Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void update(){
        Intent nt = new Intent(order.this, MainOjek.class);
        nt.putExtra("TotalPaying",li+"");
        nt.putExtra("OrderID",UIDD.getText().toString()+"");
        startActivity(nt);
    }

    public void backtomap(View view){
        Intent intent = new Intent(getApplicationContext(),home.class);
        startActivity(intent);
    }

    private ArrayList findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted)
            if (!hasPermission(perm)) {
                result.add(perm);
            }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(order.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        servisku.stopListener();
    }

}