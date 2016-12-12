/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiercelieu;

import java.util.ArrayList;

/**
 *
 * @author emarq_000
 */
public class GameControl {
    
    /* array of villagers */
    private Villager[] villagers;
    private String[] playerNames;
    
    /**
     * Basic constructor, without special villagers
     * @param playerNumber
     *          number of players, set externally
     */
    public GameControl(int playerNumber){
        Math.random();
        villagers = new Villager[playerNumber];
        this.villagerInit();
        this.playerNameInit();
    }
    
    //---------- Accessors ----------
        /**
         * 
         */
        protected String getVillagerName(int index) {
            return 
        }
    //---------- Initialisors ----------
    
    
}
