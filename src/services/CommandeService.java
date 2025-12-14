package services;

import config.Database;
import dao.CommandeDAO;
import dao.PaiementDAO;
import models.Commande;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CommandeService {

    private CommandeDAO commandeDAO;

    public CommandeService() {
        this.commandeDAO = new CommandeDAO();
    }

    // NOTE POUR DÉBUTANT :
    // Cette classe contient la logique simple liée aux commandes.
    // - Elle délègue l'accès à la base au `CommandeDAO`.
    // - Les méthodes sont volontairement claires et linéaires.

    // Créer une commande (délégué au DAO)
    public boolean creerCommande(Commande commande) {
        // Appelle le DAO pour insérer la commande en base
        return commandeDAO.addCommande(commande);
    }

    // Lister toutes les commandes
    public List<Commande> listerCommandes() {
        return commandeDAO.getAllCommandes();
    }

    // Retourne List<Object[]> = {produitType:String|null, produitId:Integer, quantite:Integer, prixUnitaire:Double}
    public java.util.List<Object[]> listerItems(int commandeId) {
        java.util.List<Object[]> items = commandeDAO.getItemsByCommandeId(commandeId);
        dao.BurgerDAO burgerDAO = new dao.BurgerDAO();
        for (Object[] row : items) {
            String pType = (String) row[0];
            Integer pId = (Integer) row[1];
            Double pu = (Double) row[3];
            if ((pu == null || pu.doubleValue() == 0) && "burger".equalsIgnoreCase(pType)) {
                try {
                    models.Burger b = burgerDAO.getBurgerById(pId.intValue());
                    if (b != null) row[3] = Double.valueOf(b.getPrix());
                } catch (Exception ignore) {}
            }
        }
        return items;
    }

    // Méthodes utilitaires courtes pour trouver et mettre à jour le statut

    public Commande trouverCommande(int id) {
        List<Commande> commandes = commandeDAO.getAllCommandes();
        for (Commande c : commandes) {
            if (c.getId() == id) return c;
        }
        return null;
    }

    public boolean mettreAJourStatut(int id, String statut) {
        Commande commande = trouverCommande(id);
        if (commande != null) {
            commande.setStatut(statut);
            return commandeDAO.updateCommande(commande);
        }
        return false;
    }

    public void ajouterItem(int commandeId, int burgerId, int quantite) {
        String sql = "INSERT INTO commande_item (commande_id, item_id, quantite) VALUES (?, ?, ?)";
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, commandeId);
            ps.setInt(2, burgerId);
            ps.setInt(3, quantite);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur ajout item !");
        }
    }

    // Créer une commande et insérer tous les items dans une seule transaction
    // items: List of Object[]{ String produitType, Integer produitId, Integer quantite }
    public boolean creerCommandeAvecItems(models.Commande commande, java.util.List<Object[]> items) {
        String insertItemSql = "INSERT INTO commande_item (commande_id, item_id, quantite) VALUES (?, ?, ?)";
        String insertItemAltSql = "INSERT INTO commande_item (commande_id, burger_id, quantite) VALUES (?, ?, ?)";
        String insertProduitSql = "INSERT INTO commande_item (commande_id, produit_type, produit_id, quantite, prix_unitaire) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection()) {
            try {
                conn.setAutoCommit(false);

                boolean created = commandeDAO.addCommande(conn, commande);
                if (!created) { conn.rollback(); return false; }

                String chosenSql = insertItemSql;
                java.sql.DatabaseMetaData md = conn.getMetaData();
                boolean hasItemId = false;
                try (java.sql.ResultSet cols = md.getColumns(null, null, "commande_item", "item_id")) {
                    if (cols.next()) hasItemId = true;
                }
                if (!hasItemId) {
                    boolean hasBurgerId = false;
                    try (java.sql.ResultSet cols2 = md.getColumns(null, null, "commande_item", "burger_id")) {
                        if (cols2.next()) { hasBurgerId = true; chosenSql = insertItemAltSql; }
                    }
                    if (!hasBurgerId) {
                        try (java.sql.ResultSet cols3 = md.getColumns(null, null, "commande_item", "produit_id")) {
                            if (cols3.next()) { chosenSql = insertProduitSql; hasBurgerId = true; }
                        }
                    }
                    if (!hasBurgerId) { conn.rollback(); System.out.println("La table 'commande_item' ne contient pas de colonne utilisable (item_id, burger_id, produit_id)."); return false; }
                }

                dao.BurgerDAO burgerDAO = new dao.BurgerDAO();
                try (PreparedStatement ps = conn.prepareStatement(chosenSql)) {
                    for (Object[] it : items) {
                        String pType = (String) it[0];
                        int pId = (Integer) it[1];
                        int pQty = (Integer) it[2];
                        if (chosenSql.equals(insertProduitSql)) {
                            double prix = 0;
                            try { if ("burger".equalsIgnoreCase(pType)) { models.Burger b = burgerDAO.getBurgerById(pId); if (b != null) prix = b.getPrix(); } } catch (Exception ignore) {}
                            ps.setInt(1, commande.getId());
                            ps.setString(2, pType);
                            ps.setInt(3, pId);
                            ps.setInt(4, pQty);
                            ps.setDouble(5, prix);
                        } else {
                            ps.setInt(1, commande.getId());
                            ps.setInt(2, pId);
                            ps.setInt(3, pQty);
                        }
                        ps.executeUpdate();
                    }
                }

                conn.commit();
                return true;
            } catch (SQLException e) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
                e.printStackTrace();
                return false;
            } finally {
                try { conn.setAutoCommit(true); } catch (SQLException ex) { ex.printStackTrace(); }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public models.Paiement getPaiementByCommandeId(int commandeId) {
        dao.PaiementDAO pdao = new dao.PaiementDAO();
        return pdao.getPaiementByCommande(commandeId);
    }
}
