package com.franciscain.lautrerive.outils;

import com.franciscain.lautrerive.objets.Objectif;

public class ParDefaut {
    /**
     * Cette fonction crée des objectifs prédéfinis en les récupérant dans une base de donnée.
     * Les objectifs sont vierges.
     * @return
     */
    public static Objectif[] CreerObjectifsPredefinis(){
        String[] objectifsBruts = Database.objectifsPredefinis();
        Objectif[] sortie = new Objectif[objectifsBruts.length];
        for (int i=0; i< objectifsBruts.length; i++) {
            sortie[i]= new Objectif(objectifsBruts[i]);
        }
        return sortie;
    }
}
