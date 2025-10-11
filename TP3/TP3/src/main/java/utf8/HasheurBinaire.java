package utf8;

import java.util.List;

public class ConvertisseurBinaire extends Convertisseur{
    public ConvertisseurBinaire(){
        CARACTERE_MIN = 65;
        CARACTERE_MAX = 122;
    }

    static final List<Character> symboles = List.of('!', '#', '(', ')', '*', '+', '/', '?');

    public static int convertirEnDecimal(char caractere) {
        if (estValide(caractere)) {
            return (int) caractere;
        } else {
            return -1;
        }
    }

    public static String convertirEnBinaire(int decimal) {
        int exposant = 8;
        String resultat = "";
        while (exposant > 0) {
            Integer puissanceDeDeux = (int) Math.pow(2, exposant - 1);
            if (decimal >= puissanceDeDeux) {
                decimal -= puissanceDeDeux;
                exposant--;
                resultat += "1";
            } else {
                exposant--;
                resultat += "0";
            }
        }

        return resultat;
    }
}
