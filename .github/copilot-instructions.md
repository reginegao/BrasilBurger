<!-- Instructions concises pour agents IA travaillant sur BrasilBurger -->
# BrasilBurger — Directives pour assistants de code

Objectif rapide
- Ce dépôt contient une petite application CLI Java (gestion de commandes).
- Entrée principale : `src/App.java` — interface console basée sur `Scanner`.

Architecture & flux de données (big picture)
- UI/CLI : `src/App.java` (boucle principale, menus). Voir cas d'usage «faire une commande».
- Couche service : `src/services/*` — orchestration, transactions et logique métier (ex. `CommandeService.creerCommandeAvecItems` gère transaction et détection du schéma `commande_item`).
- Couche DAO : `src/dao/*` — accès JDBC direct via `config/Database.getConnection()`; chaque DAO mappe `ResultSet` → objets `src/models/*`.
- Modèles : `src/models/*` — POJO simples pour Burger, Commande, Paiement, User, Zone.
- Utilitaires : `src/utils/*` — aide à la saisie/validation (utilisés par `App.java`).

Points critiques à connaître
- La connexion DB est codée dans `src/config/Database.java`. Modifier ce fichier mettra à jour les credentials/URL utilisés partout.
- Le projet dépend du pilote PostgreSQL présent sous `lib/postgresql-42.7.7.jar`.
- Compilation/run se fait depuis `src` (exemples ci‑dessous). Les DAO utilisent JDBC et PreparedStatement — évitez de remplacer par des frameworks ORM sans mise à jour complète.
- `CommandeService.creerCommandeAvecItems` détecte dynamiquement la colonne disponible dans `commande_item` (item_id | burger_id | produit_id) via DatabaseMetaData : conservez cette robustesse si vous modifiez la table.

Commandes utiles (depuis `src`)
```bash
javac -cp ".;../lib/postgresql-42.7.7.jar" config/*.java models/*.java dao/*.java services/*.java utils/*.java App.java
java  -cp ".;../lib/postgresql-42.7.7.jar" App
```

Conventions et patterns spécifiques
- DAO: méthode `addX`, `getAllX`, `getXById`, `updateX`, `deleteX`. Elles retournent booléens ou objets `models`.
- Services: encapsulent la logique métier et peuvent appeler DAO avec une `Connection` passée pour transactions (ex. `commandeDAO.addCommande(conn, commande)`).
- Transactions: utilisées explicitement dans `CommandeService` — effectuez commit/rollback manuellement comme l'existant.
- Affichage/Entrée: `App.java` gère les interactions utilisateur. Respectez ses messages et types d'entrée (int/double/string/o/n) pour compatibilité.

Fichiers à consulter en priorité
- Point d'entrée CLI : [src/App.java](src/App.java)
- Config DB : [src/config/Database.java](src/config/Database.java)
- Exemple DAO : [src/dao/BurgerDAO.java](src/dao/BurgerDAO.java)
- Exemple Service transactionnel : [src/services/CommandeService.java](src/services/CommandeService.java)
- Modèles : [src/models](src/models)

Recommandations pour les PRs automatiques
- Préserver la logique de détection de colonnes dans `commande_item` ou fournir une migration DB claire.
- Ne pas exposer de secrets dans le repo : si vous devez changer `Database.java`, proposez d'abord une PR pour externaliser la config (variables d'environnement) et documenter la rotation.
- Tests manuels rapides: compiler depuis `src` puis lancer `java` et parcourir les menus (créer un client, créer commande, lister commandes).

Si une section est ambiguë
- Demandez un extrait de schéma de la base (DDL) ou un dump de la table `commande_item` si vous travaillez sur insertion d'items.

Fin — demander feedback si quelque chose manque.
