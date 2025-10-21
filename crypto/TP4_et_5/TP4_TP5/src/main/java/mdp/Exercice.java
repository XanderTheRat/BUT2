package mdp;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import static mdp.GenerateurMDP.*;

public class Exercice {
    static Scanner input = new Scanner(System.in);

    static final String SORTIE_HASHEE_UNILIM = "7";
    static final String SORTIE_HASHEE_AMAZON = "6";
    static final String SORTIE_HASHEE_NETFLIX = "B";

    static final int LIMITE_INFERIEUR = 33;
    static final int LIMITE_SUPPERIEUR = 126;

    static void exo1() throws NoSuchAlgorithmException {
        System.out.println("Entrer un mot de passe");
        System.out.print("> ");
        String mdp = input.nextLine();

        System.out.println("Entrer un tag");
        System.out.print("> ");
        String tag = input.nextLine();

        String hashe = genererMDPSimple(mdp, tag);

        System.out.println(hashe);
        input.close();
    }

    static void exo2() throws Exception {
        System.out.println("Entrez une taille de mot de passe");
        System.out.print("> ");
        int tailleMax = input.nextInt();
        input.nextLine();


        System.out.println("Entrez votre mot de passe");
        System.out.print("> ");
        String mdp = input.nextLine();

        System.out.println("Entrez votre tag");
        System.out.print("> ");
        String tag = input.nextLine();

        input.close();
        String hashe = genererMDPTaille(mdp, tag, tailleMax);

        System.out.println(hashe);
    }

    static void exo3() throws Exception {
        String validation;
        String tag;
        int tailleMax;

        if (!MPWDxiste()) {
            modifierMPWD();
        }else {
            System.out.println("Voulez vous modifier le mot de passe maitre enregistrer dans .mpwd ? y/N");
            System.out.print("> ");
            validation = input.nextLine();

            if(validation.equalsIgnoreCase("Y")){
                modifierMPWD();
            }
        }

        System.out.println("Entrez votre tag :");
        System.out.print("> ");
        tag = input.nextLine();

        System.out.println("Entrez votre taille de mot de passe :");
        System.out.print("> ");
        tailleMax =  input.nextInt();

        String hashe = genererMDPDepuisMDPMaitre(tag, tailleMax);
        System.out.println(hashe);
        input.close();
    }

    private static void modifierMPWD() {
        String motDePasseMaitre;
        System.out.println("Entrez votre mot de passe maitre :");
        System.out.print("> ");
        motDePasseMaitre = input.nextLine();

        GenerateurMDP.enregistrerMPWD(motDePasseMaitre);
    }

    public static ArrayList<String> exo4() {
        ArrayList<String> mdpPossibles = new ArrayList<>();
        ArrayList<Character> alphabet = new ArrayList<>();

        for (char c = 33; c <= 126; c++) {
            alphabet.add(c);
        }

        Iterator<Character> iterator = alphabet.iterator();



        return mdpPossibles;
    }
}
