package utf8;

import java.util.Scanner;

public class MainHashage {
    public static void main(String[] args) {
        // Convertisseur
        /*
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez un seul caractere : ");
        char caractere = scanner.next().charAt(0);
        int decimal = Convertisseur.convertirEnDecimal(caractere);
        if (decimal != -1) {
            String binaire = Convertisseur.convertirEnBinaire(decimal);
            System.out.println("Le caractere " + caractere + " en binaire est : " + binaire);
        } else {
            System.out.println("Caractere invalide");
        }
        scanner.close();*/
        // HashageXOR
        HashageXOR hashage = new HashageXOR();
        String message = "Hello World!";
        String hash = hashage.hasher(message);
        System.out.println("Le hash du message \"" + message + "\" est : " + hash);


    }
}
