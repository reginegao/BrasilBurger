package services;

import dao.UserDAO;
import models.User;

import java.util.List;

public class UserService {

    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    // Créer un utilisateur simple
    public boolean creerUtilisateur(String nom, String role) {
        User user = new User();
        user.setNom(nom);
        user.setRole(role);
        return userDAO.addUser(user);
    }

    // Rechercher un utilisateur par ID
    public User chercherUtilisateurParId(int id) {
        return userDAO.getUserById(id);
    }

    // Rechercher un utilisateur par nom (parcours simple)
    public User chercherUtilisateurParNom(String nom) {
        List<User> utilisateurs = userDAO.getAllUsers();
        for (User u : utilisateurs) {
            if (u.getNom() != null && u.getNom().equalsIgnoreCase(nom)) {
                return u;
            }
        }
        return null;
    }

    // Lister tous les utilisateurs
    public List<User> listerUtilisateurs() {
        return userDAO.getAllUsers();
    }
}
