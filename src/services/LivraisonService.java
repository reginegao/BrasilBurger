package services;

import dao.CommandeDAO;
import models.Commande;

import java.util.ArrayList;
import java.util.List;

public class LivraisonService {

    private CommandeDAO commandeDAO;

    public LivraisonService() {
        this.commandeDAO = new CommandeDAO();
    }

    // Récupérer toutes les commandes à livrer
    public List<Commande> commandesALivrer() {
        List<Commande> commandes = commandeDAO.getAllCommandes();
        List<Commande> aLivrer = new ArrayList<>();
        for (Commande c : commandes) {
            if ("LIVRAISON".equalsIgnoreCase(c.getTypeRetrait()) && !"TERMINER".equalsIgnoreCase(c.getStatut())) {
                aLivrer.add(c);
            }
        }
        return aLivrer;
    }

    // Affecter les commandes à un livreur
    public void affecterCommande(int commandeId, int livreurId) {
        // Ici tu peux créer un DAO pour livraison_affectation
        // et stocker la commande avec le livreur
        System.out.println("Commande " + commandeId + " affectée au livreur " + livreurId);
    }
}
