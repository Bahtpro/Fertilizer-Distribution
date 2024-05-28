package com.example.login;


import static com.example.login.R.id.bookticket2;
import static com.example.login.R.id.card_view_add_car;
import static com.example.login.R.id.info;
import static com.example.login.R.id.supp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class a extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        androidx.cardview.widget.CardView addCard;
        addCard = (androidx.cardview.widget.CardView)findViewById(card_view_add_car);
        addCard.setOnClickListener(view -> startActivity(new Intent(a.this, MainActivity1.class)));
        androidx.cardview.widget.CardView vieorder;
        vieorder = (androidx.cardview.widget.CardView)findViewById(bookticket2);
        vieorder.setOnClickListener(view -> startActivity(new Intent(a.this, Listofcandidates.class)));
        androidx.cardview.widget.CardView inform;
        inform = (androidx.cardview.widget.CardView)findViewById(info);
        inform.setOnClickListener(view -> startActivity(new Intent(a.this, Details.class)));
        androidx.cardview.widget.CardView support;
        support = (androidx.cardview.widget.CardView)findViewById(supp);
        support.setOnClickListener(view -> startActivity(new Intent(a.this, support.class)));
    }
}
