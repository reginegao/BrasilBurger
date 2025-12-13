package dao;

import models.Zone;
import config.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ZoneDAO {

    public boolean addZone(Zone zone) {
        String sql = "INSERT INTO zone (nom, prix_livraison) VALUES (?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, zone.getNom());
            stmt.setDouble(2, zone.getPrixLivraison());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    zone.setId(keys.getInt(1));
                }
                return true;
            }
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Zone> getAllZones() {
        List<Zone> zones = new ArrayList<>();
        String sql = "SELECT * FROM zone";

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Zone z = new Zone(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getDouble("prix_livraison")
                );
                zones.add(z);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return zones;
    }

    public Zone getZoneById(int id) {
        String sql = "SELECT * FROM zone WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Zone(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getDouble("prix_livraison")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
