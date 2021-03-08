package com.example.aplicationopa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class UserProfileActivity extends AppCompatActivity{

    private FirebaseUser user;
    private DatabaseReference reference;

    private String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users");
        UserID = user.getUid();

        final TextView Pfullname = (TextView) findViewById(R.id.profile_name);
        final TextView Pemail = (TextView) findViewById(R.id.profile_email);
        final TextView Pphone = (TextView) findViewById(R.id.profile_phone);
        final TextView Tname = (TextView) findViewById(R.id.nameTop);
        final TextView Tphone = (TextView) findViewById(R.id.phoneTop);

        reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel user = snapshot.getValue(UserModel.class);

                if (user != null){
                    String fullname = user.getName();
                    String email = user.getEmail();
                    String phone = user.getPhone();

                    Pfullname.setText(fullname);
                    Tname.setText(fullname);
                    Pemail.setText(email);
                    Pphone.setText(phone);
                    Tphone.setText(phone);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfileActivity.this,"Error !",Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void logout(View view){
        Toast.makeText(this,"Logout", Toast.LENGTH_SHORT).show();
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, LoginActivity.class));
        this.finish();
    }

    public void back(View view){
        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
        startActivity(intent);
    }
}