/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiercelieu;

import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author emarq_000
 */
public class Village {
    private AbstractPersonnage[] personnages;
    private int MODE=0; //0: Base, 1: Medium, 2: Advanced
    private ArrayList<String> playerNames;
    
    public Village() {
        
        Control.getPlayerNames();
        
        this.distributeCard();
    }
    
    private void distributeCard(){
        
    }
}
