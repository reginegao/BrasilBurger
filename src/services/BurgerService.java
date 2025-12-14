package services;

import dao.BurgerDAO;
import models.Burger;
import utils.Input;

import java.util.List;

public class BurgerService {

    private BurgerDAO burgerDAO = new BurgerDAO();

  
    public void afficherCatalogue() {
        List<Burger> burgers = burgerDAO.getAllBurgers();

        if (burgers.isEmpty()) {
            System.out.println("Aucun burger disponible.");
            return;
        }

        System.out.println("\n=== CATALOGUE DES BURGERS ===");
        for (Burger b : burgers) {
            System.out.println(b.getId() + ". " + b.getNom() + " - " + b.getPrix() +
                    " FCFA (image: " + b.getImageUrl() + ")");
        }
    }

  
    public void ajouterBurgerConsole() {
        System.out.println("\n=== AJOUTER UN BURGER ===");

        String nom = Input.readString("Nom du burger : ");
        double prix = Input.readDouble("Prix : ");
        String url = Input.readString("URL de l'image : ");

        Burger b = new Burger(0, nom, prix, url);

        if (burgerDAO.addBurger(b)) {
            System.out.println(" Burger ajouté avec succès !");
        } else {
            System.out.println("Erreur lors de l'ajout.");
        }
    }
}
