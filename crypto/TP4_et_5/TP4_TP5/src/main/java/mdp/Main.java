package mdp;

import java.util.Scanner;

import static mdp.Exercice.*;

public class Main {
    public static void main(String[] args) throws Exception {
         int choix;
         Scanner input = new Scanner(System.in);
         System.out.println("Quel exercice choissisez vous ?");
         System.out.println("1. Mots de passes simples sur 8 caracteres,");
         System.out.println("2. Mots de passes a taille variable,");
         System.out.println("3. Mots de passes en fonction d'un mdp maitre");
         System.out.println("4. Recherche de collisions en fonction des tags Unilim, Amazon et Netflix sur le mdp maitre");
         System.out.print("> ");
         choix = input.nextInt();
         input.nextLine();

         switch (choix) {
             case 1:
                 exo1();
                 break;
             case 2:
                 exo2();
                 break;
             case 3:
                 exo3();
                 break;
             case 4 :
                 exo4();
                 break;
             default:
                 System.out.println("Choix non valide.");
                 break;
         }
    }

}