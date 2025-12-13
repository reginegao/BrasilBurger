package services;

import dao.BurgerDAO;
import models.Burger;

import java.util.List;

public class BurgerService {

    private BurgerDAO burgerDAO;

    public BurgerService() {
        this.burgerDAO = new BurgerDAO();
    }

    // Ajouter un burger
    public boolean ajouterBurger(String nom, double prix, String imageUrl) {
        Burger burger = new Burger(nom, prix, imageUrl);
        return burgerDAO.addBurger(burger);
    }

    // Lister tous les burgers
    public List<Burger> listerBurgers() {
        return burgerDAO.getAllBurgers();
    }

    // Trouver un burger par ID
    public Burger trouverBurger(int id) {
        return burgerDAO.getBurgerById(id);
    }

    // Archiver un burger
    public boolean archiverBurger(int id) {
        Burger burger = burgerDAO.getBurgerById(id);
        if (burger != null) {
            burger.setArchived(true);
            return burgerDAO.updateBurger(burger);
        }
        return false;
    }
}
