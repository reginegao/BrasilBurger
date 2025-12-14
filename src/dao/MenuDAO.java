package dao;

import models.Menu;
import config.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuDAO {

    public boolean addMenu(Menu menu) {
        String sql = "INSERT INTO menu (nom, image_url) VALUES (?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, menu.getNom());
            ps.setString(2, menu.getImageUrl());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) menu.setId(keys.getInt(1));
                }
                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    public List<Menu> getAllMenus() {
        List<Menu> list = new ArrayList<>();
        String sql = "SELECT id, nom, image_url FROM menu WHERE archived = FALSE";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Menu m = new Menu(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("image_url")
                );
                list.add(m);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public boolean updateMenu(Menu menu) {
        String sql = "UPDATE menu SET nom = ?, image_url = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, menu.getNom());
            ps.setString(2, menu.getImageUrl());
            ps.setInt(3, menu.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    public boolean deleteMenu(int id) {
        String sql = "UPDATE menu SET archived = TRUE WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean archiveMenu(int id) {
        return deleteMenu(id);
    }
}
