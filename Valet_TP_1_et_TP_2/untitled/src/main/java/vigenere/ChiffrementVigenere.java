package vigenere;

import model.Alphabet;

public class ChiffrementVigenere {
    public static final int INDEX_ERREUR = -1;
    private static final int LONGUEUR_ALPHABET = Alphabet.values().length;

    // Exercice 1
    public String chiffrer(String cle, String message) {
        StringBuilder resultat = new StringBuilder();
        int longueurCle = cle.length();

        for (int indexCaractere = 0; indexCaractere < message.length(); indexCaractere++) {
            String caractereMessage = recupererUnCaractere(message, indexCaractere);

            String caractereCle = recupererUnCaractere(cle, indexCaractere % longueurCle );

            int indexMessage = getIndexAlphabet(caractereMessage);
            int indexCle = getIndexAlphabet(caractereCle);

            if (indexMessage != INDEX_ERREUR && indexCle != INDEX_ERREUR) {
                int indexChiffre = (indexMessage + indexCle) % LONGUEUR_ALPHABET;
                resultat.append(Alphabet.values()[indexChiffre].getSymbol());
            } else {
                resultat.append(caractereMessage);
            }
        }
        return resultat.toString();
    }

    // Exercice 2
    public String dechiffrer(String cle, String message) {
        StringBuilder resultat = new StringBuilder();
        int longueurCle = cle.length();

        for (int indexCaractere = 0; indexCaractere < message.length(); indexCaractere++) {
            String caractereMessage = recupererUnCaractere(message, indexCaractere);
            Integer index = indexCaractere % longueurCle;
            String caractereCle = recupererUnCaractere(cle, index);

            int indexMessage = getIndexAlphabet(caractereMessage);
            int indexCle = getIndexAlphabet(caractereCle);

            if (indexMessage != INDEX_ERREUR && indexCle != INDEX_ERREUR) {
                int indexDechiffre = (indexMessage - indexCle + LONGUEUR_ALPHABET) % LONGUEUR_ALPHABET;
                resultat.append(Alphabet.values()[indexDechiffre].getSymbol());
            } else {
                resultat.append(caractereMessage);
            }
        }
        return resultat.toString();
    }

    private int getIndexAlphabet(String caractere) {
        for (int compteur = 0; compteur < Alphabet.values().length; compteur++) {
            if (caractereExisteTIl(caractere, compteur)) {
                return compteur;
            }
        }
        return INDEX_ERREUR;
    }

    private static boolean caractereExisteTIl(String caractere, int compteur) {
        return String.valueOf(caractere).equals(Alphabet.values()[compteur].getSymbol());
    }

    private static String recupererUnCaractere(String cle, Integer indexMax) {
        return cle.substring(indexMax, indexMax + 1);
    }

    public int getLongueurAlphabet() {
        return LONGUEUR_ALPHABET;
    }
}
