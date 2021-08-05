package com.franciscain.lautrerive.objets;

public class Utilisateur {
    private String nom;
    private String email;
    private Objectif[] objectifs;

    public Utilisateur(){}

    public Utilisateur(String nom, String email) {
        this.nom    = nom;
        this.email  = email;
    }

    public Utilisateur(String nom, String email, Objectif[] objectifs) {
        this.nom        = nom;
        this.email      = email;
        this.objectifs  = objectifs;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }
}
