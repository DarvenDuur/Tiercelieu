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
class Witch extends Villager {
    private boolean healPotion, killPotion;

    public Witch() {
        healPotion = true;
        killPotion = true;
    }
    
    public boolean canHeal(){
        return healPotion;
    }
    public boolean canKill(){
        return killPotion;
    }
    
    public void useHeal(){
        healPotion = false;
    }
    public void useKill(){
        killPotion = false;
    }
    
    @Override
    public String toString(){
        return ("witch");
    }
}
