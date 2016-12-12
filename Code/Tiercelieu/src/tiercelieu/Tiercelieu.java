/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiercelieu;

/**
 *
 * @author emarq_000
 */
public class Tiercelieu {
    
    private static Villager[] villagers; //array of villagers
    private static String[] playerNames;
    private static int playerNumber = 8;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        playerNumber = setNumberOfPlayers();
        villagers = new Villager[playerNumber];
        villagerInit();
        playerNameInit();
    }
    
    /**
     * ask the game master for the number of players
     * @return number of players
     */
    private static int setNumberOfPlayers() {
        return 8;
    }

    /**
     * 
     */
    private static void villagerInit() {
        Scanner scanner= new Scanner();
        for(int i = 0; i < playerNumber; i++) {
            
        }
    }
    
    /**
     * 
     */
    private static void playerNameInit() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
