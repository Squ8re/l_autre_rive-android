package com.franciscain.lautrerive.objets;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;

public class Objectif {
    private String nom;
    private int joursSansIncidents;
    private ArrayList<String> joursRéussis;

    public Objectif(){
    };

    public Objectif(String nom){
        this.nom = nom;
        this.joursRéussis = new ArrayList<>();
        joursSansIncidents = 0;
    }

    public Objectif(String nom, int joursSansIncidents, ArrayList<String> joursRéussis) {
        this.nom = nom;
        this.joursSansIncidents = joursSansIncidents;
        this.joursRéussis = joursRéussis;
    }

    public ArrayList<String> getJoursRéussis() {
        return joursRéussis;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getJoursSansIncidents() {
        return joursSansIncidents;
    }

    /**
     * Cette fonction permet d'avertir que la tache a été faite aujourd'hui
     * @param aujourdhui contient la date d'aujourd'hui
     */
    public void jourReussi(LocalDate aujourdhui){
        if(this.joursRéussis == null) {
            this.joursRéussis = new ArrayList<>();
            joursSansIncidents = 1;
            joursRéussis.add(aujourdhui.toString());
        }else if(aujourdhui.isEqual(LocalDate.parse(joursRéussis.get(joursRéussis.size()-1)))){
            return;
        }else if(aujourdhui.minusDays(1).isEqual(LocalDate.parse((joursRéussis.get(joursRéussis.size()-1)))))
            joursSansIncidents += 1;
        else
            joursSansIncidents = 0;
    }
}
