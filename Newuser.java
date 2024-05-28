package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Newuser extends AppCompatActivity {

    private EditText firstNameField, lastNameField, phoneNumberField, emailField, passwordField, confirmPasswordField;
    private Button registerButton;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newuser);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        firstNameField = findViewById(R.id.user_name);
        lastNameField = findViewById(R.id.lnml);
        phoneNumberField = findViewById(R.id.lphone);
        emailField = findViewById(R.id.lemail);
        passwordField = findViewById(R.id.lpassword);
        confirmPasswordField = findViewById(R.id.lconpassword);
        registerButton = findViewById(R.id.signup);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        final String firstName = firstNameField.getText().toString().trim();
        final String lastName = lastNameField.getText().toString().trim();
        final String phoneNumber = phoneNumberField.getText().toString().trim();
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();
        String confirmPassword = confirmPasswordField.getText().toString().trim();

        if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(phoneNumber)
                || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(Newuser.this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmail(email)) {
            Toast.makeText(Newuser.this, "Invalid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidPhoneNumber(phoneNumber)) {
            Toast.makeText(Newuser.this, "Invalid phone number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidPassword(password)) {
            Toast.makeText(Newuser.this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(Newuser.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            Toast.makeText(Newuser.this, "User registered successfully", Toast.LENGTH_SHORT).show();

                            // Save additional user details to Firestore
                            saveUserToFirestore(firstName, lastName, phoneNumber);

                            // Redirect to login page
                            Intent intent = new Intent(Newuser.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Newuser.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveUserToFirestore(String firstName, String lastName, String phoneNumber) {
        Map<String, Object> farmer = new HashMap<>();
        farmer.put("firstName", firstName);
        farmer.put("lastName", lastName);
        farmer.put("phoneNumber", phoneNumber);

        mFirestore.collection("farmers")
                .document(mAuth.getCurrentUser().getUid())
                .set(farmer)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "User details saved to Firestore");
                        } else {
                            Log.w("TAG", "Error adding document", task.getException());
                        }
                    }
                });
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return Pattern.matches(emailPattern, email);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String phonePattern = "^[+]?[0-9]{10,13}$";
        return Pattern.matches(phonePattern, phoneNumber);
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 6;
    }
}
