package vigenere;

import java.util.Scanner;

public class MainVigenere {
    public static void main(String[] args) {
        String message;
        String cle;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Entrez le message à chiffrer :");
        message = scanner.nextLine();
        System.out.println("Entrez la clé de chiffrement :");
        cle = scanner.nextLine();
        ChiffrementVigenere chiffre = new ChiffrementVigenere();
        String messageChiffre = chiffre.chiffrer(cle, message);
        System.out.println("Votre message chiffré est : " + messageChiffre);
        String messageDechiffre = chiffre.dechiffrer(cle, messageChiffre);
        System.out.println("Votre message déchiffré est : " + messageDechiffre);
        scanner.close();
    }
}
