package setting;
import java.util.Scanner;

/**
 * @author emarq_000, Claudel_Adrien
 */
public class Config {
	
    //if true, set the name of the players as "player [player number]" 
    public static final boolean AUTO_PLAYER_NAMES = true;
    
    //if true, set the unaffected roles to the werewolves positon (debug use)
    public static final boolean FORCE_UNAFFECTED_WEREWOLVES = false;
    
    //if true, skip presentation of roles (debug use)
    public static final boolean SKIP_ROLES_PRESENTATION = true;

    //if true, will show the number of survivors of each faction at the end of each turn
    public static final boolean SHOW_SURVIVORS = true;
    
    //number of werevolves
    public static final int WEREWOLF_NUMBER = 2;
    
    //number of undistributed cards
    public static final int ADDITONAL_ROLES_NUMBER = 2;
    
    public static final Scanner SCANNER = new Scanner(System.in);
    
    public static void print(String s){
    	System.out.print(s);
    }
}
