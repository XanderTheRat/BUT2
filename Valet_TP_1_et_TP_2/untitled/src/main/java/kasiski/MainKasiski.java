package kasiski;


import vigenere.ChiffrementVigenere;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class MainKasiski {
    public static void main(String[] args) throws IOException {
        String texte;
        String cle;
        String choix;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Quel fichier voulez-vous utiliser ?");
        System.out.println("1. Introduction de Dark Souls 1");
        System.out.println("2. Une phrase fameuse venant du Seigneur des Anneaux");
        System.out.println("3. Du Lorem Ipsum");
        choix = scanner.nextLine();
        texte = switch (choix) {
            case "1" -> Files.readString(Paths.get("messages/dark_souls.txt"));
            case "2" -> Files.readString(Paths.get("messages/lotr.txt"));
            case "3" -> Files.readString(Paths.get("messages/lorem_ipsum.txt"));
            default -> throw new IllegalArgumentException("Choix invalide");
        };

        System.out.println("Entrez la clé de chiffrement :");
        cle = scanner.nextLine();
        System.out.println("Taille de la clé : " + cle.length());

        ChiffrementVigenere chiffre = new ChiffrementVigenere();
        String messageChiffre = chiffre.chiffrer(cle, texte);
        List<Integer> tailleCle = Kasiski.trouverCleAvecKasiski(messageChiffre);

        System.out.println("Texte chiffré : " + messageChiffre);
        System.out.println("Candidats pour la taille de la clé : " + tailleCle);
    }
}
