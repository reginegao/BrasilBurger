-- schema.sql (Postgres)
CREATE TABLE "user" (
  id SERIAL PRIMARY KEY,
  nom VARCHAR(100) NOT NULL,
  prenom VARCHAR(100),
  telephone VARCHAR(30),
  email VARCHAR(150) UNIQUE,
  mot_de_passe VARCHAR(255) NOT NULL,
  role VARCHAR(20) NOT NULL,
  created_at TIMESTAMP DEFAULT now()
);

CREATE TABLE burger (
  id SERIAL PRIMARY KEY,
  nom VARCHAR(150) NOT NULL,
  description TEXT,
  prix NUMERIC(10,2) NOT NULL,
  image_url TEXT,
  archived BOOLEAN DEFAULT FALSE
);

CREATE TABLE complement (
  id SERIAL PRIMARY KEY,
  nom VARCHAR(150) NOT NULL,
  prix NUMERIC(10,2) NOT NULL,
  image_url TEXT,
  archived BOOLEAN DEFAULT FALSE
);

CREATE TABLE menu_ (
  id SERIAL PRIMARY KEY,
  nom VARCHAR(150) NOT NULL,
  image_url TEXT,
  archived BOOLEAN DEFAULT FALSE
);

CREATE TABLE menu_burger (
  menu_id INT REFERENCES menu_(id) ON DELETE CASCADE,
  burger_id INT REFERENCES burger(id) ON DELETE CASCADE,
  PRIMARY KEY (menu_id, burger_id)
);

CREATE TABLE menu_complement (
  menu_id INT REFERENCES menu_(id) ON DELETE CASCADE,
  complement_id INT REFERENCES complement(id) ON DELETE CASCADE,
  PRIMARY KEY (menu_id, complement_id)
);

CREATE TABLE zone (
  id SERIAL PRIMARY KEY,
  nom VARCHAR(150) NOT NULL,
  prix_livraison NUMERIC(8,2) DEFAULT 0
);

CREATE TABLE quartier (
  id SERIAL PRIMARY KEY,
  nom VARCHAR(150) NOT NULL,
  zone_id INT REFERENCES zone(id)
);

CREATE TABLE commande (
  id SERIAL PRIMARY KEY,
  client_id INT REFERENCES "user"(id),
  date_creation TIMESTAMP DEFAULT now(),
  statut VARCHAR(30) DEFAULT 'EN_COURS',
  type_retrait VARCHAR(20),
  adresse_livraison TEXT,
  zone_id INT REFERENCES zone(id),
  total NUMERIC(12,2) DEFAULT 0,
  paye BOOLEAN DEFAULT FALSE
);

CREATE TABLE commande_item (
  id SERIAL PRIMARY KEY,
  commande_id INT REFERENCES commande(id) ON DELETE CASCADE,
  produit_type VARCHAR(10) NOT NULL,
  produit_id INT NOT NULL,
  quantite INT DEFAULT 1,
  prix_unitaire NUMERIC(10,2) NOT NULL
);

CREATE TABLE commande_item_complement (
  commande_item_id INT REFERENCES commande_item(id) ON DELETE CASCADE,
  complement_id INT REFERENCES complement(id) ON DELETE CASCADE,
  PRIMARY KEY (commande_item_id, complement_id)
);

CREATE TABLE paiement (
  id SERIAL PRIMARY KEY,
  commande_id INT REFERENCES commande(id) UNIQUE,
  date_paiement TIMESTAMP DEFAULT now(),
  montant NUMERIC(12,2),
  methode VARCHAR(20)
);

CREATE TABLE livraison_affectation (
  id SERIAL PRIMARY KEY,
  commande_id INT REFERENCES commande(id),
  livreur_id INT REFERENCES "user"(id),
  date_affectation TIMESTAMP DEFAULT now()
);
