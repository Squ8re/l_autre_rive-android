package com.franciscain.lautrerive.objets;

import com.franciscain.lautrerive.outils.ParDefaut;

import java.util.ArrayList;
import java.util.Arrays;

public class Utilisateur {
    private String nom;
    private String email;
    public ArrayList<Objectif> objectifs;

    public Utilisateur(){}

    public Utilisateur(String nom, String email) {
        this.nom    = nom;
        this.email  = email;
        this.objectifs   = ParDefaut.CreerObjectifsPredefinis();
    }

    public Utilisateur(String nom, String email, ArrayList<Objectif> objectifs) {
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

    public ArrayList<Objectif> getObjectifs() {
        return objectifs;
    }

    public void addObjectif(Objectif objectif) {
        objectifs.add(objectif);
    }
}
