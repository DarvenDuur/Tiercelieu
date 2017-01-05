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
 * TO DO
 * thief
 * remove unnafected from getAlives
 */


/**
 *
 * @author emarq_000
 */
public class Tiercelieu {
    //if true, set the name of the players as "player [player number]" in spite of asking the user (debug use)
    private static final boolean AUTO_PLAYER_NAMES = true; 
    //if true, will show the number of survivors of each faction at the end of each turn
    private static final boolean SHOW_SURVIVORS = true; 
    
    private static Villager[] villagers; //array of villagers
    private static String[] playerNames; //array of names
    private static int playerNumber; //number of players
    private static final int WEREWOLF_NUMBER = 2; //number of werevolves
    private static final int ADDITONAL_ROLES_NUMBER = 2; //number of undistributed cards
    
    private static int werewolvesTarget = -1; //index of the player to be killed by the werewolves, -1 for undefined
    private static int witchTarget = -1; //index of the player to be killed, -1 for undefined
    private static int loverIndex1, loverIndex2; //indexs of the two lovers set by cupidon
    private static boolean newCupidon = true; //true if the thief has exchanged cupidon card
    
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
        swowPlayerRole();
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
            for (int i = 0; i < playerNumber-WEREWOLF_NUMBER-2; i++) {
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
            
            //set roles that won't be afectated to a player
            setUnafected();
            
            //role distribution
            shuffleNames();
        }

