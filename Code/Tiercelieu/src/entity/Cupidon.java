package entity;

import java.util.ArrayList;

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
    	Config.print(this.getName() + " qui voulez vous unir ?\n");
    	
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
			}
            //if input is not a number
			catch(NumberFormatException e){
				System.out.println("Veuillez entrez un nombre !");
	        }
		}while(input < 0 || input > villager.size()); //while input is different from any villager
		
        do{
			try{
				Config.print("\nSecond amoureux ?\n");
				input2 = Config.SCANNER.nextInt();
			}
            //if input is not a number
			catch(NumberFormatException e){
				System.out.println("Veuillez entrez un nombre !");
	        }
		}while(input2 < 0 || input2 > villager.size() || input2 == input); //while input is different from any villager
		
		String couple[] = new String[2];
		couple[0] = villager.get(input).getName();
		couple[1] = villager.get(input2).getName();
		
		return  couple;
	}
    
	
    @Override
    public String toString(){
        return ("Cupidon");
    }
}
