package dao;

import models.Burger;
import config.Database;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BurgerDAO {
    public boolean addBurger(Burger burger) {
        String sql = "INSERT INTO burger (nom, prix, image_url) VALUES (?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, burger.getNom());
            ps.setDouble(2, burger.getPrix());
            ps.setString(3, burger.getImageUrl());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        burger.setId(keys.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    public List<Burger> getAllBurgers() {
        List<Burger> result = new ArrayList<>();
        String sql = "SELECT id, nom, prix, image_url FROM burger";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                double prix = rs.getDouble("prix");
                String image = rs.getString("image_url");

                Burger b = new Burger(id, nom, prix, image);
                result.add(b);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return result;
    }
    public boolean updateBurger(Burger burger) {
        String sql = "UPDATE burger SET nom = ?, prix = ?, image_url = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, burger.getNom());
            ps.setDouble(2, burger.getPrix());
            ps.setString(3, burger.getImageUrl());
            ps.setInt(4, burger.getId());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deleteBurger(int id) {
        String sql = "DELETE FROM burger WHERE id = ?";
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
    public Burger getBurgerById(int id) {
        String sql = "SELECT id, nom, prix, image_url FROM burger WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Burger(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getDouble("prix"),
                            rs.getString("image_url")
                    );
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
