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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText email,password;
    private Button btnRegistration;
    private Button btnLogin;

    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);

        email = findViewById(R.id.login_Email);
        password = findViewById(R.id.login_Password);
        btnLogin = findViewById(R.id.btn_Login);
        btnRegistration = findViewById(R.id.btn_Registration);

        btnLogin.setOnClickListener(this);
        btnRegistration.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_Login:
                String mEmail = email.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if (TextUtils.isEmpty(mEmail)) {
                    email.setError("Required field...");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    password.setError("Required field ...");
                    return;
                }
                if(password.length()<6){
                    password.setError("Password must be at least 6 characters");
                    return;
                }

                mDialog.setMessage("Processing...");
                mDialog.show();
                mAuth.signInWithEmailAndPassword(mEmail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                mDialog.dismiss();
                            } else {
                                Toast.makeText(getApplicationContext(), "Registration Failed...", Toast.LENGTH_SHORT).show();
                            }
                        }
                });
                break;
            case R.id.btn_Registration:
                btnRegistration.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
                    }
                });
                break;
        }
    }
}
