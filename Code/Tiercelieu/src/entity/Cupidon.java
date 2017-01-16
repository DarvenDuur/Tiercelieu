package entity;

import java.util.ArrayList;

import setting.Config;

/**
 * @author emarq_000, Claudel_Adrien
 */
public class Cupidon extends Villager{
	
    
    /**
     * Cupidon's vote (first night)
     */
	public String[] turn(ArrayList<Villager> villager){
            Config.print(this.getName() + " voulez-vous utiliser votre pouvoir ?\n");
            int rep = 0;
            do{
                Config.print("1: Oui \n2: Non\n");
                rep = Config.askInt();
                
            }while(rep != 1 && rep != 2); //while input is different from available answers
            
            if(rep==1){   
            Config.print(this.getName() + " qui voulez-vous unir ?\n");
            
            //vote input
            int input = -1, input2 = -1;
            do{
                Config.print("\nPremier amoureux ?\n");
                //get all possible votes
                for (int i = 0; i < villager.size(); i++){
                    Villager v = villager.get(i);
			
                    System.out.println(i + ". " + v.getName());
                }
                input = Config.askInt();
                
            }while(input < 0 || input >= villager.size()); //while input is different from any villager
		
            do{
                Config.print("\nDeuxième amoureux ?\n");
                for (int i = 0; i < villager.size(); i++){
                    if (i!=input){
                        Villager v = villager.get(i);

                        System.out.println(i + ". " + v.getName());
                    }
                }
                input2=Config.askInt();
                
            }while(input2 < 0 || input2 >= villager.size() || input2 == input); //while input is different from any villager
	
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
