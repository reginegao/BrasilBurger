# CHANGELOG

## [Unreleased] - 2025-12-13

- Généralise l'insertion dans `commande_item` : supporte les schémas `item_id`, `burger_id` ou `produit_id`/`produit_type`/`prix_unitaire`.
- Transactionnalise la création de commande et l'ajout des items (création + items dans une seule transaction).
- Adaptations côté CLI : `App.java` stocke désormais les items comme `{produitType, produitId, quantite}`.
- Fichiers modifiés principaux :
  - `src/services/CommandeService.java`
  - `src/dao/CommandeDAO.java`
  - `src/App.java`

### Notes
- Tests rapides effectués : scénario de commande RETRAIT avec plusieurs items — commande créée et items insérés.
- Prochaine étape recommandée : pousser le dépôt vers un remote et ajouter des tests unitaires pour la création transactionnelle.
