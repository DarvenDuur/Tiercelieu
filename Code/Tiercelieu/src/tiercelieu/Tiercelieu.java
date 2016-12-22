/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiercelieu;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author emarq_000
 */
public class Tiercelieu {
    
    private static Villager[] villagers; //array of villagers
    private static String[] playerNames; //array of names, will get to change
    private static int playerNumber = 8;
    private static final int WEREWOLF_NUMBER = 2;
    private static int werewolvesTarget = -1; //index of the player to be killed, -1 for undefined
    private static boolean isEnded = false; //if true, the game ends

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        setNumberOfPlayers();
        villagers = new Villager[playerNumber];
        villagerInit();
        playerNamesInit();
        Console.print(getNonWerewolvesNames());
        Console.clear();
        Console.print(getNonWerewolvesNames());
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
         * the werewolves are at the end
         */
        private static void villagerInit() {
            for (int i = 0; i < playerNumber-WEREWOLF_NUMBER; i++) {
                villagers[i] = new Villager();
            }

            //add the Werewolves
            for (int i = playerNumber-WEREWOLF_NUMBER; i < playerNumber; i++) {
                villagers[i] = new Werewolf();
            }
        }

        /**
         * set the player names and shuffle
         */
        private static void playerNamesInit() {
            //playerNames = Console.askString(playerNumber, "Input player names :").clone();
            playerNames = new String[8];
            playerNames[0] = "player 1";
            playerNames[1] = "player 2";
            playerNames[2] = "player 3";
            playerNames[3] = "player 4";
            playerNames[4] = "player 5";
            playerNames[5] = "player 6";
            playerNames[6] = "player 7";
            playerNames[7] = "player 8";
            shuffleNames();
        }

    //Operations on names
        /**
         * basic way to shuffle the name
         */
        private static void shuffleNames() {
            Random random = new Random();
            for (int i = playerNames.length - 1; i > 0; i--)
            {
                swapNames(i, random.nextInt(i + 1));
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
        
    //Getters role based
        /**
         * Gets all werewolves players index
         * @return : list of wrewolwes indexes
         */
        private static int[] getWerewolvesInt(){
            ArrayList<Integer> list = new ArrayList<>();
            for (int i = 0; i < WEREWOLF_NUMBER; i++) {
                if (villagers[playerNumber-i].getAlive()){
                    list.add(playerNumber-i);
                }
            }
            
            if (list.size()>0){ //if there is still werewolwes, return them in an array
                int[] array = new int[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    array[i] = list.get(i);
                }
                return array;
            }else{
                return null;
            }
        }
        /**
         * Gets all werewolves players names
         * @return : list of wrewolwes names
         */
        private static String[] getWerewolvesNames(){
            int[] intList = getWerewolvesInt();
            String[] list = new String[intList.length];
            for (int i = 0; i < intList.length; i++) {
                list[i]= playerNames[intList[i]];
            }
            return list;
        }
        
        /**
         * Gets all non-werewolves players index
         * @return : list of non-wrewolwes indexes
         */
        private static int[] getNonWerewolvesInt(){
            ArrayList<Integer> list = new ArrayList<>();
            for (int i = 0; i < playerNumber-WEREWOLF_NUMBER; i++) {
                if (villagers[i].getAlive()) {
                    list.add(i);
                }
            }
            
            if (list.size()>0){ //if there is still werewolwes, return them in an array
                int[] array = new int[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    array[i] = list.get(i);
                }
                return array;
            }else{
                return null;
            }
        }
        /**
         * Gets all non-werewolves players names
         * @return : list of non-wrewolwes names
         */
        private static String[] getNonWerewolvesNames(){
            int[] intList = getNonWerewolvesInt();
            String[] list = new String[intList.length];
            for (int i = 0; i < intList.length; i++) {
                list[i]= playerNames[intList[i]];
            }
            return list;
        }
    
        /**
         * Number of live werewolves
         * @return : number of live werewolves
         */
        private static int getWerewolvesNumber(){
            int[] werewolves = getWerewolvesInt();
            if (werewolves != null) {
                return werewolves.length;
            }else{
                return 0;
            }
        }
        /**
         * Number of live villagers
         * @return : number of live villagers
         */
        private static int getNonWerewolvesNumber(){
            int[] villagers = getNonWerewolvesInt();
            if (villagers != null) {
                return villagers.length;
            }else{
                return 0;
            }
        }
                
    //Victory
        /**
         * determines if any team won, and end the game
         */
        private static void victoryUpdate() {
            isEnded = true;
            if (getWerewolvesNumber()==0){//villagers victory
                Console.print("Villagers won!");
                if(getNonWerewolvesNumber()==0){
                    Console.print("No survivors.");
                }else{
                    Console.print("Survivors:");
                    Console.print(getNonWerewolvesNames());
                }
            }else if(getNonWerewolvesNumber()==0){//werewolves victory
                Console.print("Werewolves won!");
            }else{
                isEnded = false;
            }
        }
        
    //Night
        private static void night() {
            werewolvesTarget = -1;
            werewolvesAction();
            
            nightUpdate();
            victoryUpdate();
        }
        
        /**
         * Ask the Werewolves for a target
         */
        private static void werewolvesAction() {
            String[] werewolves = getWerewolvesNames();
            int[] votes = new int[werewolves.length];
            for (int i = 0; i < WEREWOLF_NUMBER; i++){
                Console.clear();
                votes[i] = Console.askInt(getNonWerewolvesNames(), werewolves[i]+": chose your victim!");
            }
            
            //chose randomely a target
            Random random = new Random();
            werewolvesTarget = votes[random.nextInt(werewolves.length)];
        }
        
        /**
         * Kill the dead villagers
         */
        private static void nightUpdate(){
            //if the target is valid
            if (werewolvesTarget>-1 && villagers[werewolvesTarget].getAlive()) {
                villagers[werewolvesTarget].kill();
                Console.print("There was a victim this night: "+playerNames[werewolvesTarget]+", who was "+villagers[werewolvesTarget]+". May (s)he rest in peace.");
            }else{
                Console.print("No one died this night!");
            }
        }
        
    //Day
}