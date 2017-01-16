package tiercelieu;

import java.util.Collections;
import java.util.ArrayList;

import setting.Config;

import entity.Villager;
import entity.Werewolf;
import entity.Witch;
import entity.Thief;
import entity.Cupidon;

/**
 * @author emarq_000, Claudel_Adrien
 */
public class Town{

    private ArrayList <Villager> villagers; //list of all villager
    private Controller controller; //link with the controller

    private String lover1, lover2; //lovers, set by cupidon

    /**
     *  Constructor
     *  @param playerNumber : number of players
     *  @param controller : controller to link with
     */
    public Town(int playerNumber, Controller controller){
        this.villagers = new ArrayList<Villager>();
        this.controller = controller;
        
        villagerInit(playerNumber);
    }

    /**
     * villagers' initialisation
     * @param playerNumber : number of players
     */
    private void villagerInit(int playerNumber) {
        int nbPlayer = playerNumber+Config.WEREWOLF_NUMBER+1;
        
        //random positions for names
    	ArrayList <Integer> pos = new ArrayList<Integer>();
    	for(int i = 0; i < nbPlayer; i++){
    		pos.add(i);
    	}
        
    	//shuffling positions
        Collections.shuffle(pos);

        nbPlayer = playerNumber + Config.ADDITONAL_ROLES_NUMBER;
        
            //adding the Villagers
            for (int i = 0; i < nbPlayer-Config.WEREWOLF_NUMBER-2; i++) {
                villagers.add(new Villager(pos.get(0)));
                pos.remove(0);
            }
            
            //adding the Witch
            villagers.add(new Witch(pos.get(0)));
            pos.remove(0);
            
            //adding the Cupidon
            villagers.add(new Cupidon(pos.get(0)));
            pos.remove(0);
            
            //adding the Thief
            villagers.add(new Thief(pos.get(0)));
            pos.remove(0);
            
            //adding the Werewolves
            for (int i = nbPlayer-Config.WEREWOLF_NUMBER; i < nbPlayer; i++) {
            	villagers.add(new Werewolf(pos.get(0)));
            	pos.remove(0);
            }
        
        //randomisation of roles order
        Collections.shuffle(villagers);
    }

    /**
     * get werewolves' vote
     */
    protected void werewolf(){
    	Config.print("Les loups-garous se reveillent !\n");
    	
    	for(Villager v : villagers){
            if(v instanceof Werewolf){
                //prevent werwolf to kill it's lover
                if (isLover(v)){
                    controller.setVote(((Werewolf)v).vote(getLoverless()));
                }else{
                    controller.setVote(((Werewolf)v).vote(villagers));
                }
            }
    	}
    }

    /**
     * calls Witch's healing methode
     * @return : true if the witch use her healing power, false otherwise
     */
    protected boolean witchHeal(){
    	Config.print("\n");

    	for(Villager v : villagers){
            if(v instanceof Witch){
                //controller.getVote() is the villager targeted by the werewolves
                //calls Witch.heal()
                return ((Witch) v).heal(villagers, controller.getVote());
            }
    	}
    	
    	//if the witch is not alive
    	return false;
    }

    /**
     * calls Witch's poisoning methode
     */
    protected void witchPoison(){
    	Config.print("\n");

    	int answer = -1;
    	for(Villager v : villagers){
            if(v instanceof Witch){
    	    	answer = ((Witch) v).poison(villagers);
            }
    	}
    	
    	if(answer != -1){
    		die(answer);
    	}
    }

    /**
     * ask Cupidon to form a couple
     */
    public void cupidon(){
    	Config.print("Cupidon va designer un couple\n");
    	
    	for(Villager v : villagers){
    		if(v instanceof Cupidon){
    			String couple[] = ((Cupidon) v).turn(villagers);
    			lover1 = couple[0];
    			lover2 = couple[1];
    		}
    	}
    }

    /**
     * get villagers' vote
     */
    protected void villager(){
    	Config.print("Les villageois se reveillent !\n");
    	
    	for(Villager v : villagers){
            controller.setVote(v.vote(villagers));
        }
    }
    
    /**
     * kill the villager at position i
     * @param i : position of villager to kill
     */
    protected void die(int i){
    	//prints the death of the villager
    	Config.print(villagers.get(i).getName() + " : " + villagers.get(i) +" a ete tue.\n");
        
        //remove the villager
        villagers.remove(i);
    
        //if the villager who die is the first lover, kill the second one
    	if(villagers.get(i).getName() == lover1){
            for(int y = 0; y < villagers.size(); y++){
		if(villagers.get(y).getName() == lover2){
                    Config.print(villagers.get(y).toString() + ", son amant, est mort de desespoir.\n");
        		villagers.remove(y);
                }
            }
        }
		
		//if the villager who die is the second lover, kill the first one
        else if(villagers.get(i).getName() == lover2){
			for(int y = 0; y < villagers.size(); y++){
				if(villagers.get(y).getName() == lover1){
					Config.print(villagers.get(y).toString() + ", son amant, est mort de desespoir.\n");
        			villagers.remove(y);
				}
			}
		}
    }
    
    /**
     * check if any werewolf is alive
     */
    public boolean wolfCheck(){
    	for(Villager v : villagers){
    		if(v instanceof Werewolf){
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * check if any non-werewolf villager is alive
     */
    public boolean villagerCheck(){
    	for(Villager v : villagers){
    		if(!(v instanceof Werewolf)){
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * check if any non-lover villager is alive
     */
    public boolean loverCheck(){
    	for(Villager v : villagers){
    		if(!isLover(v)){
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * return true if villager is a lover
     * @param v : villager to check
     * @return : true if villager at index i is a lover
     */
    protected boolean isLover(Villager v){
        return (v.getName() == lover1 || v.getName() == lover2);
    }
    
    /**
     * 
     * @return : villagers without lovers
     */
    protected ArrayList <Villager> getLoverless(){
        ArrayList <Villager> copy = new ArrayList<>();
        for(Villager v : villagers){
            if (isLover(v)) {
                copy.add(v);
            }          
    	}
        return copy;
    }
}
