/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiercelieu;

import java.util.HashSet;

/**
 *
 * @author emarq_000
 */
public class Village {
    private HashSet<AbstractPersonnage> personnages;
    private int MODE=0; //0: Base, 1: Medium, 2: Advanced
    private String[] playerNames;
    
    public Village(int numberOfPlayer) {
        switch (MODE){
            case 0: //Base mode (8p dont 2 Ww)
                playerNames = Control.getPlayerNames(8);
                for (int i=0; i<2; i++) {
                    this.personnages.add(new Werewolf());
                }
                for (int i=0; i<6; i++) {
                    this.personnages.add(new Villager());
                }
                break;

            case 1: //Medium mode (8-12p dont 2 Ww)
                playerNames = Control.getPlayerNames(numberOfPlayer);
                for (int i=0; i<2; i++) {
                    this.personnages.add(new Werewolf());
                }
                for (int i=0; i<numberOfPlayer-2; i++) {
                    this.personnages.add(new Villager());
                }
                break;

            case 2: //Advanced mode (8-12p dont 2 Ww, 1 Sorceress, 1 Cupidon, 1 Thief)
                playerNames = Control.getPlayerNames(numberOfPlayer);
                for (int i=0; i<2; i++) {
                    this.personnages.add(new Werewolf());
                }
                this.personnages.add(new Sorceress());
                this.personnages.add(new Cupidon());
                this.personnages.add(new Thief());
                for (int i=0; i<numberOfPlayer-5; i++) {
                    this.personnages.add(new Villager());
                }
                break;
        }
    }
    
    
}
