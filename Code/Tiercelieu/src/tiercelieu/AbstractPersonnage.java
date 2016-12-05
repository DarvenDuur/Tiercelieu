/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiercelieu;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author e_marquer
 */
public abstract class AbstractPersonnage {
    private boolean alive;
    private final boolean werewolf; //true if faction is werewolf
    
    private String possessor;
    
    protected AbstractPersonnage(boolean werewolf){
        this.alive = true;
        this.possessor = "";
        this.werewolf = werewolf;
    }
    
    //operators
        public boolean equals(AbstractPersonnage personnage) {
            return this.hashCode()==personnage.hashCode();
        }
        
    //possessor support
        public String getPosessor() {return this.possessor;}
        protected void setPosessor(String possessor) {this.possessor = possessor;}
        
    //life support
        public boolean isAlive(){
            return this.alive;
        }

        protected void kill(){
            this.alive = false;
        }
        
    //basic faction support
        public boolean isWerewolf(){return this.werewolf;}
        public boolean isVillager(){return !this.werewolf;}
        public String faction() {return this.werewolf? "Werewolf": "Villager";}
        
    //basic vote system
        public AbstractPersonnage basicVote(AbstractPersonnage[] possibleChoices){
            String[] stringChoices = new String[possibleChoices.length];
            for (int i=0; i<possibleChoices.length; i++) {
                stringChoices[i]=possibleChoices[i].toString();
            }
            int answer = Interface.askChoices(stringChoices);
            Interface.display("You chose : ", stringChoices[answer]);
            return possibleChoices[answer];
        }
        
        public AbstractPersonnage basicVote(Set<AbstractPersonnage> possibleChoices){
            return this.basicVote(possibleChoices.toArray(new AbstractPersonnage[possibleChoices.size()]));
        }
        
    //self exclusive vote system
        public AbstractPersonnage exclusiveVote(Set<AbstractPersonnage> possibleChoices){
            Set<AbstractPersonnage> realChoices = new HashSet<>(possibleChoices);
            realChoices.remove(this);
            return this.basicVote(realChoices);
        }
        
    //toString
        @Override
        public String toString(){
            return this.faction() + " (" + this.getPosessor() + ")";
        }
        
    //action support
        public void action(Set<AbstractPersonnage> personnages) {
            System.out.println(this.getClass() + " has no action");
        }
}
