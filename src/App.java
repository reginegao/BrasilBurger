import dao.BurgerDAO;
import dao.UserDAO;
import dao.ZoneDAO;
import dao.PaiementDAO;
import models.Burger;
import models.Commande;
import models.User;
import models.Zone;
import models.Paiement;
import services.CommandeService;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        BurgerDAO burgerDAO = new BurgerDAO();
        UserDAO userDAO = new UserDAO();
        ZoneDAO zoneDAO = new ZoneDAO();
        CommandeService commandeService = new CommandeService();
        Scanner scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            System.out.println("\n=== BRASIL BURGER ===");
            System.out.println("1. Voir le catalogue");
            System.out.println("2. Ajouter un burger");
            System.out.println("3. Faire une commande");
            System.out.println("4. Voir les commandes");
            System.out.println("5. Changer le statut d’une commande");
            System.out.println("6. Quitter");

            int choix = readInt(scanner, "Votre choix : ");

            switch (choix) {
                case 1:
                    System.out.println("\n=== CATALOGUE DES BURGERS ===");
                    List<Burger> burgers = burgerDAO.getAllBurgers();
                    for (Burger b : burgers) {
                        System.out.println(b.getId() + ". " + b.getNom() + " - " + (int)Math.round(b.getPrix()) + " FCFA (image: " + b.getImageUrl() + ")");
                    }
                    break;

                case 2:
                    System.out.println("\n=== AJOUTER UN BURGER ===");
                    String nom = readString(scanner, "Nom du burger : ");
                    double prix = readDouble(scanner, "Prix : ");
                    String imageUrl = readString(scanner, "URL de l'image : ");
                    Burger nb = new Burger(0, nom, prix, imageUrl);
                    if (burgerDAO.addBurger(nb)) {
                        System.out.println("Burger ajouté avec succès !");
                    } else {
                        System.out.println(" Erreur lors de l'ajout du burger.");
                    }
                    break;

                case 3:
                    faireCommande(scanner, burgerDAO, userDAO, zoneDAO, commandeService);
                    break;

                case 4:
                    System.out.println("\n--- LISTE DES COMMANDES ---");
                    for (Commande c : commandeService.listerCommandes()) {
                        System.out.println(
                            "Commande #" + c.getId() +
                            " | Client ID: " + c.getClientId() +
                            " | Total: " + (long)Math.round(c.getTotal()) + " FCFA" +
                            " | Payé: " + (c.isPaye() ? "Oui" : "Non") +
                            " | Statut: " + c.getStatut()
                        );

                        if ("LIVRAISON".equalsIgnoreCase(c.getTypeRetrait())) {
                            System.out.println("   Type: Livraison | Adresse: " + c.getAdresseLivraison() + " | Zone ID: " + c.getZoneId());
                        } else if ("RETRAIT".equalsIgnoreCase(c.getTypeRetrait())) {
                            System.out.println("   Type: Retrait sur place");
                        }

                        if (c.isPaye()) {
                            Paiement p = commandeService.getPaiementByCommandeId(c.getId());
                            if (p != null) {
                                System.out.println("   Paiement: " + p.getMontant() + " FCFA | Méthode: " + p.getMethode());
                            }
                        }

                        java.util.List<Object[]> items = commandeService.listerItems(c.getId());
                        if (items != null && !items.isEmpty()) {
                            for (Object[] it : items) {
                                String pType = it[0] != null ? (String) it[0] : "produit";
                                int pId = (Integer) it[1];
                                int qty = (Integer) it[2];
                                double pu = (Double) it[3];
                                String label = pType.toUpperCase();
                                String extra = "";
                                if ("burger".equalsIgnoreCase(pType)) {
                                    Burger b = burgerDAO.getBurgerById(pId);
                                    if (b != null) {
                                        label = b.getNom();
                                        extra = " (BURGER ID:" + pId + ")";
                                    }
                                }
                                long subtotal = Math.round(pu * qty);
                                System.out.println("   - " + label + extra + " x" + qty + " = " + subtotal + " FCFA");
                            }
                        }
                    }
                    break;

                case 5:
                    changerStatutCommande(scanner, commandeService);
                    break;

                case 6:
                    System.out.println("Au revoir !");
                    running = false;
                    break;

                default:
                    System.out.println("Choix invalide, réessayez.");
            }
        }
    }

    private static void faireCommande(Scanner scanner, BurgerDAO burgerDAO, UserDAO userDAO, ZoneDAO zoneDAO, CommandeService commandeService) {
        System.out.println("\n--- FAIRE UNE COMMANDE ---");

        List<User> clients = userDAO.getAllUsers();
        int clientId = -1;
        if (clients.isEmpty()) {
            System.out.println("Aucun client trouvé. Création automatique d'un client de démonstration.");
            User demo = new User(0, "Client", "Demo", "000000000", "demo@example.com", "", "client");
            if (userDAO.addUser(demo)) {
                clientId = demo.getId();
                System.out.println("Client démo créé (ID: " + clientId + ")");
            } else {
                System.out.println("Échec création client démo.");
                return;
            }
        } else {
            System.out.println("Clients disponibles :");
            for (User u : clients) {
                System.out.println(u.getId() + ". " + u.getNom() + " (" + u.getEmail() + ")");
            }

            while (true) {
                clientId = readInt(scanner, "Choisir l'ID client (0 = créer nouveau) : ");
                if (clientId == 0) {
                    String nomC = readString(scanner, "Nom : ");
                    String prenomC = readString(scanner, "Prénom : ");
                    String telC = readString(scanner, "Téléphone : ");
                    String emailC = readString(scanner, "Email : ");
                    String mdp = readString(scanner, "Mot de passe (optionnel) : ");
                    String role = readString(scanner, "Rôle (client/admin) [client] : ");
                    if (role.isEmpty()) role = "client";
                    User existing = userDAO.findByEmail(emailC);
                    if (existing != null) {
                        System.out.println("Email déjà enregistré, utilisation de l'utilisateur existant (ID: " + existing.getId() + ")");
                        clientId = existing.getId();
                        break;
                    }
                    User newUser = new User(0, nomC, prenomC, telC, emailC, mdp, role);
                    if (userDAO.addUser(newUser)) {
                        System.out.println("Client créé (ID: " + newUser.getId() + ")");
                        clientId = newUser.getId();
                        break;
                    } else {
                        System.out.println("Échec création client.");
                        return;
                    }
                } else {
                    clients = userDAO.getAllUsers();
                    boolean found = false;
                    for (User u : clients) if (u.getId() == clientId) { found = true; break; }
                    if (found) break;
                    System.out.println("ID invalide, réessayez.");
                }
            }
        }

        String typeCmd = readString(scanner, "Type (LIVRAISON/RETRAIT) : ").toUpperCase();
        String adresse = null;
        int zoneId = 0;
        if ("LIVRAISON".equalsIgnoreCase(typeCmd)) {
            adresse = readString(scanner, "Adresse de livraison : ");
            List<Zone> zones = zoneDAO.getAllZones();
            if (zones.isEmpty()) {
                System.out.println("Aucune zone définie. Vous pouvez en créer une.");
            } else {
                System.out.println("Zones disponibles :");
                for (Zone z : zones) {
                    System.out.println(z.getId() + ". " + z.getNom() + " (prix: " + z.getPrixLivraison() + ")");
                }
            }
            while (true) {
                int zid = readInt(scanner, "Choisir Zone ID (0 = créer nouvelle zone) : ");
                if (zid == 0) {
                    String nomZ = readString(scanner, "Nom de la zone : ");
                    double prixZ = readDouble(scanner, "Prix livraison : ");
                    Zone newZone = new Zone(0, nomZ, prixZ);
                    if (zoneDAO.addZone(newZone)) {
                        System.out.println("Zone créée (ID: " + newZone.getId() + ")");
                        zoneId = newZone.getId();
                        break;
                    } else {
                        System.out.println("Échec création zone.");
                    }
                } else {
                    Zone found = zoneDAO.getZoneById(zid);
                    if (found != null) { zoneId = zid; break; }
                    System.out.println("Zone ID invalide, réessayez.");
                }
            }
        }

        System.out.println("\n=== CHOISIR LES PRODUITS ===");
        List<Burger> burgersMenu = burgerDAO.getAllBurgers();
        if (burgersMenu.isEmpty()) {
            burgerDAO.addBurger(new Burger(0, "Classic", 400, null));
            burgerDAO.addBurger(new Burger(0, "Cheese", 500, null));
            burgerDAO.addBurger(new Burger(0, "Big Boy", 900, null));
            burgersMenu = burgerDAO.getAllBurgers();
        }
        for (Burger b : burgersMenu) {
            System.out.println(b.getId() + ". " + b.getNom() + " - " + (int)Math.round(b.getPrix()) + " FCFA");
        }
        System.out.println("0. Terminer");

        double total = 0;
        List<Object[]> items = new ArrayList<>();
        while (true) {
            int idBurger = readInt(scanner, "Choisir un burger : ");
            if (idBurger == 0) break;
            Burger b = burgerDAO.getBurgerById(idBurger);
            if (b == null) { System.out.println("Burger inexistant."); continue; }
            int qte = readInt(scanner, "Quantité : ");
            long sousTotal = Math.round(b.getPrix() * qte);
            total += sousTotal;
            items.add(new Object[]{"burger", idBurger, qte});
            System.out.println("Ajouté : " + b.getNom() + " ×" + qte + " = " + sousTotal + " FCFA");
        }

        System.out.println("\nTOTAL CALCULÉ : " + (long)Math.round(total) + " FCFA");
        boolean paye = readBoolean(scanner, "Payé ? (o/n) : ");

        Commande commande = new Commande(0, clientId, typeCmd, adresse, zoneId, total, paye);
        if (commandeService.creerCommandeAvecItems(commande, items)) {
            System.out.println("Commande créée (ID: " + commande.getId() + ")");
            if (!items.isEmpty()) System.out.println("Items ajoutés !");

            if (paye) {
                String methode = readString(scanner, "Méthode de paiement (WAVE / OM) : ");
                Paiement paiement = new Paiement(0, commande.getId(), commande.getTotal(), methode);
                PaiementDAO paiementDAO = new PaiementDAO();
                if (paiementDAO.addPaiement(paiement)) {
                    System.out.println("Paiement enregistré !");
                } else {
                    System.out.println("Échec enregistrement paiement.");
                }
            }
        } else {
            System.out.println("Erreur lors de la création de la commande.");
        }
    }

    private static void changerStatutCommande(Scanner scanner, CommandeService commandeService) {
        System.out.println("\n--- CHANGER LE STATUT D’UNE COMMANDE ---");

        int id = readInt(scanner, "ID de la commande : ");
        String statut = readString(scanner, "Nouveau statut (EN_COURS / LIVREE / ANNULEE) : ").toUpperCase();

        if (!statut.equals("EN_COURS") && !statut.equals("LIVREE") && !statut.equals("ANNULEE")) {
            System.out.println("Statut invalide.");
            return;
        }

        models.Commande c = commandeService.trouverCommande(id);
        if (c == null) {
            System.out.println(" Aucune commande trouvée avec l'ID : " + id);
            return;
        }
        System.out.println("Commande trouvée — statut actuel : " + c.getStatut());

        if (commandeService.mettreAJourStatut(id, statut)) {
            System.out.println(" Statut mis à jour avec succès ! (" + c.getId() + ")");
        } else {
            System.out.println(" Échec de la mise à jour. Vérifiez les contraintes en base (FK, etc.).");
        }
    }

    private static int readInt(Scanner scanner, String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Entrée invalide, entrez un nombre entier.");
            }
        }
    }

    private static double readDouble(Scanner scanner, String message) {
        while (true) {
            try {
                System.out.print(message);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Entrée invalide, entrez un nombre décimal.");
            }
        }
    }

    private static String readString(Scanner scanner, String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    private static boolean readBoolean(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String line = scanner.nextLine().trim().toLowerCase();
            if (line.isEmpty()) continue;
            if (line.startsWith("o") || line.startsWith("y") || line.equals("true")) return true;
            if (line.startsWith("n") || line.equals("false")) return false;
            System.out.println("Répondre par 'o' (oui) ou 'n' (non).");
        }
    }
}
