package dao;

import models.Commande;
import config.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommandeDAO {
    public boolean addCommande(Commande commande) {
        String sql = "INSERT INTO commande (client_id, type_retrait, adresse_livraison, zone_id, total, paye, statut) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, commande.getClientId());
            ps.setString(2, commande.getTypeRetrait());
            ps.setString(3, commande.getAdresseLivraison());
            if (commande.getZoneId() > 0) ps.setInt(4, commande.getZoneId()); else ps.setNull(4, Types.INTEGER);
            ps.setDouble(5, commande.getTotal());
            ps.setBoolean(6, commande.isPaye());
            ps.setString(7, commande.getStatut() == null ? "EN_COURS" : commande.getStatut());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) commande.setId(keys.getInt(1));
                }
                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    public boolean addCommande(Connection conn, Commande commande) throws SQLException {
        String sql = "INSERT INTO commande (client_id, type_retrait, adresse_livraison, zone_id, total, paye, statut) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, commande.getClientId());
            ps.setString(2, commande.getTypeRetrait());
            ps.setString(3, commande.getAdresseLivraison());
            if (commande.getZoneId() > 0) ps.setInt(4, commande.getZoneId()); else ps.setNull(4, Types.INTEGER);
            ps.setDouble(5, commande.getTotal());
            ps.setBoolean(6, commande.isPaye());
            ps.setString(7, commande.getStatut() == null ? "EN_COURS" : commande.getStatut());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) commande.setId(keys.getInt(1));
                }
                return true;
            }
        }
        return false;
    }

    public List<Commande> getAllCommandes() {
        List<Commande> result = new ArrayList<>();
        String sql = "SELECT id, client_id, type_retrait, adresse_livraison, zone_id, total, paye, statut FROM commande";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Commande c = new Commande();
                c.setId(rs.getInt("id"));
                c.setClientId(rs.getInt("client_id"));
                c.setTypeRetrait(rs.getString("type_retrait"));
                c.setAdresseLivraison(rs.getString("adresse_livraison"));
                c.setZoneId(rs.getInt("zone_id"));
                c.setTotal(rs.getDouble("total"));
                c.setPaye(rs.getBoolean("paye"));
                c.setStatut(rs.getString("statut"));
                result.add(c);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public java.util.List<Object[]> getItemsByCommandeId(int commandeId) {
        java.util.List<Object[]> items = new java.util.ArrayList<>();
        String sql = "SELECT * FROM commande_item WHERE commande_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, commandeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String produitType = null;
                    try { produitType = rs.getString("produit_type"); } catch (SQLException ignore) { produitType = null; }
                    int produitId = 0;
                    try { produitId = rs.getInt("produit_id"); if (rs.wasNull()) produitId = 0; } catch (SQLException e1) {
                        try { produitId = rs.getInt("item_id"); if (rs.wasNull()) produitId = 0; } catch (SQLException e2) {
                            try { produitId = rs.getInt("burger_id"); if (rs.wasNull()) produitId = 0; } catch (SQLException e3) { produitId = 0; }
                        }
                    }
                    int quantite = 0;
                    try { quantite = rs.getInt("quantite"); } catch (SQLException ignore) { quantite = 0; }
                    double prixUnitaire = 0;
                    try { prixUnitaire = rs.getDouble("prix_unitaire"); if (rs.wasNull()) prixUnitaire = 0; } catch (SQLException ignore) { prixUnitaire = 0; }

                    items.add(new Object[]{produitType, produitId, quantite, prixUnitaire});
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return items;
    }
    public boolean updateCommande(Commande commande) {
        String sql = "UPDATE commande SET client_id = ?, type_retrait = ?, adresse_livraison = ?, zone_id = ?, total = ?, paye = ?, statut = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, commande.getClientId());
            ps.setString(2, commande.getTypeRetrait());
            ps.setString(3, commande.getAdresseLivraison());
            if (commande.getZoneId() > 0) ps.setInt(4, commande.getZoneId()); else ps.setNull(4, Types.INTEGER);
            ps.setDouble(5, commande.getTotal());
            ps.setBoolean(6, commande.isPaye());
            ps.setString(7, commande.getStatut());
            ps.setInt(8, commande.getId());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
