package services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageService {

    private final String dossierImages = "images/";

    public ImageService() {
        File dossier = new File(dossierImages);
        if (!dossier.exists()) {
            dossier.mkdir();
        }
    }

    // Sauvegarde une image et retourne le chemin (gère les erreurs simplement)
    public String enregistrerImage(String nomFichier, byte[] contenu) {
        try {
            Path chemin = Paths.get(dossierImages + nomFichier);
            Files.write(chemin, contenu);
            return chemin.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Supprime une image si elle existe
    public boolean supprimerImage(String nomFichier) {
        File fichier = new File(dossierImages + nomFichier);
        if (fichier.exists()) {
            return fichier.delete();
        }
        return false;
    }
}
