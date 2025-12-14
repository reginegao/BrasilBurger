package models;

import java.time.LocalDateTime;
public class Commande {
    private int id;
    private int clientId;
    private LocalDateTime dateCreation;  
    private String typeRetrait;
    private String adresseLivraison;
    private int zoneId;
    private double total;
    private boolean paye;
    private String statut;

    public Commande() {}
    public Commande(int id, int clientId, LocalDateTime dateCreation, String typeRetrait, String adresseLivraison, int zoneId, double total, boolean paye, String statut) {
        this.id = id;
        this.clientId = clientId;
        this.dateCreation = dateCreation;
        this.typeRetrait = typeRetrait;
        this.adresseLivraison = adresseLivraison;
        this.zoneId = zoneId;
        this.total = total;
        this.paye = paye;
        this.statut = statut;
    }

    public Commande(int id, int clientId, String typeRetrait, String adresseLivraison, int zoneId, double total, boolean paye) {
        this.id = id;
        this.clientId = clientId;
        this.dateCreation = LocalDateTime.now();
        this.typeRetrait = typeRetrait;
        this.adresseLivraison = adresseLivraison;
        this.zoneId = zoneId;
        this.total = total;
        this.paye = paye;
        this.statut = "EN_COURS";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getClientId() { return clientId; }
    public void setClientId(int clientId) { this.clientId = clientId; }

    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }

    public String getTypeRetrait() { return typeRetrait; }
    public void setTypeRetrait(String typeRetrait) { this.typeRetrait = typeRetrait; }

    public String getAdresseLivraison() { return adresseLivraison; }
    public void setAdresseLivraison(String adresseLivraison) { this.adresseLivraison = adresseLivraison; }

    public int getZoneId() { return zoneId; }
    public void setZoneId(int zoneId) { this.zoneId = zoneId; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public boolean isPaye() { return paye; }
    public void setPaye(boolean paye) { this.paye = paye; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
}
