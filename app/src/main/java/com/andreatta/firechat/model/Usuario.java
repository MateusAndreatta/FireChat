package com.andreatta.firechat.model;

import com.andreatta.firechat.helpers.ConfiguracoesFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

/**
 * Created by Andreatta on 19/08/2018.
 */
public class Usuario {

    private String id,email,senha,telefone;

    public Usuario(){

    }

    public void salvar(){
        DatabaseReference reference = ConfiguracoesFirebase.getFirebase();
        reference.child("usuarios").child(getId()).setValue(this);
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
