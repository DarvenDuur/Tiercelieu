package entity;
import setting.Config;
import java.util.ArrayList;
import java.util.InputMismatchException;


/**
 * @author emarq_000, Claudel_Adrien
 */
public class Villager {
	
    private boolean alive;
    protected String name;

    /**
     * 's constructor
     * @param i : index of player
     */
    public Villager(int i) {
    	
        this.alive = true;
        
        namesInit(i);
    }


    /**
     * Get (or gernerates) the player names and atribute roles
     * Sets playerNames
     */
    private void namesInit(int i){
        //generates the names automaticaly
        if (Config.AUTO_PLAYER_NAMES){
               name = "Player_" + i;
        }
        
        //or ask the game master for them
    	else{
    		System.out.print("Input player name : ");

    		name = Config.SCANNER.next();
    	}
    }
    
    /*
     *  Tour de villageois
     */
	public int vote(ArrayList<Villager> villager) {
    	
    	Config.print(this.getName() + " qui voulez-vous tuer ?\n");
    	
    	// Parcours des votes possibles
		for (int i = 0; i < villager.size(); i++){
			Villager v = villager.get(i);
			
			System.out.println(i + ". " + v.getName() + " : " + v);
		}
		
		// Saisie du vote
		int input = -1;
		do{
			try{
				input = Config.SCANNER.nextInt();
			}catch(InputMismatchException e){// Si la saisie n'est pas un nombre 
			System.out.println("Veuillez entrer un nombre !");
                    }catch(NumberFormatException e){// Si la saisie n'est pas un nombre 
			System.out.println("Veuillez entrer un nombre !");
                    }
		}
        // tant que le vote ne corespond a aucun villageois
		while(input < 0 && input > villager.size());
		
		return input;
	}
    
    /* Getter and Setter */
    public void kill() {
        this.alive = false;
    }
    
    public boolean isAlive(){
        return this.alive;
    }
    
    public String getName(){
    	return name;
    }
    
    @Override
    public String toString(){
        return ("Villageois");
    }

}