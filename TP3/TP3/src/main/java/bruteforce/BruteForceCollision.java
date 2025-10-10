package bruteforce;

import utf8.Convertisseur;

import java.util.ArrayList;

public class BruteForceCollision {
    private Integer nbEssaies;
    private String hashCible;
    private ArrayList<String> collisionsTrouvees;

    public BruteForceCollision(String hashCible) {
        this.nbEssaies = 0;
        this.hashCible = hashCible;
        this.collisionsTrouvees = new ArrayList<String>();
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
                if (Convertisseur.estValide((char) decimalCaractere)) {
                    this.nbEssaies++;
                    String hashTeste = Convertisseur.convertirEnBinaire(decimalCaractere);
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
