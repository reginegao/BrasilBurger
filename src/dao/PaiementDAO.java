package dao;

import models.Paiement;
import config.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaiementDAO {
    public boolean addPaiement(Paiement paiement) {
        String sql = "INSERT INTO paiement (commande_id, montant, methode) VALUES (?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, paiement.getCommandeId());
            ps.setDouble(2, paiement.getMontant());
            ps.setString(3, paiement.getMethode());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) paiement.setId(keys.getInt(1));
                }
                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    public Paiement getPaiementByCommande(int commandeId) {
        String sql = "SELECT id, commande_id, date_paiement, montant, methode FROM paiement WHERE commande_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, commandeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Paiement(
                            rs.getInt("id"),
                            rs.getInt("commande_id"),
                            rs.getTimestamp("date_paiement"),
                            rs.getDouble("montant"),
                            rs.getString("methode")
                    );
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public List<Paiement> getAllPaiements() {
        List<Paiement> list = new ArrayList<>();
        String sql = "SELECT id, commande_id, date_paiement, montant, methode FROM paiement";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Paiement p = new Paiement(
                        rs.getInt("id"),
                        rs.getInt("commande_id"),
                        rs.getTimestamp("date_paiement"),
                        rs.getDouble("montant"),
                        rs.getString("methode")
                );
                list.add(p);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
}
