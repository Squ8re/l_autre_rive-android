package com.franciscain.lautrerive.objets;

import com.franciscain.lautrerive.outils.Database;
import com.franciscain.lautrerive.outils.ParDefaut;
import com.google.firebase.database.Exclude;

import java.time.LocalDate;
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

    @Exclude
    public void addObjectif(Objectif objectif) {
        objectifs.add(objectif);
        updateUser();
    }

    @Exclude
    public void objectifReussi(String nomObjectif, LocalDate date){
        objectifs.get(this.indexOfName(nomObjectif)).jourReussi(date);
        updateUser();
    }

    /**
     * Retourne l'indice de l'objectif portant le nom nomSecondaire
     * @param nomSecondaire nom d'un objectif
     * @return index de l'objectif avec nomSecondaire, -1 sinon.
     */
    @Exclude
    private int indexOfName(String nomSecondaire){
        for(int i = 0; i< objectifs.size(); i++){
            if(objectifs.get(i).getNom().equals(nomSecondaire))
                return i;
        }
        return -1;
    }

    @Exclude
    private void updateUser(){
        Database.updateObjectifs(objectifs);
    }
}
