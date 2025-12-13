package models;

public class Complement {
    private int id;
    private String nom;
    private double prix;
    private String imageUrl;
    private boolean archived;

    public Complement() {}

    public Complement(int id, String nom, double prix, String imageUrl) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.imageUrl = imageUrl;
        this.archived = false;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public boolean isArchived() { return archived; }
    public void setArchived(boolean archived) { this.archived = archived; }
}
