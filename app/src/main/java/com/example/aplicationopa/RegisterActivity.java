package com.example.aplicationopa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText rFullName, rEmail, rPass, rPhone;
    Button rBtnRegis;
    TextView rTextLogin;
    FirebaseAuth auth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        auth = FirebaseAuth.getInstance();

        rFullName = findViewById(R.id.fullname);
        rEmail = findViewById(R.id.email);
        rPass = findViewById(R.id.password);
        rPhone = findViewById(R.id.phone);
        rTextLogin = findViewById(R.id.Logintxt);

        rBtnRegis = findViewById(R.id.btnRegiss);

        progressBar = findViewById(R.id.progres_bar_register);

//        if (auth.getCurrentUser() != null){
//            startActivity(new Intent(getApplicationContext(),Login.class));
//            finish();
//        }
    }

    public void Login (View view){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    public void btnRegis (View view){

        String userName = rFullName.getText().toString().trim();
        String phone = rPhone.getText().toString().trim();

        String email = rEmail.getText().toString().trim();
        String password = rPass.getText().toString().trim();

        if (TextUtils.isEmpty(userName)){
            rFullName.setError("Full Name is Required");
            rFullName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)){
            rEmail.setError("Email is Required.");
            rPhone.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            rEmail.setError("Please provide valid email");
            rEmail.requestFocus();
            return;
        }

        if (password.length() < 6){
            rPass.setError("Password must be minimum 6 Character");
            return;
        }
        if (TextUtils.isEmpty(password)){
            rPass.setError("Password is Required");
            rPhone.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(phone)){
            rPhone.setError("Phone Number is Required");
            rPhone.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        // Register process ti FireBase
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Map<String,Object> map=new HashMap<>();
                            map.put("name",rFullName.getText().toString());
                            map.put("Email",rEmail.getText().toString());
                            map.put("Phone",rPhone.getText().toString());
                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        if (user.isEmailVerified()){
                                            Toast.makeText(RegisterActivity.this,"User has been registered successfully",Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                            progressBar.setVisibility(View.GONE);
                                        } else {
                                            user.sendEmailVerification();
                                            Toast.makeText(RegisterActivity.this, "Check your email to verify account",Toast.LENGTH_LONG).show();
                                            progressBar.setVisibility(View.GONE);
                                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                        }

                                    } else {
                                        Toast.makeText(RegisterActivity.this,"Failed to register",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(RegisterActivity.this,"Failed Register",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

    }
}
