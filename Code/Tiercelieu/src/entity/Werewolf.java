package entity;

import setting.Config;
import java.util.ArrayList;
import java.util.InputMismatchException;

/**
 * @author emarq_000, Claudel_Adrien
 */
public class Werewolf extends Villager {

    /**
     * Werewolf's constructor
     * @param i : index of player
     */
    public Werewolf(int i){
        super(i);
    }
    
    
    /**
     * Werevolf's vote
     */
    public int turn(ArrayList<Villager> villager) {
    	
    	Config.print(this.getName() + " qui voulez-vous tuer ?\n");
    	
    	// Parcours des vote possibles
        for (int i = 0; i < villager.size(); i++){
            Villager v = villager.get(i);
            
            System.out.println(i + ". " + v.getName() + " : " + v);
        }

        // Saisie du vote
        int input = -1;
        do{
            try{
                input = Config.SCANNER.nextInt();
            }catch(InputMismatchException e){// Si la saisie n'est pas un nombre 
			System.out.println("Veuillez entrer un nombre !");
                    }catch(NumberFormatException e){// Si la saisie n'est pas un nombre 
			System.out.println("Veuillez entrer un nombre !");
                    }
		}
        // tant que le vote ne corespond a aucun villageois
		while(input < 0 && input > villager.size());
		
		return input;
	}
    
    
    @Override
    public String toString(){
        return ("Werewolf");
    }
}