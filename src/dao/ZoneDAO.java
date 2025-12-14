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
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, zone.getNom());
            ps.setDouble(2, zone.getPrixLivraison());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        zone.setId(keys.getInt(1));
                    }
                }
                return true;
            }
            return false;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    public List<Zone> getAllZones() {
        List<Zone> zones = new ArrayList<>();
        String sql = "SELECT id, nom, prix_livraison FROM zone";

           try (Connection conn = Database.getConnection();
               PreparedStatement ps = conn.prepareStatement(sql);
               ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Zone z = new Zone(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getDouble("prix_livraison")
                );
                zones.add(z);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return zones;
    }
    public Zone getZoneById(int id) {
        String sql = "SELECT id, nom, prix_livraison FROM zone WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Zone(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getDouble("prix_livraison")
                    );
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
