package dao;

import models.Commande;
import config.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommandeDAO {

    public boolean addCommande(Commande commande) {
        String sql = "INSERT INTO commande (client_id, type_retrait, adresse_livraison, zone_id, total, paye) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, commande.getClientId());
            stmt.setString(2, commande.getTypeRetrait());
            stmt.setString(3, commande.getAdresseLivraison());
            if (commande.getZoneId() > 0) {
                stmt.setInt(4, commande.getZoneId());
            } else {
                stmt.setNull(4, java.sql.Types.INTEGER);
            }
            stmt.setDouble(5, commande.getTotal());
            stmt.setBoolean(6, commande.isPaye());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    commande.setId(keys.getInt(1));
                }
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Variante utilisant une connexion fournie (pour transaction)
    public boolean addCommande(Connection conn, Commande commande) throws SQLException {
        String sql = "INSERT INTO commande (client_id, type_retrait, adresse_livraison, zone_id, total, paye) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, commande.getClientId());
            stmt.setString(2, commande.getTypeRetrait());
            stmt.setString(3, commande.getAdresseLivraison());
            if (commande.getZoneId() > 0) {
                stmt.setInt(4, commande.getZoneId());
            } else {
                stmt.setNull(4, java.sql.Types.INTEGER);
            }
            stmt.setDouble(5, commande.getTotal());
            stmt.setBoolean(6, commande.isPaye());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    commande.setId(keys.getInt(1));
                }
                return true;
            }
        }

        return false;
    }

    public List<Commande> getAllCommandes() {
        List<Commande> commandes = new ArrayList<>();
        String sql = "SELECT * FROM commande";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Commande c = new Commande(
                        rs.getInt("id"),
                        rs.getInt("client_id"),
                        rs.getString("type_retrait"),
                        rs.getString("adresse_livraison"),
                        rs.getInt("zone_id"),
                        rs.getDouble("total"),
                        rs.getBoolean("paye")
                );

                commandes.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return commandes;
    }

    public boolean updateCommande(Commande commande) {
        String sql = "UPDATE commande SET client_id = ?, type_retrait = ?, adresse_livraison = ?, zone_id = ?, total = ?, paye = ?, statut = ? WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, commande.getClientId());
            stmt.setString(2, commande.getTypeRetrait());
            stmt.setString(3, commande.getAdresseLivraison());
            stmt.setInt(4, commande.getZoneId());
            stmt.setDouble(5, commande.getTotal());
            stmt.setBoolean(6, commande.isPaye());
            stmt.setString(7, commande.getStatut());
            stmt.setInt(8, commande.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
