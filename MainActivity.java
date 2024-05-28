package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText email, password;
    Button login,admin;
    TextView forget;
    TextView newuser;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        forget = findViewById(R.id.sforgotpassword);
        email = findViewById(R.id.semail);
        password = findViewById(R.id.spassword);
        login = findViewById(R.id.slogin);
        admin= findViewById(R.id.alogin);
        newuser = findViewById(R.id.snewuser);

        newuser.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Newuser.class);
            startActivity(intent);
        });
        admin.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Admin.class);
            startActivity(intent);
        });
        forget.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, changepassword.class);
            startActivity(intent);
        });

        login.setOnClickListener(v -> {
            String uemail = email.getText().toString().trim();
            String upass = password.getText().toString().trim();
            if (uemail.isEmpty() || upass.isEmpty()) {
                Toast.makeText(MainActivity.this, "Enter all details", Toast.LENGTH_SHORT).show();
            } else {
                progressDialog.setMessage("Verifying email and password");
                progressDialog.show();
                firebaseAuth.signInWithEmailAndPassword(uemail, upass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            finish();
                            Intent intent = new Intent(MainActivity.this, a.class);
                            startActivity(intent);
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Email or password is incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
