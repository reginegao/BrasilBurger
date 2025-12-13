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
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, burger.getNom());
            stmt.setDouble(2, burger.getPrix());
            stmt.setString(3, burger.getImageUrl());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Burger> getAllBurgers() {
        List<Burger> burgers = new ArrayList<>();
        String sql = "SELECT * FROM burger";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Burger b = new Burger(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getDouble("prix"),
                        rs.getString("image_url")
                );
                burgers.add(b);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return burgers;
    }

    public boolean updateBurger(Burger burger) {
        String sql = "UPDATE burger SET nom = ?, prix = ?, image_url = ? WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, burger.getNom());
            stmt.setDouble(2, burger.getPrix());
            stmt.setString(3, burger.getImageUrl());
            stmt.setInt(4, burger.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBurger(int id) {
        String sql = "DELETE FROM burger WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Burger getBurgerById(int id) {
        String sql = "SELECT * FROM burger WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Burger(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getDouble("prix"),
                        rs.getString("image_url")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
