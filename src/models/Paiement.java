package models;

import java.sql.Timestamp;

public class Paiement {
    private int id;
    private int commandeId;
    private Timestamp datePaiement;
    private double montant;
    private String methode;

    public Paiement() {}

    public Paiement(int id, int commandeId, double montant, String methode) {
        this.id = id;
        this.commandeId = commandeId;
        this.montant = montant;
        this.methode = methode;
    }

    public Paiement(int id, int commandeId, Timestamp datePaiement, double montant, String methode) {
        this.id = id;
        this.commandeId = commandeId;
        this.datePaiement = datePaiement;
        this.montant = montant;
        this.methode = methode;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCommandeId() { return commandeId; }
    public void setCommandeId(int commandeId) { this.commandeId = commandeId; }

    public Timestamp getDatePaiement() { return datePaiement; }
    public void setDatePaiement(Timestamp datePaiement) { this.datePaiement = datePaiement; }

    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }

    public String getMethode() { return methode; }
    public void setMethode(String methode) { this.methode = methode; }
}
