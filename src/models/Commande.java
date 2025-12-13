package models;

public class Commande {
    private int id;
    private int clientId;
    private String typeRetrait;
    private String adresseLivraison;
    private int zoneId;
    private double total;
    private boolean paye;
    private String statut;

    public Commande() {}

    public Commande(int id, int clientId, String typeRetrait, String adresseLivraison, int zoneId, double total, boolean paye) {
        this.id = id;
        this.clientId = clientId;
        this.typeRetrait = typeRetrait;
        this.adresseLivraison = adresseLivraison;
        this.zoneId = zoneId;
        this.total = total;
        this.paye = paye;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getClientId() { return clientId; }
    public void setClientId(int clientId) { this.clientId = clientId; }

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
