package bruteforce;

import utf8.Convertisseur;
import utf8.HasheurBinaire;

import java.util.ArrayList;

public class BruteForceCollision {
    private Integer nbEssaies;
    private String hashCible;
    private ArrayList<String> collisionsTrouvees;
    private HasheurBinaire hash;

    public BruteForceCollision(String hashCible) {
        this.nbEssaies = 0;
        this.hashCible = hashCible;
        this.collisionsTrouvees = new ArrayList<String>();
        hash = new HasheurBinaire();
    }

    public void ajouterCollision(String collision) {
        this.collisionsTrouvees.add(collision);
    }

    public Integer getNbEssaies() {
        return nbEssaies;
    }

    public ArrayList<String> trouverCollision(Integer borneInf, Integer borneSup) {
        this.nbEssaies = 0;
        try {
            for (int decimalCaractere = borneInf; decimalCaractere <= borneSup; decimalCaractere++) {
                if (hash.estValide((char) decimalCaractere)) {
                    this.nbEssaies++;
                    String hashTeste = HasheurBinaire.convertirEnBinaire(decimalCaractere);
                    if (hashTeste.equals(this.hashCible)) {
                        this.ajouterCollision(String.valueOf((char) decimalCaractere));
                    }
                }
            }

            return this.collisionsTrouvees;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
