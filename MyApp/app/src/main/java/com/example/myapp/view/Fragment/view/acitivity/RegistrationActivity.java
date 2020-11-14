package com.example.myapp.view.Fragment.view.acitivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText emailReg, passwordReg;
    private Button btnLoginReg, btnReg;

    private FirebaseAuth mAuth;

    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);

        emailReg = findViewById(R.id.Registration_Email);
        passwordReg = findViewById(R.id.Registration_Password);
        btnLoginReg = findViewById(R.id.btnlogin);
        btnReg = findViewById(R.id.btnregistration);


        btnLoginReg.setOnClickListener(this);
        btnReg.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnregistration:
                String email = emailReg.getText().toString().trim();
                String pass = passwordReg.getText().toString().trim();

                if(TextUtils.isEmpty(email))    {
                    emailReg.setError("Required field...");
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    passwordReg.setError("Required field...");
                    return;
                }
                if(passwordReg.length()<6){
                    passwordReg.setError("Password must be at least 6 characters");
                    return;
                }
                mDialog.setMessage("Registrationing...");
                mDialog.show();
                mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegistrationActivity.this, "Successful", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            mDialog.dismiss();
                        } else {
                            Toast.makeText(getApplicationContext(), "Registration Failed...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.btnlogin:
                btnLoginReg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                });
                break;
        }
    }
}
