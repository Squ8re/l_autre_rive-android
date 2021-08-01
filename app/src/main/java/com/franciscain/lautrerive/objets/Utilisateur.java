package com.franciscain.lautrerive.objets;

public class Utilisateur {
    private String nom;
    private String email;

    public Utilisateur(){}

    public Utilisateur(String nom, String email) {
        this.nom = nom;
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }
}
