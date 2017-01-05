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
public class Thief extends Villager {
    
    @Override
    public String toString(){
        return ("thief");
    }
}

/**
 * todo
 *      add 2 undistributed cards
 *      swow the two cards to the thief at the first turn
 *          if the two cards are werewolves, thief must change to werewolf
 *          else, the thief can chose to change to one of the cards, or stay a thief
 *      on every other turn
 *          if he uses his power,
 *      if the thief is in the undistributed cards, print the messages as if he was alive
 */