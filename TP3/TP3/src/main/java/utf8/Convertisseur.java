package utf8;

import java.util.List;

public class Convertisseur {
    private static final int CARACTERE_MAX = 122;
    private static final int CARACTERE_MIN = 65;
    private static final List<Character> symboles = List.of('!', '#', '(', ')', '*', '+', '/', '?');

    public static void main(String[] args) {
        System.out.println(convertirEnBinaire(convertirEnDecimal('A')));
    }

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


    public static Boolean estValide(char caractere) {
        Boolean validationLettre = (caractere >= CARACTERE_MIN && caractere <= CARACTERE_MAX);
        Boolean validationSymbole = (symboles.contains(caractere));
        return (validationLettre || validationSymbole);
    }
}
