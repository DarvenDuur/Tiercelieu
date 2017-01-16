package entity;

/**
 * @author emarq_000, Claudel_Adrien
 */
public class Thief extends Villager {
	
    /**
     * 's constructor
     * @param i : index of player
     */
	public Thief(int i){
		super(i);
	}
    
    @Override
    public String toString(){
        return ("Thief");
    }
}