package com.example.aplicationopa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText rEmailLogin, rPassword;
    Button Loginbtn;
    TextView regisBtn;
    ProgressBar progressBar;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        rEmailLogin = findViewById(R.id.Email_Login);
        rPassword = findViewById(R.id.Pass_Login);
        progressBar = findViewById(R.id.progree_bar_login);

        auth = FirebaseAuth.getInstance();
    }

    public void regis (View view){
        Intent intent = new Intent(getApplicationContext(),Register.class);
        startActivity(intent);
    }

    public void btnLogin (View view){
        String email = rEmailLogin.getText().toString().trim();
        String password = rPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            rEmailLogin.setError("Email is required.");
            return;
        }

        if (TextUtils.isEmpty(password)){
            rPassword.setError("Password is Required");
            return;
        }

        if (password.length() < 6){
            rPassword.setError("Password must be minimum 6 Character");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        // Login Process

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Login.this,"Login Succesfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), home.class));
                } else {
                    Toast.makeText(Login.this,"Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}