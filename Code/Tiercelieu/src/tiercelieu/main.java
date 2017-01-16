package tiercelieu;

import java.util.InputMismatchException;
import setting.Config;

/**
 * @author emarq_000, Claudel_Adrien
 */
public class main {

	public static void main(String[] args) {
        //input of the nomber of players
		Config.print("Entrez le nombre de joueur (Entre 8 et 12):");
		int nb = 0;
		
		do{
                    try{
			nb = Config.SCANNER.nextInt();
                    }catch(InputMismatchException e){// Si la saisie n'est pas un nombre 
			System.out.println("Veuillez entrer un nombre !");
                    }catch(NumberFormatException e){// Si la saisie n'est pas un nombre 
			System.out.println("Veuillez entrer un nombre !");
                    }
		}while(nb < 8 || nb > 12);
		
        //initialize the controler
		Controller controller = new Controller(nb);
        
        //start the game
		controller.start();
	}
}
