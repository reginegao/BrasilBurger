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
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, complement.getNom());
            stmt.setDouble(2, complement.getPrix());
            stmt.setString(3, complement.getImageUrl());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Complement> getAllComplements() {
        List<Complement> complements = new ArrayList<>();
        String sql = "SELECT * FROM complement WHERE archived = FALSE";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Complement c = new Complement(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getDouble("prix"),
                        rs.getString("image_url")
                );
                complements.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return complements;
    }

    public boolean updateComplement(Complement complement) {
        String sql = "UPDATE complement SET nom = ?, prix = ?, image_url = ?, archived = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, complement.getNom());
            stmt.setDouble(2, complement.getPrix());
            stmt.setString(3, complement.getImageUrl());
            stmt.setBoolean(4, complement.isArchived());
            stmt.setInt(5, complement.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteComplement(int id) {
        String sql = "UPDATE complement SET archived = TRUE WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