        /**
         * For each player show player name, and after asking for confirmation,
         * show the role of the player
         */
        private static void swowPlayerRole() {
            //randomised array, to avoid deducting role by position
            int[] playerIndex = getAliveIndex();
            
            //for each player
            for (int i=0; i<playerNumber; i++){
                Console.print("Player "+playerNames[playerIndex[i]]);
                Console.waitContinue("Input anything to see your class");
                Console.print("You are "+villagers[playerIndex[i]].toString());
                Console.waitContinue();
                Console.clear();
            }
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
        private static String[] toNames(int[] indexArray){
            String[] array = new String[indexArray.length];
            for (int i = 0; i < indexArray.length; i++) {
                array[i]= playerNames[indexArray[i]];
            }
            return array;
        }
    
    //Operations on lovers
        /**
         * Shows a message depending of the lover of villager at index
         * Does not care if villager at index or lover is alive
         * @param index : index of villager
         * @return : message if villager has lover, "" otherwise
         */
        private static void tellLover(int index){
            if (index==loverIndex1){
                Console.print("You are in love with "+playerNames[loverIndex2]+"! Have fun ;)");
            }else if (index==loverIndex2){
                Console.print("You are in love with "+playerNames[loverIndex1]+"! Have fun ;)");
            }
        }
    
        /**
         * Removes the lovers' index from inputed array of indexes
         * @param villagersIndex : array of indexes
         * @return : array of indexes without lovers' index
         */
        private static int[] toLoverless(int[] villagersIndex){
            //find the number of lovers
            int lovers = 0;
            for (int i=0; i<villagersIndex.length; i++) {
                if (villagersIndex[i]==loverIndex1 || villagersIndex[i]==loverIndex2) {
                    lovers++;
                }
            }
            
            //removes the lovers
            int[] loverlessVillagersIndex = new int[villagersIndex.length-lovers];
            int index = 0;
            for (int i=0; i<villagersIndex.length; i++) {
                if (villagersIndex[i]!=loverIndex1 && villagersIndex[i]!=loverIndex2) {
                    loverlessVillagersIndex[index]=villagersIndex[i];
                    index++;
                }
            }
            return loverlessVillagersIndex;
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
         * Gets index of the cupidon
         * @return : index of the cupidon
         */
        private static int getCupidonIndex(){
            return playerNumber-WEREWOLF_NUMBER-2;
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
         *      if couple wins, short message
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
                    Console.print(toNames(getAliveIndex()));
                }
                
            //werewolves victory
            }else if(getNonWerewolvesNumber()==0){
                Console.print("Werewolves won!");
                
            //couple victory (only if from different factions)
            }else if(getWerewolvesNumber()==1 && getNonWerewolvesNumber()==1 && villagers[loverIndex1].getAlive() && villagers[loverIndex2].getAlive()){
                Console.print("The forbidden love was victorious!");
                
            //no victory
            }else{
                isEnded = false;
                if (SHOW_SURVIVORS){
                    Console.print("Werwolves alive: "+getWerewolvesNumber());
                    Console.print("Villagers alive: "+getNonWerewolvesNumber());
                }
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
            
            //thief action (first night only)
            if(nights==1){
                preliminaryThiefAction();
            }
            
            //tells the game master that the turn has begun
            Console.print("Cupidon wakes up");
            Console.waitContinue();
            //cupidon action
            if(newCupidon){
                cupidonAction();
            }
            //tells the game master that the turn has ended
            Console.print("Cupidon goes back to sleep");
            
            //werewolves action
            Console.print("Werewolves");
            werewolvesAction();
            
            //witch action
            Console.print("Witch");
            witchAction();
            
            //thief action (first night only)
            if(nights==1){
                thiefAction();
            }
            
            nightUpdate();
            victoryUpdate();
        }
        
        /**
         * Ask the Werewolves for a target (werewolves excluded, lover excluded)
         * Sets werewolvesTarget
         */
        private static void werewolvesAction() {
            //tells the game master that the turn has begun
            Console.print("The werewolves wake up");
            Console.waitContinue(); 
            
            int[] werewolvesIndex = getWerewolvesIndex();
            String[] werewolvesName = toNames(werewolvesIndex);
            
            //possible targets (werewolves excluded)
            int[] villagersIndex = getNonWerewolvesIndex();
            String[] villagersName = toNames(villagersIndex);
            
            //array of voted targets
            int[] votes = new int[werewolvesIndex.length];
            
            //gets the vote of every werewolf
            for (int i = 0; i < werewolvesIndex.length; i++){
                //if the werewolf is a lover
                if (werewolvesIndex[i]==loverIndex1 || werewolvesIndex[i]==loverIndex2){
                    //removes the lovers from possible targets
                    int[] loverlessVillagersIndex = toLoverless(villagersIndex);
                    
                    //tell the lover
                    tellLover(werewolvesIndex[i]);
                    
                    //ask for the target
                    votes[i] = loverlessVillagersIndex[Console.askIndex(toNames(loverlessVillagersIndex), werewolvesName[i]+": chose your victim!")];
                
                //if the werewolf is not a lover
                }else{
                    //ask for the target
                    votes[i] = villagersIndex[Console.askIndex(villagersName, werewolvesName[i]+": chose your victim!")];
                }
                Console.clear();
            }
            
            //chose randomely a target, to clear eventual ex aequo
            Random random = new Random();
            werewolvesTarget = votes[random.nextInt(votes.length)];
            
            //tells the game master that the turn has ended
            Console.print("The werewolves go back to sleep");
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
                //tells the game master that the turn has begun
                Console.print("The witch wakes up");
                Console.waitContinue();
                
                //begin the message with the name of the witch
                String message = playerNames[witchIndex];
                
                if (witch.canHeal()) {
                    //complete the message with available potions
                    message += ", you have a healing potion";
                    if (witch.canKill()) {
                        message += " and a poison potion";
                    }
                    Console.print(message+" left.");
                    
                    //tell the lover
                    tellLover(witchIndex);
                    
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
                             witchTarget = targetIndex[Console.askIndex(toNames(targetIndex), "Chose your victim!")];
                             witch.useKill();
                        }
                    }
                    Console.clear();
                    
                //if the player can only kill
                }else if (witch.canKill()) {
                    //complete the message with available potion
                    message += ", you have a poison potion left.";
                    Console.print(message);
                    
                    //tell the lover
                    tellLover(witchIndex);
                    
                    int[] aliveIndex = getAliveIndex();
                    
                    //check if player wants to kill a villager
                    if (Console.askIndex(new String[]{"Yes","No"}, "Kill someone?")==0) {
                        //gets the victim
                        witchTarget = aliveIndex[Console.askIndex(toNames(aliveIndex), "Chose your victim!")];
                        witch.useKill();
                    }
                    Console.clear();
                }
                //tells the game master that the turn has ended
                Console.print("The witch goes bacck to sleep.");
            }
        }
        
        /**
         * Ask Cupidon for two lovers
         * Sets loverIndex1, loverIndex2 (different)
         */
        private static void cupidonAction() {
            //if cupidon is alive
            if (villagers[getCupidonIndex()].getAlive()){
                //if cupidon wish to use its power
                if (Console.askIndex(new String[]{"Yes","No"},
                        (loverIndex2 < 0 || loverIndex1 < 0) ? "Do you want to set a couple?" : "Do you want to change the couple?")==0){
                    newCupidon = false;

                    int[] aliveIndex = getAliveIndex();

                    //set the first lover
                    loverIndex1 = aliveIndex[Console.askIndex(toNames(aliveIndex), "Chose the first lover:")];

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
                    loverIndex2 = newIndex[Console.askIndex(toNames(newIndex), "Chose the second lover:")];
                    
                    //show the two lovers to the game master
                    Console.print(playerNames[loverIndex1]+" and "+playerNames[loverIndex2]+" are now lovers!");
                }
            }
        }
        
        /**
         * Allows the thief to chose a card in the undistributed cards.
         * If all the werewolves are undistributed
         */
        private static void preliminaryThiefAction() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        /**
         * Allows the thief to exchange the cards of two playes
         */
        private static void thiefAction() {
            int thiefIndex = getThiefIndex();
            
            //if the thief is alive
            if (villagers[thiefIndex].getAlive()){
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
            }
        }
        
        /**
         * Kill the dead villagers
         *      Werewolf victim (if not healed)
         *      Witch victim (if there is)
         *      Lovers suicide
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
            
            //if the first lover died, kills the second one if alive
            if (!villagers[loverIndex1].getAlive() && villagers[loverIndex2].getAlive()){
                villagers[loverIndex2].kill();
                Console.print(playerNames[loverIndex2]+", "+playerNames[loverIndex1]+"'s lover, commited suicide out of despair!");
            }
            
            //if the second lover died, kills the first one if alive
            else if (villagers[loverIndex1].getAlive() && !villagers[loverIndex2].getAlive()){
                villagers[loverIndex1].kill();
                Console.print(playerNames[loverIndex1]+", "+playerNames[loverIndex2]+"'s lover, commited suicide out of despair!");
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
            Console.waitContinue() ;
        }
        
        /**
         * Ask every live villager for a vote
         * A villager can vote for himself
         * The villager with the most votes is chosen
         * In case of ex aequo, coice is randomised
         * @return : index of chosen villager
         */
        private static int villageVote(){
            int[] villagersIndex = getAliveIndex();
            String[] villagersName = toNames(villagersIndex);
            
            //array of number of votes by villager
            int[] votes = new int[villagersIndex.length];
            for (int i=0; i<villagersIndex.length; i++){
                votes[i] = 0;
            }
            
            for (int i=0; i<villagersIndex.length; i++){
                //if the villager is a lover
                if (villagersIndex[i]==loverIndex1 || villagersIndex[i]==loverIndex2){
                    //removes the lovers from possible targets
                    int[] loverlessVillagersIndex = toLoverless(villagersIndex);
                    
                    //tell the lover
                    tellLover(villagersIndex[i]);
                    
                    votes[Console.askIndex(toNames(loverlessVillagersIndex), "Villager "+villagersName[i]+", please chose who shall die!")] += 1;
                
                //if the villager is not a lover
                }else{
                    //adds 1 at votes[index], index beeing the answer of the Cinsole.askIndex()
                    votes[Console.askIndex(villagersName, "Villager "+villagersName[i]+", please chose who shall die!")] += 1;
                }
                
                Console.clear();
            }
            
            //analyze the votes to find who has the most votes
            int maxVotes = 0;
            Random random = new Random();
            for (int i=1; i<villagersIndex.length; i++){
                if (votes[i]>votes[maxVotes]){
                    maxVotes = i;
                    
                //if two villagers have the samamount of votes, one of them is chosen randomly
                }else if (votes[i]==votes[maxVotes]){
                    if (random.nextInt(10) > 4) {
                        maxVotes = i;
                    }
                }
            }
            
            return villagersIndex[maxVotes];
        }
        
    //Game loop
        /**
         * Main loop
         * Loops through the day/night cycle while there is no winner
         * Sets nights, days
         */
        private static void gameLoop(){
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

    private static int getThiefIndex() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static boolean isAffected(int cupidonIndex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

        

    
}