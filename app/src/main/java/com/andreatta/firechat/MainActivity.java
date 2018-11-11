package com.andreatta.firechat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.andreatta.firechat.helpers.ConfiguracoesFirebase;
import com.andreatta.firechat.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText email,senha;
    Button btn;
    Usuario usuario;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.campoEmail);
        senha = findViewById(R.id.campoSenha);
        btn = findViewById(R.id.buttonEntrar);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    usuario = new Usuario();
                    usuario.setEmail(email.getText().toString());
                    usuario.setSenha(senha.getText().toString());
                    validarLogin();
                }catch (Exception ex){
                    Toast.makeText(MainActivity.this, "Verifique seus dados", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void validarLogin(){
        auth = ConfiguracoesFirebase.getFirebaseAuth();
        auth.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "SUCESSO", Toast.LENGTH_SHORT).show();
                    AbrirTelaPrincipal();
                }else{
                    Toast.makeText(MainActivity.this, "Erro ao realizar login", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void AbrirTelaPrincipal(){
        Intent intent = new Intent(MainActivity.this,Principal.class);
        startActivity(intent);
        //finish();
    }

    public void irCadastro(View view) {
        startActivity(new Intent(this,Cadastro.class));
    }
}
