/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.ai.AiAdvanced;
import clue.card.Card;
import clue.client.gameInstance;
import clue.player.Player;
import java.util.List;

/**
 *Represents one player showing a card to another player.
 * @author slb35
 */
public class ShowCardAction extends Action {
    private final Card card;
    private gameInstance gui;
    private Player whoShowedTheCard;
    
    /**
     * Creates a new ShowCardAction
     * @param player the Player to show the Card to
     * @param card the Card to be shown.
     * @param gui the gameInstance to be prompted
     * @param whoShowedTheCard the player who revealed the card
     */
    public ShowCardAction(Player player,Card card, gameInstance gui, Player whoShowedTheCard) {
        super(player);
        this.actionType = ActionType.SHOWCARD;
        this.card = card;
        this.gui = gui;
        this.whoShowedTheCard = whoShowedTheCard;
    }

    /**
     * 
     * @param player the player to show the card to
     * @param card the card to be shown
     * @param shower the player to show the card to
     */
    public ShowCardAction(Player player, Card card, Player shower) {
        super(player);
        this.card = card;
        this.whoShowedTheCard = shower;
    }
    
    /**
     * @return the player who showed the card.
     */
    public Player getWhoShowedTheCard(){
        return whoShowedTheCard;
    }
    
    /**
     * @return the card to be shown
     */
    public Card getCardToShow(){
        return card;
    }
    
    
/**
 * Executes the ShowCardAction.
 */
    @Override
    public void execute() {
        if (player.isAi()){
            ((AiAdvanced) player).revealCard(card, whoShowedTheCard);
        }
        else if (gui!=null){
            gui.actionResponse(this);
        }
        else{
            System.out.println("[ShowCardAction.execute] no gui found");
        }
    }

}
