package mdp;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class GenerateurMDP {
    static String CHEMIN_MPWD = ".mpwd";
    static Path MPWD = Path.of(CHEMIN_MPWD);

    // Exercice 1
    public static String genererMDPSimple(String texte, String tag) throws NoSuchAlgorithmException {
        StringBuilder resultat = new StringBuilder();

        String mdp = texte+tag;

        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(mdp.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();

        String hashe = Base64.getEncoder().encodeToString(digest);

        for (int indexHashe = 0; indexHashe < 8; indexHashe++) {
            resultat.append(hashe.charAt(indexHashe));
        }
        return resultat.toString();
    }

    // Exercice 2
    public static String genererMDPTaille(String texte, String tag, int tailleMax) throws Exception {
        StringBuilder resultat = new StringBuilder();

        String mdp = texte+tag;

        if (tailleMax > 0 && tailleMax < 44) { // Agrandi a 43 caracteres max pour exo 3
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(mdp.getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();

            String hashe = Base64.getEncoder().encodeToString(digest);

            for  (int indexHashe = 0; indexHashe < tailleMax; indexHashe++) {
                resultat.append(hashe.charAt(indexHashe));
            }
        }else if (tailleMax > 43) {
            throw new Exception("Le mot de passe peut avoir jusqu'a 43 caracteres");
        }
        else {
            throw new Exception("Taille positive demande");
        }

        return resultat.toString();
    }

    // Exercice 3
    public static String genererMDPDepuisMDPMaitre(String tag, int tailleMax) throws Exception {
        String mdpMaitre = lireMPWD();

        return genererMDPTaille(mdpMaitre, tag, tailleMax);
    }

    public static void enregistrerMPWD(String texte){
        try {
            Files.writeString(
                    MPWD,
                    texte,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String lireMPWD() {
        String sortie;
        try {
            sortie = Files.readString(MPWD);
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier");
            e.printStackTrace();
            sortie = "";
        }

        return sortie;
    }

    public static boolean MPWDxiste() {
        return Files.exists(MPWD);
    }
}
