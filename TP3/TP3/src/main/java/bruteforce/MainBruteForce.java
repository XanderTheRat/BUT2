package bruteforce;

import utf8.Convertisseur;

public class MainBruteForce {
    public static void main(String[] args) {
        char caractereCible = 'A';
        String hashCible = Convertisseur.convertirEnBinaire(Convertisseur.convertirEnDecimal(caractereCible));
        BruteForceCollision bruteForce = new BruteForceCollision(hashCible);
        Integer borneInf = 0;
        Integer borneSup = 4090;
        var collisions = bruteForce.trouverCollision(borneInf, borneSup);
        System.out.println("Nombre d'essais de collisions : " + bruteForce.getNbEssaies());
        if (collisions.isEmpty() || collisions.size() == 1 && collisions.get(0).equals(String.valueOf(caractereCible))) {
            System.out.println("Aucune collision trouvée.");
        } else {
            System.out.println("Collisions trouvées : " + collisions);
        }


        BruteForcePreimage bruteForcePreimage = new BruteForcePreimage(hashCible);
        var preimages = bruteForcePreimage.trouverPreimage(borneInf, borneSup);
        System.out.println("Nombre d'essais de preimages : " + bruteForcePreimage.getNbEssaies());
        if (preimages == 0) {
            System.out.println("Aucune preimage trouvée.");
        } else {
            System.out.println("Preimages trouvées : " + preimages);
        }
    }
}
