/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiercelieu;

import java.util.Set;

/**
 *
 * @author e_marquer
 */
public class Villager extends AbstractVillager {

    public Villager() {
        
    }

    //toString
        @Override
        public String toString(){
            return this.faction() + ": comonner (" + this.getPosessor() + ")";
        }  
}
