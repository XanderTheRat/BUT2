package utf8;

public class HashageXOR {
    public String hasher(String message) {
        String binaireCaractere = "11111111";
        for (int indexCaractere = 0; indexCaractere < message.length(); indexCaractere++) {
            binaireCaractere = Convertisseur.convertirEnBinaire(Convertisseur.convertirEnDecimal(message.charAt(indexCaractere)));
            binaireCaractere = xor(binaireCaractere, "10101010");
        }

        return binaireCaractere;
    }

    private String xor(String binaireCaractere1, String binaireCaractere2) {
        String resultat = "";
        for (int indexBit = 0; indexBit < binaireCaractere1.length(); indexBit++) {
            if (binaireCaractere1.charAt(indexBit) == binaireCaractere2.charAt(indexBit)) {
                resultat += "0";
            } else {
                resultat += "1";
            }
        }

        return resultat;
        // TODO Refacto convertisseur en asbstract et adapter les autres classes pour les brute force
    }
}
