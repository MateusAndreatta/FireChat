package com.andreatta.firechat;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.andreatta.firechat.helpers.ConfiguracoesFirebase;
import com.andreatta.firechat.helpers.Preferences;
import com.andreatta.firechat.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

public class Validacao extends AppCompatActivity {

    EditText campoCodigo;
    private Usuario usuario;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validacao);
        campoCodigo = findViewById(R.id.campoCodigo);
    }

    public void validar(View view) {
        String codigo = campoCodigo.getText().toString().trim();
        if(codigo.length() == 0){
            Toast.makeText(this, "Digite o codigo!", Toast.LENGTH_SHORT).show();
        }else{
            Preferences preferences = new Preferences(this);
            HashMap<String,String> dados = preferences.getDadosUsuario();
            if(codigo.equals(dados.get("token"))){
                Toast.makeText(this, "Validado com sucesso", Toast.LENGTH_SHORT).show();
                usuario = new Usuario();
                usuario.setEmail(dados.get("nome"));
                usuario.setSenha(dados.get("senha"));
                usuario.setTelefone(dados.get("tel"));
                cadastrarUsuario();
            }else{
                Toast.makeText(this, "Código incorreto!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void cadastrarUsuario(){
        auth = ConfiguracoesFirebase.getFirebaseAuth();
        auth.createUserWithEmailAndPassword(
                usuario.getEmail(),usuario.getSenha()
        ).addOnCompleteListener(Validacao.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Validacao.this, "Cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                    FirebaseUser usuarioFirebase = task.getResult().getUser();
                    usuario.setId( usuarioFirebase.getUid() );
                    usuario.salvar();

                    /* O usuario já está logado
                        Pode-se redirecionar ele para a tela principal
                        Realizando signOut somente para teste
                     * */

                    auth.signOut();
                    finish();
                }else{

                    String erro = "" ;

                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erro = "Senha muito fraca";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erro = "E-mail inválido";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erro = "Esse e-mail já está em uso!";
                    } catch (Exception e) {
                        erro = "Erro ao cadastrar usuário";
                        e.printStackTrace();
                    }
                    Toast.makeText(Validacao.this, "Problemas ao cadastrar", Toast.LENGTH_SHORT).show();
                    Toast.makeText(Validacao.this, erro, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
