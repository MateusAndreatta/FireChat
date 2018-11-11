package com.andreatta.firechat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.andreatta.firechat.helpers.ConfiguracoesFirebase;
import com.google.firebase.auth.FirebaseAuth;

public class Principal extends AppCompatActivity {

    Button btnSair;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        btnSair = findViewById(R.id.btn_sair);

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth = ConfiguracoesFirebase.getFirebaseAuth();
                auth.signOut();
                Intent intent = new Intent(Principal.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
