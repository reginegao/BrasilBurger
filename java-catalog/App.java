package com.brasilburger;

import java.util.ArrayList;
import java.util.List;

public class App {
    public static class Burger {
        String nom;
        double prix;
        String imageUrl;
        public Burger(String n, double p, String img) { nom = n; prix = p; imageUrl = img; }
    }

    public static void main(String[] args) {
        List<Burger> catalogue = new ArrayList<>();
        catalogue.add(new Burger("Classic Burger", 400.0, "https://example.com/img1.jpg"));
        catalogue.add(new Burger("Cheese Burger", 450.0, "https://example.com/img2.jpg"));
        catalogue.add(new Burger("Veggie Burger", 380.0, "https://example.com/img3.jpg"));

        System.out.println("Catalogue des burgers:");
        for (int i = 0; i < catalogue.size(); i++) {
            Burger b = catalogue.get(i);
            System.out.println((i+1) + ". " + b.nom + " - " + b.prix + " FCFA  (image: " + b.imageUrl + ")");
        }
    }
}
