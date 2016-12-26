/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiercelieu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author emarq_000
 */
public class Tiercelieu {
    //if true, set the name of the players as "player [player number]" (debug use)
    private static final boolean AUTO_PLAYER_NAMES = false; 
    
    private static Villager[] villagers; //array of villagers
    private static String[] playerNames; //array of names
    private static int playerNumber; //number of players
    private static final int WEREWOLF_NUMBER = 2; //number of werevolves
    
    private static int werewolvesTarget = -1; //index of the player to be killed by the werewolves, -1 for undefined
    private static int witchTarget = -1; //index of the player to be killed, -1 for undefined
    private static int loverIndex1, loverIndex2; //indexs of the two lovers set by cupidon
    
    private static boolean isEnded = false; //if true, the game ends
    private static int nights, days; //number of nights/days elapsed since the begining of the game

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        nights = 0;
        days = 0;
        loverIndex1 = -1;
        loverIndex2 = -1;
        setNumberOfPlayers();
        villagers = new Villager[playerNumber];
        villagerInit();
        playerNamesInit();
        gameLoop();
    }
    
    //Initialisations
        /**
         * Ask the game master for the number of players
         * The number of players is between 
         * Sets playerNumber
         */
        private static void setNumberOfPlayers() {
            playerNumber = Console.askInt(8,12,"Enter the number of players (between 8 and 12):");
        }

        /**
         * Setup roles at set position
         *      the werewolves are at the end
         *      the witch is right before the werewolves
         *      the cupidon is right before the witch
         * Sets villagers
         */
        private static void villagerInit() {
            //add the Villagers
            for (int i = 0; i < playerNumber-WEREWOLF_NUMBER-3; i++) {
                villagers[i] = new Villager();
            }
            
            //add the Witch
            villagers[playerNumber-WEREWOLF_NUMBER-1] = new Witch();
            
            //add the Cupidon
            villagers[playerNumber-WEREWOLF_NUMBER-2] = new Cupidon();

            //add the Werewolves
            for (int i = playerNumber-WEREWOLF_NUMBER; i < playerNumber; i++) {
                villagers[i] = new Werewolf();
            }
        }

        /**
         * Get (or gernerates) the player names and atribute roles
         * Sets playerNames
         */
        private static void playerNamesInit() {
            //generates the names automaticaly ...
            if (AUTO_PLAYER_NAMES){
                playerNames = new String[playerNumber];
                for (int i=0; i<playerNumber; i++){
                    playerNames[i] = "player "+(i+1);
                }
                
            //... or ask the names by input
            }else{
                playerNames = Console.askString(playerNumber, "Input player names :").clone();
            }
            
            //role distribution
            shuffleNames();
        }

    //Operations on names
        /**
         * Basic way to shuffle the name
         * Simulate role distribution
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
         * Changes playerNames
         * @param i : first position
         * @param j : second position
         */
        private static void swapNames(int i,int j) {
            String temp = playerNames[j];
            playerNames[j] = playerNames[i];
            playerNames[i] = temp;
        }
        
        /**
         * Transforms an array of indexes to an array of corresponding names
         * @param indexArray : array of indexes (between 0 and the number of players-1)
         * @return : array of names
         */
        private static String[] getNames(int[] indexArray){
            String[] array = new String[indexArray.length];
            for (int i = 0; i < indexArray.length; i++) {
                array[i]= playerNames[indexArray[i]];
            }
            return array;
        }
    
    //Getters role based
        /**
         * Gets index of all live werewolves players
         * @return : list of wrewolwes indexes
         */
        private static int[] getWerewolvesIndex(){
            ArrayList<Integer> list = new ArrayList<>();
            
            //get all live werewolves
            for (int i = 0; i < WEREWOLF_NUMBER; i++) {
                if (villagers[playerNumber-i-1].getAlive()){
                    list.add(playerNumber-i-1);
                }
            }

            //if there is still werewolwes, return them in an array
            if (list.size()>0){
                int[] array = new int[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    array[i] = list.get(i);
                }
                return array;
                
            //if there is no werewolf left, return null
            }else{
                return null;
            }
        }
        
        /**
         * Number of live werewolves
         * @return : number of live werewolves
         */
        private static int getWerewolvesNumber(){
            int[] werewolves = getWerewolvesIndex();
            if (werewolves != null) {
                return werewolves.length;
            }else{
                return 0;
            }
        }
        
        /**
         * Gets index of all live non-werewolves players
         * now shuffeled! (to make it harder do know who has wich role)
         * @return : list of non-wrewolwes indexes
         */
        private static int[] getNonWerewolvesIndex(){
            ArrayList<Integer> list = new ArrayList<>();
            
            //get all live werewolves
            for (int i = 0; i < playerNumber-WEREWOLF_NUMBER; i++) {
                if (villagers[i].getAlive()) {
                    list.add(i);
                }
            }
            
            //shuffle the list
            Collections.shuffle(list);
            
            //if there is still non-werewolwes players, return them in an array
            if (list.size()>0){
                int[] array = new int[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    array[i] = list.get(i);
                }
                return array;
                
            //if there is no non-werewolf player left, return null
            }else{
                return null;
            }
        }
    
        /**
         * Number of live villagers
         * @return : number of live villagers
         */
        private static int getNonWerewolvesNumber(){
            int[] villagersIndex = getNonWerewolvesIndex();
            if (villagersIndex != null) {
                return villagersIndex.length;
            }else{
                return 0;
            }
        }
        
        /**
         * Gets index of the witch
         * @return : index of the witch
         */
        private static int getWitchIndex(){
            return playerNumber-WEREWOLF_NUMBER-1;
        }
        
        /**
         * Gets index of all live players
         * now shuffeled! (to make it harder do know who has wich role)
         * @return : list of non-wrewolwes indexes
         */
        private static int[] getAliveIndex(){
            ArrayList<Integer> list = new ArrayList<>();
            
            //get all live players
            for (int i = 0; i < playerNumber; i++) {
                if (villagers[i].getAlive()) {
                    list.add(i);
                }
            }
            
            //shuffle the list
            Collections.shuffle(list);
            
            //if there is still players, return them in an array
            if (list.size()>0){
                int[] array = new int[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    array[i] = list.get(i);
                }
                return array;
                
            //if there is no player left, return null
            }else{
                return null;
            }
        }
    
    //Victory
        /**
         * Determines if any team won, and end the game according to it
         * Prints a message depending of the outcome
         *      if no victory, survivor list
         *      if werewolwes win, short message
         *      if villagers win, short message and survivor list
         *      [todo] if couple wins, short message
         * When the game ends, prints the duration of the game (in days and nights)
         * Sets isEnded
         */
        private static void victoryUpdate() {
            isEnded = true;
            
            //villagers victory
            if (getWerewolvesNumber()==0){
                Console.print("Villagers won!");
                if(getNonWerewolvesNumber()==0){
                    Console.print("No survivor.");
                }else{
                    Console.print("Survivors:");
                    Console.print(getNames(getAliveIndex()));
                }
                
            //werewolves victory
            }else if(getNonWerewolvesNumber()==0){
                Console.print("Werewolves won!");
                
            //no victory
            }else{
                isEnded = false;
                Console.print("Werwolves alive: "+getWerewolvesNumber());
                Console.print("Villagers alive: "+getNonWerewolvesNumber());
            }
            
            //prints game duration
            if (isEnded){
                Console.print("Time elapsed: "+nights+" nights and "+days+" days.");
            }
        }
        
    //Night
        /**
         * Calls werewolves and witch actions
         * Check for victory
         */
        private static void night() {
            //clear targets
            werewolvesTarget = -1;
            witchTarget = -1;
            
            //werewolves action
            Console.print("Werewolves");
            werewolvesAction();
            
            //witch action
            Console.print("Witch");
            witchAction();
            
            nightUpdate();
            victoryUpdate();
        }
        
        /**
         * Ask the Werewolves for a target (werewolves excluded)
         * Sets werewolvesTarget
         */
        private static void werewolvesAction() {
            String[] werewolves = getNames(getWerewolvesIndex());
            
            //possible targets (werewolves excluded)
            int[] villagersInt = getNonWerewolvesIndex();
            String[] villagersName = getNames(villagersInt);
            
            //array of voted targets
            int[] votes = new int[werewolves.length];
            
            //gets the vote of every werewolf
            for (int i = 0; i < werewolves.length; i++){
                votes[i] = villagersInt[Console.askIndex(villagersName, werewolves[i]+": chose your victim!")];
                Console.clear();
            }
            
            //chose randomely a target, to clear eventual ex aequo
            Random random = new Random();
            werewolvesTarget = votes[random.nextInt(votes.length)];
        }
        
        /**
         * Ask the Witch for an action (heal or kill), if it's available
         * The witch sees the victim of the werewolves 
         * only if she still has her healing potion
         * Sets witchTarget (if kill)
         * Unsets werewolvesTarget (if heal)
         */
        private static void witchAction() {
            //recuperate the witch,and getting access to Witch methodes
            int witchIndex = getWitchIndex();
            Witch witch = (Witch)villagers[witchIndex];
            
            //if the witch is alive
            if (witch.getAlive()) {
                //begin the message with the name of the witch
                String message = playerNames[witchIndex];
                
                if (witch.canHeal()) {
                    //complete the message with available potions
                    message += ", you have a healing potion";
                    if (witch.canKill()) {
                        message += " and a poison potion";
                    }
                    Console.print(message+" left.");
                    
                    //shows the victim
                    Console.print(playerNames[werewolvesTarget]+" has been killed by the werewolves.");
                    
                    //check if player wants to heal the victim
                    if (Console.askIndex(new String[]{"Yes","No"}, "Resurrect him/her?")==0) {
                        //save the victim by removing the target from it
                        werewolvesTarget = -1;
                        witch.useHeal();
                        
                    //if possible to kill another villager
                    }else if (witch.canKill()) {
                        int[] aliveIndex = getAliveIndex();
                        
                        //exclude index of victim
                        int[] targetIndex = new int[aliveIndex.length-1];
                        int index = 0;
                        for (int i=0; i<aliveIndex.length; i++) {
                            if (aliveIndex[i]!=werewolvesTarget) {
                                targetIndex[index]=aliveIndex[i];
                                index++;
                            }
                        }
                        
                        //check if player wants to kill a villager
                        if (Console.askIndex(new String[]{"Yes","No"}, "Kill someone?")==0) {
                            //gets the victim
                             witchTarget = targetIndex[Console.askIndex(getNames(targetIndex), "Chose your victim!")];
                             witch.useKill();
                        }
                    }
                    Console.clear();
                    
                //if the player can only kill
                }else if (witch.canKill()) {
                    //complete the message with available potion
                    message += ", you have a poison potion left.";
                    Console.print(message);
                    int[] aliveIndex = getAliveIndex();
                    
                    //check if player wants to kill a villager
                    if (Console.askIndex(new String[]{"Yes","No"}, "Kill someone?")==0) {
                        //gets the victim
                        witchTarget = aliveIndex[Console.askIndex(getNames(aliveIndex), "Chose your victim!")];
                        witch.useKill();
                    }
                    Console.clear();
                }
            }
        }
        
        /**
         * Ask Cupidon for two lovers
         * Sets loverIndex1, loverIndex2 (different)
         */
        private static void cupidonAction() {
            int[] aliveIndex = getAliveIndex();
            
            //set the first lover
            loverIndex1 = aliveIndex[Console.askIndex(getNames(aliveIndex), "Chose the first lover:")];
            
            //excluding index of first lover
            int[] newIndex = new int[aliveIndex.length-1];
            int index = 0;
            for (int i=0; i<aliveIndex.length; i++) {
                if (aliveIndex[i]!=loverIndex1) {
                    newIndex[index]=aliveIndex[i];
                    index++;
                }
            }
            
            //set the second lover
            loverIndex2 = newIndex[Console.askIndex(getNames(newIndex), "Chose the second lover:")];
        }
        
        /**
         * Kill the dead villagers
         *      Werewolf victim (if not healed)
         *      Witch victim (if there is)
         *      [todo] Lovers suicide
         * Show messages about the death
         */
        private static void nightUpdate(){
            //check the targets are valid (existing and alive)
            boolean werewolvesTargetValid, witchTargetValid;
            werewolvesTargetValid = werewolvesTarget>-1 && villagers[werewolvesTarget].getAlive();
            witchTargetValid = witchTarget>-1 && villagers[witchTarget].getAlive();
            
            //two death (werewolves and witch kill)
            if (werewolvesTargetValid && witchTargetValid) {
                villagers[werewolvesTarget].kill();
                villagers[witchTarget].kill();
                Console.print("There were two victims this night: "
                        +playerNames[werewolvesTarget]+", who was "+villagers[werewolvesTarget]+", and "
                        +playerNames[witchTarget]+", who was "+villagers[witchTarget]
                        +". May they rest in peace.");
            
            //one death (werewolves kill and witch absention)
            }else if (werewolvesTargetValid) {
                villagers[werewolvesTarget].kill();
                Console.print("There was a victim this night: "+playerNames[werewolvesTarget]+", who was "+villagers[werewolvesTarget]+". May (s)he rest in peace.");
            
            /*[obselete] one death (witch)
            }else if (witchTargetValid) {
                villagers[witchTarget].kill();
                Console.print("There was a victim this night: "+playerNames[witchTarget]+", who was "+villagers[witchTarget]+". May (s)he rest in peace.");*/
            
            //no death (werewolves kill and witch heal)
            }else{
                Console.print("No one died this night!");
            }
        }
        
    //Day
        /**
         * Call the village vote, kill the chosen victim, 
         * and prints a message depending of the victim.
         * Call victoryUpdate()
         */
        private static void day() {
            int vote = villageVote();
            
            if (villagers[vote] instanceof Werewolf) {
                 Console.print("The village killed a werewolf, "+playerNames[vote]+". Yay!");
            } else {
                Console.print("The village killed "+playerNames[vote]+", who was "+villagers[vote]+". May (s)he rest in peace.");
            }
            
            villagers[vote].kill();
            
            //check if the game has ended
            victoryUpdate();
            
            //waiting for the user to read
            Console.askString(1,"Type something to continue");
        }
        
        /**
         * Ask every live villager for a vote
         * A villager can vote for himself
         * The villager with the most votes is chosen
         * In case of ex aequo, coice is randomised
         * @return : index of chosen villager
         */
        private static int villageVote(){
            int[] villagersInt = getAliveIndex();
            String[] villagersName = getNames(villagersInt);
            
            //array of number of votes by villager
            int[] votes = new int[villagersInt.length];
            for (int i=0; i<villagersInt.length; i++){
                votes[i] = 0;
            }
            
            for (int i=0; i<villagersInt.length; i++){
                //adds 1 at votes[index], index beeing the answer of the Cinsole.askIndex()
                votes[Console.askIndex(villagersName, "Villager "+villagersName[i]+", please chose who shall die!")] += 1;
                Console.clear();
            }
            
            //analyze the votes to find who has the most votes
            int maxVotes = 0;
            Random random = new Random();
            for (int i=1; i<villagersInt.length; i++){
                if (votes[i]>votes[maxVotes]){
                    maxVotes = i;
                    
                //if two villagers have the samamount of votes, one of them is chosen randomly
                }else if (votes[i]==votes[maxVotes]){
                    if (random.nextInt(10) > 4) {
                        maxVotes = i;
                    }
                }
            }
            
            return villagersInt[maxVotes];
        }
        
    //Game loop
        /**
         * Main loop
         * Loops through the day/night cycle while there is no winner
         * Sets nights, days
         */
        private static void gameLoop(){
            //first night only
            cupidonAction();
            
            while (!isEnded){
                nights++;
                Console.print("Night "+nights);
                night();
                if(!isEnded){
                    days++;
                    Console.print("Day "+days);
                    day();
                }
            }
        }
}