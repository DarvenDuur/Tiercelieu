package entity;

/**
 * @author emarq_000, Claudel_Adrien
 */
public class Werewolf extends Villager {

    /**
     * Werewolf's constructor
     * @param i : index of player
     */
    public Werewolf(int i){
        super(i);
    }
    
    @Override
    public String toString(){
        return ("Loup-garou");
    }
}