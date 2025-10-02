package kasiski;

import java.util.*;

public class Kasiski {
    private static final int TAILLE_MIN = 3;
    public static final int DISTANCE_MIN = 2;
    public static final int INDEX_PREMIERE_REP = 0;
    public static final int PGCD_TRIVIAL = 1;
    public static final int INDEX_REPETITION = 1;
    private static List<Repetition> repet;
    static List<Integer> candidats = new ArrayList<>();

    private Kasiski() {}

    public static List<Integer> trouverCleAvecKasiski(String texte) {
        repet = Repetition.trouverRepetitions(texte, TAILLE_MIN);

        if (repet.isEmpty()) {
            throw new IllegalArgumentException("Aucune répétition trouvée");
        }

        List<Integer> candidats = chercherCandidats();

        candidats = remplirTemp(candidats);

        if (candidats.isEmpty()) {
            throw new IllegalArgumentException("Aucun candidat trouvé pour la taille de la clé");
        }

        return candidats;
    }

    private static List<Integer> remplirTemp(List<Integer> candidats) {
        for (int indexRepetition = INDEX_REPETITION; indexRepetition < repet.size(); indexRepetition++) {
            List<Integer> temp = new ArrayList<>();
            for (int candidat : candidats) {
                for (int distance : repet.get(indexRepetition).distances) {
                    int valeurPGCD = pgcd(candidat, distance);
                    if (valeurPGCD > PGCD_TRIVIAL && !temp.contains(valeurPGCD)) {
                        temp.add(valeurPGCD);
                    }
                }
            }

            if (!temp.isEmpty()) {
                candidats = temp;
            }
        }
        return candidats;
    }

    private static List<Integer> chercherCandidats() {
        List<Integer> distances = repet.get(INDEX_PREMIERE_REP).distances;
        for (int distanceIndividuelle : distances) {
            for (int indexRepetitions = DISTANCE_MIN; indexRepetitions <= distanceIndividuelle; indexRepetitions++) {
                if (distanceIndividuelle % indexRepetitions == INDEX_PREMIERE_REP && !candidats.contains(indexRepetitions)) {
                    candidats.add(indexRepetitions);
                }
            }
        }
        return candidats;
    }

    private static int pgcd(int nombreUn, int nombreDeux) {
        while (nombreDeux != 0) {
            int nombreTemp = nombreDeux;
            nombreDeux = nombreUn % nombreDeux;
            nombreUn = nombreTemp;
        }
        return nombreUn;
    }
}

