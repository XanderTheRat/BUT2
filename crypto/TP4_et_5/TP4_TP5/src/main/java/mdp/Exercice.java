package mdp;

import org.fusesource.jansi.AnsiConsole;
import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import static mdp.GenerateurMDP.*;

public class Exercice {
    static Scanner input = new Scanner(System.in);

    static final int LIMITE_INFERIEUR = 33;
    static final int LIMITE_SUPPERIEUR = 126;

    static void exo1() throws NoSuchAlgorithmException {
        System.out.println(ansi().bold().fg(CYAN).a("Entrer un mot de passe"));
        System.out.print(ansi().bold().fg(GREEN).a("> "));
        String mdp = input.nextLine();

        System.out.println(ansi().bold().fg(CYAN).a("Entrer un tag"));
        System.out.print(ansi().bold().fg(GREEN).a("> "));
        String tag = input.nextLine();

        String hashe = genererMDPSimple(mdp, tag);

        System.out.println(hashe);
        input.close();
    }

    static void exo2() throws Exception {
        System.out.println(ansi().bold().fg(CYAN).a("Entrez une taille de mot de passe"));
        System.out.print(ansi().bold().fg(GREEN).a("> "));
        int tailleMax = input.nextInt();
        input.nextLine();


        System.out.println(ansi().bold().fg(CYAN).a("Entrer un mot de passe"));
        System.out.print(ansi().bold().fg(GREEN).a("> "));
        String mdp = input.nextLine();

        System.out.println(ansi().bold().fg(CYAN).a("Entrer un tag"));
        System.out.print(ansi().bold().fg(GREEN).a("> "));
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
        } else {
            System.out.println(ansi().bold().fgBright(YELLOW).a("Voulez vous modifier le mot de passe maitre enregistrer dans .mpwd ? y/N"));
            System.out.print(ansi().bold().fg(GREEN).a("> "));
            validation = input.nextLine();

            if (validation.equalsIgnoreCase("Y")) {
                modifierMPWD();
            }
        }

        System.out.println("Entrez votre tag :");
        System.out.print(ansi().bold().fg(GREEN).a("> "));
        tag = input.nextLine();

        System.out.println("Entrez votre taille de mot de passe :");
        System.out.print(ansi().bold().fg(GREEN).a("> "));
        tailleMax = input.nextInt();

