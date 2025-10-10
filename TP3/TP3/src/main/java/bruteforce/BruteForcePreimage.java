package bruteforce;

import utf8.Convertisseur;

import java.util.ArrayList;

public class BruteForcePreimage {
    private Integer nbEssaies;
    private String hashCible;
    private ArrayList<String> preimageTrouvees;

    public BruteForcePreimage(String hashCible) {
        this.nbEssaies = 0;
        this.hashCible = hashCible;
        this.preimageTrouvees = new ArrayList<String>();
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
                if (Convertisseur.estValide(Character.highSurrogate(nombreAleatoire)) && Convertisseur.convertirEnBinaire(nombreAleatoire).equals(this.hashCible)) {
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
