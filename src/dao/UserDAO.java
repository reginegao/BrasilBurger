package dao;

import models.User;
import config.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, nom, prenom, telephone, email, mot_de_passe, role FROM \"user\"";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User u = new User(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("telephone"),
                    rs.getString("email"),
                    rs.getString("mot_de_passe"),
                    rs.getString("role")
                );
                users.add(u);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return users;
    }
    public User findByEmail(String email) {
        String sql = "SELECT id, nom, prenom, telephone, email, mot_de_passe, role FROM \"user\" WHERE email = ?";
           try (Connection conn = Database.getConnection();
               PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("telephone"),
                            rs.getString("email"),
                            rs.getString("mot_de_passe"),
                            rs.getString("role")
                    );
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public User getUserById(int id) {
        String sql = "SELECT id, nom, prenom, telephone, email, mot_de_passe, role FROM \"user\" WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("telephone"),
                            rs.getString("email"),
                            rs.getString("mot_de_passe"),
                            rs.getString("role")
                    );
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public boolean addUser(User user) {
        String sql = "INSERT INTO \"user\" (nom, prenom, telephone, email, mot_de_passe, role) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getNom());
            ps.setString(2, user.getPrenom());
            ps.setString(3, user.getTelephone());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getMotDePasse());
            ps.setString(6, user.getRole());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        user.setId(keys.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
