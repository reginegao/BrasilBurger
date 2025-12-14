package dao;

import config.Database;
import models.Complement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComplementDAO {
    public boolean addComplement(Complement complement) {
        String sql = "INSERT INTO complement (nom, prix, image_url) VALUES (?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, complement.getNom());
            ps.setDouble(2, complement.getPrix());
            ps.setString(3, complement.getImageUrl());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) complement.setId(keys.getInt(1));
                }
                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    public List<Complement> getAllComplements() {
        List<Complement> list = new ArrayList<>();
        String sql = "SELECT id, nom, prix, image_url FROM complement WHERE archived = FALSE";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Complement c = new Complement(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getDouble("prix"),
                        rs.getString("image_url")
                );
                list.add(c);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
    public boolean updateComplement(Complement complement) {
        String sql = "UPDATE complement SET nom = ?, prix = ?, image_url = ?, archived = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, complement.getNom());
            ps.setDouble(2, complement.getPrix());
            ps.setString(3, complement.getImageUrl());
            ps.setBoolean(4, complement.isArchived());
            ps.setInt(5, complement.getId());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    public boolean deleteComplement(int id) {
        String sql = "UPDATE complement SET archived = TRUE WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
