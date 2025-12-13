package dao;

import models.Menu;
import config.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuDAO {

    public boolean addMenu(Menu menu) {
        String sql = "INSERT INTO menu_ (nom, image_url) VALUES (?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, menu.getNom());
            stmt.setString(2, menu.getImageUrl());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Menu> getAllMenus() {
        List<Menu> list = new ArrayList<>();
        String sql = "SELECT * FROM menu_ WHERE archived = false";

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Menu m = new Menu(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("image_url")
                );
                list.add(m);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public Menu getMenuById(int id) {
        String sql = "SELECT * FROM menu_ WHERE id = ? AND archived = false";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Menu(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("image_url")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean archiveMenu(int id) {
        String sql = "UPDATE menu_ SET archived = true WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
