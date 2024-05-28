package com.example.login;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Admin extends AppCompatActivity {

	private static final String ADMIN_USERNAME = "Admin";
	private static final String ADMIN_PASSWORD = "12345";

	private EditText usernameEditText, passwordEditText;
	private Button loginButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin);

		usernameEditText = findViewById(R.id.usernameEditText);
		passwordEditText = findViewById(R.id.passwordEditText);
		loginButton = findViewById(R.id.loginButton);

		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String username = usernameEditText.getText().toString().trim();
				String password = passwordEditText.getText().toString().trim();

				if (isValidUsername(username) && isValidPassword(password)) {
					if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
						// Login successful, start MainActivity
						startActivity(new Intent(Admin.this, Listofcandidates.class));
						finish(); // Close the current activity
					} else {
						// Login failed
						Toast.makeText(Admin.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
	}

	private boolean isValidUsername(String username) {
		if (TextUtils.isEmpty(username)) {
			usernameEditText.setError("Username cannot be empty");
			return false;
		} else if (!username.matches("[a-zA-Z]+")) {
			usernameEditText.setError("Username must contain only letters");
			return false;
		}
		return true;
	}

	private boolean isValidPassword(String password) {
		if (TextUtils.isEmpty(password)) {
			passwordEditText.setError("Password cannot be empty");
			return false;
		} else if (!password.matches("\\d{1,5}")) {
			passwordEditText.setError("Password must contain only digits and not exceed 5 values");
			return false;
		}
		return true;
	}
}
