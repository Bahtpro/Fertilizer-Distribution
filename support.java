package com.example.login;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class support extends AppCompatActivity {

    private EditText subjectEditText, messageEditText;
    private Button sendButton;
    private FirebaseFirestore db;

    // Fixed recipient email address
    private static final String RECIPIENT_EMAIL = "roticharon37@gmail.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.support);

        subjectEditText = findViewById(R.id.subjectEditText);
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);
        db = FirebaseFirestore.getInstance();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmailAndStoreData();
            }
        });
    }

    private void sendEmailAndStoreData() {
        String subject = subjectEditText.getText().toString().trim();
        String message = messageEditText.getText().toString().trim();

        // Check if subject or message is empty
        if (TextUtils.isEmpty(subject) || TextUtils.isEmpty(message)) {
            Toast.makeText(this, "Subject and message cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Send email
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{RECIPIENT_EMAIL});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        try {
            startActivity(Intent.createChooser(intent, ""));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(support.this, "No email client installed", Toast.LENGTH_SHORT).show();
        }

        // Store data in Firestore
        Map<String, Object> data = new HashMap<>();
        data.put("recipient", RECIPIENT_EMAIL);
        data.put("subject", subject);
        data.put("message", message);

        db.collection("support-requests")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(support.this, "Email sent and data stored successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(support.this, "Failed to store data in Firestore", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
