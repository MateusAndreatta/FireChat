package com.andreatta.firechat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.andreatta.firechat.helpers.Permissao;
import com.andreatta.firechat.helpers.Preferences;

import java.util.Random;

public class Cadastro extends AppCompatActivity {

    String username,telefone,senha;
    EditText campoUsername,campoTelefone,campoSenha;

    private String[] permissoesNecessarias = new String[]{
            Manifest.permission.SEND_SMS
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        Permissao.validaPermisoes(1,this,permissoesNecessarias);

        campoUsername = findViewById(R.id.campoCodigo);
        campoTelefone = findViewById(R.id.editText);
        campoSenha = findViewById(R.id.editText2);
    }

    public void irValidacao(View view) {
        username = campoUsername.getText().toString();
        telefone = campoTelefone.getText().toString();
        senha    = campoSenha.getText().toString();

        Random random = new Random();
        int numeroRandomico = random.nextInt(9999 - 1000 ) + 1000;
        String token = String.valueOf(numeroRandomico);


        Preferences preferences = new Preferences(getApplicationContext());
        preferences.salvarUsuarioPreferencias(username,senha,telefone,token);

        boolean enviado = EnviarSMS("55" + telefone,"Coódigo de confirmação do FireChat: "+ token);
        if(enviado)
            startActivity(new Intent(Cadastro.this, Validacao.class));
    }

    private boolean EnviarSMS(String telefone, String msg){
        try{
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefone,null,msg,null,null);
            return true;
        }catch (Exception ex){
            Toast.makeText(this, "Falha ao enviar SMS", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantReults){
        super.onRequestPermissionsResult(requestCode,permissions,grantReults);

        for(int resultado: grantReults){
            if(resultado == PackageManager.PERMISSION_DENIED){
                alertaValidacaoPermissao();
            }
        }
    }

    private void alertaValidacaoPermissao(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissão negada!");
        builder.setMessage("Para usar o fireChat aceite as permições");
        builder.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}