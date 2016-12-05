/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiercelieu;

import java.util.HashSet;

/**
 *
 * @author e_marquer
 */
public class Tiercelieu {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Control control=new Control();
        //control.play();
        Villager v=new Villager();
        v.action(new HashSet<AbstractPersonnage>());
    }
    
}
