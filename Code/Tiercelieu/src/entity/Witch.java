package entity;

import java.util.ArrayList;

import setting.Config;

/**
 * @author emarq_000, Claudel_Adrien
 */
public class Witch extends Villager {
    
    private boolean healPotion, killPotion; //healing potion and poison potion, true if available

    /**
     * Witch's constructor
     * @param i : index of player
     */
    public Witch(int i) {
        super(i);
        
        healPotion = true;
        killPotion = true;
    }
    
    
    /**
     * werewolves' victim saving proposal
     * @param villagers : live villagers
     * @param vote : index of werewolves' target
     */
    public boolean heal(ArrayList<Villager> villagers, int vote) {
        Config.print(this.getName() + " a vous de jouer !\n");
        
        //if the witch can use her potion
        if(healPotion){
            //ask if she want to use it
            Config.print("Voulez-vous sauver " + villagers.get(vote).getName() + " ?\n");
            int rep = 0;
            do{
                Config.print("1: Oui \n2: Non\n");
                rep = (int)Config.SCANNER.nextInt();
            }while(rep != 1 && rep != 2); //while input is different from available answers
            
            //if answer is "Oui"
            if(rep == 1){
                healPotion = false;
                return true;
            }
        }
        return false;
    }
    
    
    /**
     * killing proposal
     * @param villagers : live villagers
     * @return : index of villager to kill, -1 for no kill
     */
    public int poison(ArrayList<Villager> villagers){
        
        //if the witch can use her potion
        if(killPotion){
            //ask if she want to use it
            Config.print(this.getName() + " voulez-vous tuer quelqu'un ?\n");
            
            int rep = 0;
            do{
                Config.print("1: Oui \n2: Non\n");
                rep = (int)Config.SCANNER.nextInt();
            }while(rep != 1 && rep != 2); //while input is different from available answers
            
            //if answer is "Oui"
            if(rep == 1){
                killPotion = false;
                
                //browsing available votes
                for (int i = 0; i < villagers.size(); i++){
                    Villager v = villagers.get(i);
                    
                    System.out.println(i + ". " + v.getName() + " : " + v);
                }

                //vote input
                int input = -1;
                do{
                    try{
                        input = Config.SCANNER.nextInt();
                    }catch(NumberFormatException e){ //if input is not a number
                        System.out.println("Veuillez entrez un nombre !");
                    }
                }while(input < 0 && input > villagers.size()); //while input is different from available answers
                
                //return index of witch's victim
                return input;
            }
        }
        return -1;
    }
    
    @Override
    public String toString(){
        return ("Witch");
    }
}