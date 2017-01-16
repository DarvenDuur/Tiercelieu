package tiercelieu;

import setting.Config;

/**
 * @author emarq_000, Claudel_Adrien
 */
public class Controller {

	private Town town; //link to the town
    private int[] vote; //array of votes

    private int nights, days; //number of days and nights elapsed
	private boolean isEnded;
	
    
	/*
	 * conctructor of controler
     * @param nbPlayer : number of players
     */
    public Controller(int nbPlayer){
		this.town = new Town(nbPlayer, this);
		this.vote = new int[nbPlayer + Config.WEREWOLF_NUMBER+1];
		initVote();
		
		this.nights = 0;
		this.days = 0;
		this.isEnded = false;
	}

	
    /*
     * initialize votes array
     */
	private void initVote(){
        for (int i = 0; i < vote.length; i++)
        	vote[i] = 0;
    }

	
    /*
     * night actions cycle
     */
    private void night(){
    	initVote(); //initialize votes array
    	
        //setting the lovers
    	if(nights == 1){
    		town.cupidon();
    	}
    	
        //werewolves vote
    	town.werewolf();
    	
        //witch's turn
    	if(!town.witchHeal()){
    		town.die(getVote()); //if the witch does'nt heal the victim, the victim die
    	}else{
            town.witchPoison();
        }
    	
    	initVote(); //initialize votes array
    }
    
    
    /*
     * day actions cycle
     */
    private void day(){
    	initVote();
    	
    	town.villager();
    	town.die(getVote());
    	
    	initVote();
    }
    
    
    /*
     * add a vote to the vote array
     * @param i : index of the player
     */
    public void setVote(int i){
    	vote[i]++;
    }
    
    
    /*
     * return the villager with the most votes
     */
    public int getVote(){
    	int y = 0;
    	for(int i = 0; i < vote.length; i++){
    		if(vote[i] > y){
    			y = i;
    		}
    	}
    	return y;
    }

    
    /*
     * check if the lovers, the werewolves or the villagers won
     */
    private void victoryCheck(){
        //check if the werewolves won
    	if(!town.wolfCheck()){
    		Config.print("Les loups ont gagne.");
    		isEnded = true;
    	}
        //check if the villagers won
        if(!town.villagerCheck()){
    		Config.print("Les villageois ont gagne.");
    		isEnded = true;
    	}
        //check if the lovers won
        if(!town.loverCheck()){
    		Config.print("Les amoureux ont gagne.");
    		isEnded = true;
    	}
    }
    
    /*
     * main day/night loop
     */
    public void start(){

		while (!isEnded){
            //night cycle
            nights++;
            Config.print("Nuit "+ nights + "\n\n");
            night();
            
            victoryCheck();
            if(!isEnded){
                //day cycle
                days++;
                Config.print("Jours "+ days + "\n\n");
                day();
            }
            
            victoryCheck(); //check if any faction won
        }
    }
}
