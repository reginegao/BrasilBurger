package models;
public class CommandeItem {
    private int id;
    private int commandeId;
    private String produitType;    
    private int produitId;         
    private int quantite;
    private double prixUnitaire;  

    public CommandeItem() {}

    public CommandeItem(int id, int commandeId, String produitType, int produitId, int quantite, double prixUnitaire) {
        this.id = id;
        this.commandeId = commandeId;
        this.produitType = produitType;
        this.produitId = produitId;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCommandeId() { return commandeId; }
    public void setCommandeId(int commandeId) { this.commandeId = commandeId; }

    public String getProduitType() { return produitType; }
    public void setProduitType(String produitType) { this.produitType = produitType; }

    public int getProduitId() { return produitId; }
    public void setProduitId(int produitId) { this.produitId = produitId; }

    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }

    public double getPrixUnitaire() { return prixUnitaire; }
    public void setPrixUnitaire(double prixUnitaire) { this.prixUnitaire = prixUnitaire; }
}
