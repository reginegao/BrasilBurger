package models;

public class CommandeItem {
    private int id;
    private int commandeId;
    private String type;
    private int itemId;
    private int quantite;

    public CommandeItem() {}

    public CommandeItem(int id, int commandeId, String type, int itemId, int quantite) {
        this.id = id;
        this.commandeId = commandeId;
        this.type = type;
        this.itemId = itemId;
        this.quantite = quantite;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCommandeId() { return commandeId; }
    public void setCommandeId(int commandeId) { this.commandeId = commandeId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
}
