/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiercelieu;

import java.util.ArrayList;
import java.util.Collections;
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
    private static int nights, days; //number of nights/days elapsed since the begining of the game

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        nights = 0;
        days = 0;
        setNumberOfPlayers();
        villagers = new Villager[playerNumber];
        villagerInit();
        playerNamesInit();
        gameLoop();
    }
    
    //Initialisations
        /**
         * ask the game master for the number of players
         * @return number of players
         */
        private static void setNumberOfPlayers() {
            playerNumber = Console.askInt(8,12,"Enter the number of players (between 8 and 12):");
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
            playerNames = new String[playerNumber];
            for (int i=0; i<playerNumber; i++){
                playerNames[i] = "player "+(i+1);
            }
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
        
        /**
         * use to transform aan array of indexes to an array of names
         * @param intArray : array of indexes
         * @return : array of names
         */
        private static String[] getNames(int[] intArray){
            String[] array = new String[intArray.length];
            for (int i = 0; i < intArray.length; i++) {
                array[i]= playerNames[intArray[i]];
            }
            return array;
        }
    
    //Getters role based
        /**
         * Gets all werewolves players index
         * now shuffeled!
         * @return : list of wrewolwes indexes
         */
        private static int[] getWerewolvesInt(){
            ArrayList<Integer> list = new ArrayList<>();
            for (int i = 0; i < WEREWOLF_NUMBER; i++) {
                if (villagers[playerNumber-i-1].getAlive()){
                    list.add(playerNumber-i-1);
                }
            }
            Collections.shuffle(list);
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
            return (getNames(getWerewolvesInt()));
        }
        
        /**
         * Gets all non-werewolves players index
         * now shuffeled!
         * @return : list of non-wrewolwes indexes
         */
        private static int[] getNonWerewolvesInt(){
            ArrayList<Integer> list = new ArrayList<>();
            for (int i = 0; i < playerNumber-WEREWOLF_NUMBER; i++) {
                if (villagers[i].getAlive()) {
                    list.add(i);
                }
            }
            Collections.shuffle(list);
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
            return (getNames(getNonWerewolvesInt()));
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
  
        private static int[] getAliveInt(){
            ArrayList<Integer> list = new ArrayList<>();
            for (int i = 0; i < playerNumber; i++) {
                if (villagers[i].getAlive()) {
                    list.add(i);
                }
            }
            
            if (list.size()>0){ //if there is still players, return them in an array
                int[] array = new int[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    array[i] = list.get(i);
                }
                return array;
            }else{
                return null;
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
                Console.print("Werwolves alive: "+getWerewolvesNumber());
                Console.print("Villagers alive: "+getNonWerewolvesNumber());
            }
            if (isEnded){
                Console.print("Time elapsed: "+nights+" nights and "+days+" days.");
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
            int[] villagersInt = getNonWerewolvesInt();
            String[] villagersName = getNames(villagersInt);
            int[] votes = new int[werewolves.length];
            for (int i = 0; i < werewolves.length; i++){
                votes[i] = villagersInt[Console.askInt(villagersName, werewolves[i]+": chose your victim!")];
                Console.clear();
            }
            
            //chose randomely a target
            Random random = new Random();
            werewolvesTarget = votes[random.nextInt(votes.length)];
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
        private static void day() {
            int vote = villageVote();
            if (villagers[vote] instanceof Werewolf) {
                 Console.print("The village killed a werewolf, "+playerNames[vote]+". Yay!");
            } else {
                Console.print("The village killed "+playerNames[vote]+", who was "+villagers[vote]+". May (s)he rest in peace.");
            }
            villagers[vote].kill();
            victoryUpdate();
            Console.askString(1,"Type something to continue");
        }
        
        /**
         * makes all the village vote
         * @return : index of finaly chosen villager
         */
        private static int villageVote(){
            String[] villagersName;
            int[] villagersInt = getAliveInt();
            int[] votes = new int[villagersInt.length]; //array of number of votes by villager
            for (int i=0; i<villagersInt.length; i++){
                votes[i] = 0;
            }
            
            for (int i=0; i<villagersInt.length; i++){
                villagersName = getNames(villagersInt);
                votes[Console.askInt(villagersName, "Villager "+villagersName[i]+", please chose who shall die!")] += 1;
                Console.clear();
            }
            
            int maxVotes = 0;
            Random random = new Random();
            for (int i=1; i<villagersInt.length; i++){
                if (votes[i]>votes[maxVotes]){
                    maxVotes = i;
                }else if (votes[i]==votes[maxVotes]){ //if two villagers have the samamount of votes
                    if (random.nextInt(10) > 4) {
                        maxVotes = i;
                    }
                }
            }
            return villagersInt[maxVotes];
        }
        
    //Game
        /**
         * Main loop
         */
        private static void gameLoop(){
            while (!isEnded){
                night();
                nights++;
                if(!isEnded){
                    day();
                    days++;
                }
            }
        }
}