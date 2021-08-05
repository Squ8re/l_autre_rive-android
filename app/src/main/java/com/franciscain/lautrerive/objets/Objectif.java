package com.franciscain.lautrerive.objets;

import java.util.ArrayList;
import java.util.Date;

public class Objectif {
    private String nom;
    private int joursSansIncidents;
    private ArrayList<Date> joursRéussis;

    public Objectif(String nom){
        this.nom = nom;
        this.joursRéussis = new ArrayList<>();
    }

    public Objectif(String nom, int joursSansIncidents, ArrayList<Date> joursRéussis) {
        this.nom = nom;
        this.joursSansIncidents = joursSansIncidents;
        this.joursRéussis = joursRéussis;
    }

    public ArrayList<Date> getJoursRéussis() {
        return joursRéussis;
    }

    public void addJoursRéussi(Date joursRéussi) {
        this.joursRéussis.add(joursRéussi);
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

    public void setJoursSansIncidents(int joursSansIncidents) {
        this.joursSansIncidents = joursSansIncidents;
    }
}
