package models;

public class Quartier {
    private int id;
    private String nom;
    private int zoneId;

    public Quartier() {}

    public Quartier(int id, String nom, int zoneId) {
        this.id = id;
        this.nom = nom;
        this.zoneId = zoneId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public int getZoneId() { return zoneId; }
    public void setZoneId(int zoneId) { this.zoneId = zoneId; }
}
