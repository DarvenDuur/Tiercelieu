/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiercelieu;

/**
 *
 * @author e_marquer
 */
public class Villager extends AbstractPersonnage {

    public Villager() {
        super(false);
    }

    //toString
        @Override
        public String toString(){
            return this.faction() + ": comonner (" + this.getPosessor() + ")";
        }  
}
