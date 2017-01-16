package entity;

import java.util.ArrayList;
import setting.Config;

/**
 * @author emarq_000, Claudel_Adrien
 */
public class Thief extends Villager {

    
        /**
         * Allows the thief to chose a card in the undistributed cards.
         * If all the werewolves are undistributed, 
         * force the player to exchange roles with one of them.
         */
        public void preliminaryTurn(ArrayList <Villager> villagers, Villager[] unnafectedVillagers) {
            //tells the game master that the turn has begun
            Config.print("The thief wakes up");
            
            
            //if the thief is alive
            if (this.isAlive()){
                boolean isAffected = true;
                for (Villager u : unnafectedVillagers){
                    if (u instanceof Thief){
                        isAffected =false;
                    }
                }
                
                //if the thief is affected to a player
                if (isAffected){
                    Config.print(this.name);
                    
                    //prints unaffected roles
                    Config.print("Those roles are unnaffected:\n");
                    for (int i = 0; i<unnafectedVillagers.length; i++){
                        Config.print(i+". "+unnafectedVillagers[i]+"\n");
                    }
                    
                    int rep = 0;
                    do{
                        rep = Config.askInt();
                    }while(rep < 0 && rep >= unnafectedVillagers.length); //while input is different from available answers
                    
                    int wolvesNumber = 0;
                    int werewolfIndex = -1;
                    for (int i = 0; i<unnafectedVillagers.length; i++){
                        if (unnafectedVillagers[i] instanceof Werewolf){
                            wolvesNumber ++;
                            werewolfIndex = i;
                        }
                    }
                    boolean areWerewolvesUnafected = wolvesNumber < Config.WEREWOLF_NUMBER;
                    
                    //if the werewolves are unaffected, force the player to exchange roles with one of them
                    if (areWerewolvesUnafected){
                        Config.print("Do you want to take one of the catds? (you don't have the choice anyway)");
                        
                        unnafectedVillagers[werewolfIndex].setName(this.name);
                      
                        for (Villager v : villagers){
                            if (v instanceof Thief){
                                v = unnafectedVillagers[werewolfIndex];
                            }
                        }
                        
                    //otherwise ask the thief if he wishes to use its power to exchange his card with one of the undistibuted cards
                    }else{
                        Config.print("Do you want to take one of the catds?");
                        rep = 0;
                        do{
                            Config.print("1: Oui \n2: Non\n");
                            rep = Config.askInt();
                        }while(rep != 1 && rep != 2); //while input is different from available answers
                        
                        if (rep == 1){
                            //browsing available votes
                            for (int i = 0; i < unnafectedVillagers.length; i++){
                                Villager v = unnafectedVillagers[i];

                                System.out.println(i + ". " + v.getName());
                            }

                            //vote input
                            int input = -1;
                            do{
                                input = Config.askInt();

                            }while(input < 0 && input > unnafectedVillagers.length); //while input is different from available answers

                            //invert position with chosen role
                            unnafectedVillagers[input].setName(this.name);

                            for (Villager v : villagers){
                                if (v instanceof Thief){
                                    v = unnafectedVillagers[input];
                                }
                            }
                        }
                    }
                //if the thief is not affected to a player, print messages as if he was
                }else{
                    Config.print("Do you want to take one of the catds?");
                    Config.print("Wich one do you want to take?");
                }
            }
            
            //tells the game master that the turn has ended
            Config.print("The thief goes back to sleep");
        }

        /**
         * Allows the thief to exchange the cards of two playes
         */
        public void turn(ArrayList <Villager> villagers) {
            
            //if the thief is alive
            if (this.isAlive()){
                //tells the game master that the turn has begun
                Config.print("The thief wakes up");
                
                //if the thief wish to use its power
                Config.print("Do you want to exange two catds?");
                int rep = 0;
                    do{
                            Config.print("1: Oui \n2: Non\n");
                            rep = Config.askInt();
                        }while(rep != 1 && rep != 2); //while input is different from available answers
                        
                        if (rep == 1){
                        //vote input
                            int input = -1, input2 = -1;
                            do{
                                Config.print("\nPremier victime ?\n");
                                //get all possible votes
                                for (int i = 0; i < villagers.size(); i++){
                                    Villager v = villagers.get(i);

                                    System.out.println(i + ". " + v.getName());
                                }
                                input = Config.askInt();

                            }while(input < 0 || input >= villagers.size()); //while input is different from any villager

                            do{
                                Config.print("\nDeuxième victime ?\n");
                                for (int i = 0; i < villagers.size(); i++){
                                    if (i!=input){
                                        Villager v = villagers.get(i);

                                        System.out.println(i + ". " + v.getName());
                                    }
                                }
                                input2=Config.askInt();

                            }while(input2 < 0 || input2 >= villagers.size() || input2 == input); //while input is different from any villager
                        
                            //swap names
                            String temp = villagers.get(input).getName();
                            villagers.get(input).setName(villagers.get(input2).getName());
                            villagers.get(input2).setName(temp);
                        
                        
                
                //if the thief is not affected to a player, print messages as if he was
                }else{
                    Config.print("Do you want to exange two catds?");
                    Config.print("Chose the first victim:");
                    Config.print("Chose the second victim:");
                }
                //tells the game master that the turn has ended
                Config.print("The thief goes back to sleep");
            }
        }
        
    @Override
    public String toString(){
        return ("Thief");
    }
}