        String hashe = genererMDPDepuisMDPMaitre(tag, tailleMax);
        System.out.println(hashe);
        input.close();
    }

    private static void modifierMPWD() {
        String motDePasseMaitre;
        System.out.println("Entrez votre mot de passe maitre :");
        System.out.print(ansi().bold().fg(GREEN).a("> "));
        motDePasseMaitre = input.nextLine();

        GenerateurMDP.enregistrerMPWD(motDePasseMaitre);
    }


    public static void exo4() throws Exception {
        AnsiConsole.systemInstall();

        try {
            int[] valeursN = {1, 2, 3};

            char[] dictionnaire = new char[94];
            int index = 0;
            for (char c = LIMITE_INFERIEUR; c <= LIMITE_SUPPERIEUR; c++) {
                dictionnaire[index++] = c;
            }

            System.out.println(ansi().bold().fg(CYAN).a("Taille du dictionnaire : ").reset().a(dictionnaire.length + " caractères"));
            System.out.println();

            for (int N : valeursN) {
                System.out.println(ansi().bold().fg(MAGENTA).a("\n╔════════════════════════════════════════╗").reset());
                System.out.println(ansi().bold().fg(MAGENTA).a("║     ATTAQUES POUR N = " + N + "                ║").reset());
                System.out.println(ansi().bold().fg(MAGENTA).a("╚════════════════════════════════════════╝\n").reset());

                String hashUnilim = genererMDPDepuisMDPMaitre("Unilim", N);
                String hashAmazon = genererMDPDepuisMDPMaitre("Amazon", N);
                String hashNetflix = genererMDPDepuisMDPMaitre("Netflix", N);

                System.out.println(ansi().bold().a("Hashés pour chaque tag (N=" + N + ") :").reset());
                System.out.println(ansi().fg(YELLOW).a("  Hash Unilim  : ").reset().a(hashUnilim));
                System.out.println(ansi().fg(YELLOW).a("  Hash Amazon  : ").reset().a(hashAmazon));
                System.out.println(ansi().fg(YELLOW).a("  Hash Netflix : ").reset().a(hashNetflix));
                System.out.println();

                System.out.println(ansi().bold().fg(BLUE).a("- Attaque dictionnaire sur le tag Unilim").reset());
                System.out.println();

                long debutUnilim = System.currentTimeMillis();
                int[] compteurUnilim = {0};
                char[] bufferUnilim = new char[10];

                String collisionUnilim = attaqueDictionnaire(dictionnaire, bufferUnilim, 0, 10, hashUnilim, "Unilim", N, compteurUnilim);

                calcDuree(debutUnilim, compteurUnilim, collisionUnilim);

                System.out.println(ansi().bold().fg(BLUE).a("- Attaque dictionnaire sur le tag Amazon").reset());
                System.out.println();

                long debutAmazon = System.currentTimeMillis();
                int[] compteurAmazon = {0};
                char[] bufferAmazon = new char[10];

                String collisionAmazon = attaqueDictionnaire(dictionnaire, bufferAmazon, 0, 10, hashAmazon, "Amazon", N, compteurAmazon);

                calcDuree(debutAmazon, compteurAmazon, collisionAmazon);

                System.out.println(ansi().bold().fg(BLUE).a("- Attaque dictionnaire sur le tag Netflix").reset());
                System.out.println();

                long debutNetflix = System.currentTimeMillis();
                int[] compteurNetflix = {0};
                char[] bufferNetflix = new char[10];

                String collisionNetflix = attaqueDictionnaire(dictionnaire, bufferNetflix, 0, 10, hashNetflix, "Netflix", N, compteurNetflix);

                calcDuree(debutNetflix, compteurNetflix, collisionNetflix);

                System.out.println(ansi().bold().fg(GREEN).a("╭─── RÉCAPITULATIF (N=" + N + ") ───╮").reset());
                afficherRecap("Unilim ", collisionUnilim);
                afficherRecap("Amazon ", collisionAmazon);
                afficherRecap("Netflix", collisionNetflix);
                System.out.println(ansi().bold().fg(GREEN).a("╰────────────────────────────╯\n").reset());
            }
        } finally {
            AnsiConsole.systemUninstall();
        }
    }

    private static void afficherRecap(String tag, String collision) {
        if (collision != null) {
            System.out.println(ansi().fg(GREEN).a("│ ✓ " + tag + " : ").bold().a(collision).reset());
        } else {
            System.out.println(ansi().fg(RED).a("│ ✗ " + tag + " : Aucune").reset());
        }
    }

    private static void calcDuree(long debut, int[] compteur, String collision) {
        long fin = System.currentTimeMillis();
        long duree = (fin - debut) / 1000;

        System.out.println(ansi().fg(CYAN).a("  Essais    : ").reset().a(compteur[0]));
        System.out.println(ansi().fg(CYAN).a("  Temps     : ").reset().a(duree + " secondes"));

        if (collision != null) {
            System.out.println(ansi().fg(GREEN).bold().a("  Collision : " + collision).reset());
        } else {
            System.out.println(ansi().fg(RED).a("  Collision : Aucune").reset());
        }
        System.out.println();
    }

    private static String attaqueDictionnaire(char[] dictionnaire, char[] buffer, int position, int longueur, String hashCible, String tag, int N, int[] compteur) throws Exception {

        String resultat = null;

        if (position == longueur) {
            compteur[0]++;

            String mdpCandidat = new String(buffer, 0, longueur);

            String hashGenere = genererMDPTaille(mdpCandidat, tag, N);

            if (compteur[0] % 100000000 == 0) {
                System.out.println(ansi().fgBright(BLACK).a("  [" + compteur[0] + "] " + mdpCandidat).reset());
            }

            if (hashGenere.equals(hashCible)) {
                resultat = mdpCandidat;
            }
        } else {
            for (int indiceDictionnaire = 0; indiceDictionnaire < dictionnaire.length && resultat == null; indiceDictionnaire++) {
                buffer[position] = dictionnaire[indiceDictionnaire];
                resultat = attaqueDictionnaire(dictionnaire, buffer, position + 1, longueur, hashCible, tag, N, compteur);
            }
        }

        return resultat;
    }
}