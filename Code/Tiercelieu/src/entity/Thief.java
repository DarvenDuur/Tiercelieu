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
            
            /*
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
                    
                    //if the werewolves are unaffected, force the player to exchange roles with one of them
                    if (areWerewolvesUnafected()){
                        Console.print("Do you want to take one of the catds? (you don't have the choice anyway)");
                        
                        for (int i = 0; i < unaffectedIndex.length; i++) {
                            if (villagers[unaffectedIndex[i]] instanceof Werewolf){
                                //invert position with unaffected werewolf
                                swapNames(unaffectedIndex[i],thiefIndex);
                                unaffectedIndex[i] = thiefIndex;
                                break;
                            }
                        }
                        
                    //otherwise ask the thief if he wishes to use its power to exchange his card with one of the undistibuted cards
                    }else if(Config.askIndex(new String[]{"Yes","No"}, "Do you want to take one of the catds?")==0){
                        int choice = Config.askIndex(toNames(unaffected), "Wich one do you want to take?");
                        
                        //invert position with chosen role
                        swapNames(unaffected[choice],thiefIndex);
                        unaffectedIndex[choice] = thiefIndex;
                                
                        Config.print("You are now the "+villagers[choice].toString());
                    }
                //if the thief is not affected to a player, print messages as if he was
                }else{
                    Config.print("Do you want to take one of the catds?");
                    Config.print("Wich one do you want to take?");
                }
            }
            */
            //tells the game master that the turn has ended
            Config.print("The thief goes back to sleep");
        }

        /**
         * Allows the thief to exchange the cards of two playes
         */
        public void turn(ArrayList <Villager> villagers) {
            /*
            //if the thief is alive
            if (villagers[thiefIndex].getAlive()){
                //tells the game master that the turn has begun
                Console.print("The thief wakes up");
                Console.waitContinue();
                
                //if the thief is affected to a player
                if (isAffected(thiefIndex)){
                    //if the thief wish to use its power
                    if(Console.askIndex(new String[]{"Yes","No"}, "Do you want to exange two catds?")==0){

                        int[] aliveIndex = getAliveIndex();

                        //set the first victim of the thief
                        int victimIndex1 = aliveIndex[Console.askIndex(toNames(aliveIndex), "Chose the first victim:")];

                        //excluding index of first victim of the thief
                        int[] newIndex = new int[aliveIndex.length-1];
                        int index = 0;
                        for (int i=0; i<aliveIndex.length; i++) {
                            if (aliveIndex[i]!=victimIndex1) {
                                newIndex[index]=aliveIndex[i];
                                index++;
                            }
                        }

                        //set the second victim of the thief
                        int victimIndex2 = newIndex[Console.askIndex(toNames(newIndex), "Chose the second victim:")];
                    }
                
                //if the thief is not affected to a player, print messages as if he was
                }else{
                    Console.print("Do you want to exange two catds?");
                    Console.print("Chose the first victim:");
                    Console.print("Chose the second victim:");
                }
                //tells the game master that the turn has ended
                Console.print("The thief goes back to sleep");
            }
*/
        }
        
    @Override
    public String toString(){
        return ("Thief");
    }
}