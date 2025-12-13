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
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, paiement.getCommandeId());
            stmt.setDouble(2, paiement.getMontant());
            stmt.setString(3, paiement.getMethode());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Paiement getPaiementByCommande(int commandeId) {
        String sql = "SELECT * FROM paiement WHERE commande_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, commandeId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Paiement(
                        rs.getInt("id"),
                        rs.getInt("commande_id"),
                        rs.getTimestamp("date_paiement"),
                        rs.getDouble("montant"),
                        rs.getString("methode")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Paiement> getAllPaiements() {
        List<Paiement> paiements = new ArrayList<>();
        String sql = "SELECT * FROM paiement";

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Paiement p = new Paiement(
                        rs.getInt("id"),
                        rs.getInt("commande_id"),
                        rs.getTimestamp("date_paiement"),
                        rs.getDouble("montant"),
                        rs.getString("methode")
                );
                paiements.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paiements;
    }
}
