package bruteforce;

import utf8.Convertisseur;
import utf8.HasheurBinaire;

import java.util.ArrayList;

public class BruteForcePreimage {
    private Integer nbEssaies;
    private String hashCible;
    private ArrayList<String> preimageTrouvees;
    private HasheurBinaire hash;

    public BruteForcePreimage(String hashCible) {
        this.nbEssaies = 0;
        this.hashCible = hashCible;
        this.preimageTrouvees = new ArrayList<String>();
        hash  = new HasheurBinaire();
    }

    public void ajouterPreimage(String collision) {
        this.preimageTrouvees.add(collision);
    }

    public Integer getNbEssaies() {
        return nbEssaies;
    }

    public Integer trouverPreimage(Integer borneInf, Integer borneSup) {
        this.nbEssaies = 0;
        try {
            Integer nombre1;
            Integer nombre2;
            Integer nombrePreimage = 0;

            while (nombrePreimage != 0 || this.nbEssaies < 10000) {
                Integer nombreAleatoire = (int) (Math.random() * (borneSup - borneInf + 1)) + borneInf;
                if (hash.estValide(Character.highSurrogate(nombreAleatoire)) && HasheurBinaire.convertirEnBinaire(nombreAleatoire).equals(this.hashCible)) {
                    nombrePreimage = nombreAleatoire;
                }
                this.nbEssaies++;
            }

            return nombrePreimage;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
