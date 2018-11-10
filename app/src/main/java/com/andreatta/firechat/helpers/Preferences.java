package com.andreatta.firechat.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.session.MediaSession;

import java.util.HashMap;

/**
 * Created by Andreatta on 21/07/2018.
 */

public class Preferences {

    private Context context;
    private SharedPreferences preferences;
    private String NOME_ARQUIVO = "firebasePreference";
    private int MODE = 0;//Mode 0: Apenas o seu app tem acesso a esse arquivo
    private SharedPreferences.Editor editor;

    private String CHAVE_NOME = "nome";
    private String CHAVE_TELEFONE = "tel";
    private String CHAVE_SENHA = "senha";
    private String CHAVE_TOKEN = "token";

    public Preferences(Context context){

        this.context = context;
        this.preferences = context.getSharedPreferences(NOME_ARQUIVO,MODE);
        editor = preferences.edit();
    }

    public void salvarUsuarioPreferencias(String nome, String senha, String telefone, String token){
        editor.putString(CHAVE_NOME,nome);
        editor.putString(CHAVE_SENHA,senha);
        editor.putString(CHAVE_TELEFONE,telefone);
        editor.putString(CHAVE_TOKEN, token);
        editor.commit();
    }

    public HashMap<String,String> getDadosUsuario(){

        HashMap<String,String> dadosUser = new HashMap<>();
        dadosUser.put(CHAVE_NOME, preferences.getString(CHAVE_NOME,null));
        dadosUser.put(CHAVE_SENHA, preferences.getString(CHAVE_SENHA,null));
        dadosUser.put(CHAVE_TELEFONE, preferences.getString(CHAVE_TELEFONE,null));
        dadosUser.put(CHAVE_TOKEN, preferences.getString(CHAVE_TOKEN,null));
        return dadosUser;
    }
}
