package com.example.aplicationopa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText rFullName, rEmail, rPass, rPhone;
    Button rBtnRegis;
    TextView rTextLogin;
    FirebaseAuth auth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        rFullName = findViewById(R.id.regisName);
        rEmail = findViewById(R.id.Email_Regis);
        rPass = findViewById(R.id.Pass_Regis);
        rPhone = findViewById(R.id.Phone_Regis);
        rBtnRegis = findViewById(R.id.btnRegiss);
        rTextLogin = findViewById(R.id.Logintxt);

        auth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progree_bar);

//        if (auth.getCurrentUser() != null){
//            startActivity(new Intent(getApplicationContext(),Login.class));
//            finish();
//        }
    }

    public void Login (View view){
        Intent intent = new Intent(getApplicationContext(),Login.class);
        startActivity(intent);
    }

    public void btnRegis (View view){
        String email = rEmail.getText().toString().trim();
        String password = rPass.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            rEmail.setError("Email is required.");
            return;
        }

        if (TextUtils.isEmpty(password)){
            rPass.setError("Password is Required");
            return;
        }

        if (password.length() < 6){
            rPass.setError("Password must be minimum 6 Character");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        // Register process ti FireBase

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Register.this,"Register Succesfully",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),Login.class));
                } else {
                    Toast.makeText(Register.this,"Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });

    }
}
