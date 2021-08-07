package com.franciscain.lautrerive.outils;

import android.widget.Toast;

import com.franciscain.lautrerive.objets.Objectif;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ParDefaut {
    private static final boolean hardCode = true;
    private static final String[] objectifsBrut =
            {"Douche froide",
                    "Alcool",
                    "Sucre",
                    "Graisse",
                    "Sport",
                    "Ecrans"};

    /**
     * Cette fonction crée des objectifs prédéfinis en les récupérant dans une base de donnée.
     * Les objectifs sont vierges.
     * @return
     */
    public static ArrayList<Objectif> CreerObjectifsPredefinis(){
        if(!hardCode){
            String[] objectifsBrut = Database.objectifsPredefinis();
        }
        ArrayList<Objectif> sortie = new ArrayList<>();
        for (int i=0; i< objectifsBrut.length; i++) {
            sortie.add(new Objectif(objectifsBrut[i]));
        }
        if(sortie == null){
            sortie.add(new Objectif("Radis rouges"));
        }
        if(objectifsBrut == null){
            sortie.add(new Objectif("Pommes rouges"));
        }
        return sortie;
    }
}
