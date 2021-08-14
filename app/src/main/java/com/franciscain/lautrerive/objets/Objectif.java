package com.franciscain.lautrerive.objets;

import com.google.firebase.database.Exclude;

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

    @Exclude
    public int getStatut() {
        LocalDate aujourdhui = LocalDate.now();
        if(aujourdhui.isEqual(this.dernierJourReussi()))
            return 1;
        return 0;
    }

    /**
     * Cette fonction permet de vérifier si la tâche d'ajourd'hui a été faite.
     * @param aujourdhui contient la date d'aujourd'hui
     */
    @Exclude
    public void jourReussi(LocalDate aujourdhui){
        if(this.joursRéussis == null) { // Si c'est le premier jour
            this.joursRéussis = new ArrayList<>();
            joursSansIncidents = 1;
            joursRéussis.add(aujourdhui.toString());
        }else if(aujourdhui.isEqual(this.dernierJourReussi())){
            // nothing change !
        }else if(aujourdhui.minusDays(1).isEqual(this.dernierJourReussi())) {
            joursSansIncidents += 1;
            joursRéussis.add(aujourdhui.toString());
        }
        else {
            joursSansIncidents = 1;
            joursRéussis.add(aujourdhui.toString());
        }
    }

    @Exclude
    private LocalDate dernierJourReussi(){
        if(joursRéussis == null){
            return LocalDate.MIN;
        }else if(joursRéussis.isEmpty())
            return LocalDate.MIN;
        else
            return LocalDate.parse(joursRéussis.get(joursRéussis.size()-1));
    }
}
