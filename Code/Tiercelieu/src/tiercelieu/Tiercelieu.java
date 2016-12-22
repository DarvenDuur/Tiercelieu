/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiercelieu;

import java.util.Random;

/**
 *
 * @author emarq_000
 */
public class Tiercelieu {
    
    private static Villager[] villagers; //array of villagers
    private static String[] playerNames;
    private static int playerNumber = 8;
    private static final int WEREWOLF_NUMBER = 2;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        setNumberOfPlayers();
        villagers = new Villager[playerNumber];
        villagerInit();
        playerNamesInit();
    }
    
    //Initialisations
        /**
         * ask the game master for the number of players
         * @return number of players
         */
        private static void setNumberOfPlayers() {
            playerNumber = 8;
        }

        /**
         * setups roles
         */
        private static void villagerInit() {
            for(int i = 0; i < playerNumber-WEREWOLF_NUMBER; i++) {
                villagers[i] = new Villager();
            }

            //add the Werewolves
            for(int i = playerNumber-WEREWOLF_NUMBER; i < playerNumber; i++) {
                villagers[i] = new Werewolf();
            }
        }

        /**
         * set the player names and shuffle
         */
        private static void playerNamesInit() {
            playerNames = Console.askString(playerNumber, "Input player names :").clone();
            shuffleNames();
        }

    //Operations on names
        /**
         * basic way to shuffle the name
         */
        private static void shuffleNames() {
            Random random = new Random();
            int index;
            for (int i = playerNames.length - 1; i > 0; i--)
            {
                index = random.nextInt(i + 1);
                swapNames(i, index);
            }
        }

        /**
         * Invert player name at position i and the one at position j
         * @param i
         * @param j 
         */
        private static void swapNames(int i,int j) {
            String temp = playerNames[j];
            playerNames[j] = playerNames[i];
            playerNames[i] = temp;
        }
}