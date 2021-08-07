package com.franciscain.lautrerive.objets;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;

public class Objectif {
    private String nom;
    private int joursSansIncidents;
    private ArrayList<LocalDate> joursRéussis;

    public Objectif(){};

    public Objectif(String nom){
        this.nom = nom;
        this.joursRéussis = new ArrayList<>();
        joursSansIncidents = 0;
    }

    public Objectif(String nom, int joursSansIncidents, ArrayList<LocalDate> joursRéussis) {
        this.nom = nom;
        this.joursSansIncidents = joursSansIncidents;
        this.joursRéussis = joursRéussis;
    }

    public ArrayList<LocalDate> getJoursRéussis() {
        return joursRéussis;
    }

    public void addJourRéussi(LocalDate jourRéussi) {
        this.joursRéussis.add(jourRéussi);
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
        if(aujourdhui.minusDays(1).isEqual(joursRéussis.get(joursRéussis.size()-1)))
            joursSansIncidents += 1;
        else
            joursSansIncidents = 0;
        joursRéussis.add(aujourdhui);
    }
}
