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
public class Villager {
    private boolean alive;
    
    /**
     * default
     */
    public Villager() {
        this.alive = true;
    }
    
    
    public boolean getAlive(){
        return this.alive;
    }

    /**
     * changes the value of alive
     * @param newValue new boolean value for alive
     */
    protected void setAlive(boolean newValue){
        this.alive = newValue;
    }
    protected void kill() {
        this.setAlive(false);
    }
    
}
