package entity;

import java.util.ArrayList;
import java.util.InputMismatchException;

import setting.Config;

/**
 * @author emarq_000, Claudel_Adrien
 */
public class Cupidon extends Villager{
	
    /**
     * 's constructor
     * @param i : index of player
     */
	public Cupidon(int i){
		super(i);
	}
	
    
    /**
     * Cupidon's vote (first night)
     */
	public String[] turn(ArrayList<Villager> villager){
            Config.print(this.getName() + " voulez-vous utiliser votre pouvoir ?\n");
            int rep = 0;
            do{
                Config.print("1: Oui \n2: Non\n");
                try{
                    rep = (int)Config.SCANNER.nextInt();
                }catch(InputMismatchException e){// Si la saisie n'est pas un nombre 
			System.out.println("Veuillez entrer un nombre !");
                    }catch(NumberFormatException e){// Si la saisie n'est pas un nombre 
			System.out.println("Veuillez entrer un nombre !");
                    }
            }while(rep != 1 && rep != 2); //while input is different from available answers
            
            if(rep==1){   
            Config.print(this.getName() + " qui voulez-vous unir ?\n");
    	
    	//get all possible votes
		for (int i = 0; i < villager.size(); i++){
			Villager v = villager.get(i);
			
			System.out.println(i + ". " + v.getName() + " : " + v);
		}
		
		//vote input
		int input = -1, input2 = -1;
		do{
			try{
				Config.print("\nPremier amoureux ?\n");
				input = Config.SCANNER.nextInt();
			}catch(InputMismatchException e){// Si la saisie n'est pas un nombre 
			System.out.println("Veuillez entrer un nombre !");
                    }catch(NumberFormatException e){// Si la saisie n'est pas un nombre 
			System.out.println("Veuillez entrer un nombre !");
                    }
		}while(input < 0 || input > villager.size()); //while input is different from any villager
		
        do{
			try{
				Config.print("\nSecond amoureux ?\n");
				input2 = Config.SCANNER.nextInt();
			}catch(InputMismatchException e){// Si la saisie n'est pas un nombre 
			System.out.println("Veuillez entrer un nombre !");
                    }catch(NumberFormatException e){// Si la saisie n'est pas un nombre 
			System.out.println("Veuillez entrer un nombre !");
                    }
		}while(input2 < 0 || input2 > villager.size() || input2 == input); //while input is different from any villager
		
		String couple[] = new String[2];
		couple[0] = villager.get(input).getName();
		couple[1] = villager.get(input2).getName();
		
		return  couple;
            
            //if player does'nt want to create a couple
            }else{
                String couple[] = new String[2];
		couple[0] = "";
		couple[1] = "";
		
		return  couple;
            }
	}
    
	
    @Override
    public String toString(){
        return ("Cupidon");
    }
}
