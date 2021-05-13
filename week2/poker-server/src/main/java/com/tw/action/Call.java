package com.tw.action;

import com.tw.Game;
import com.tw.Player;

public class Call implements Action {
    @Override
    public void execute(Player player) {
        Game game = player.getGame();
        Integer currentBid = game.getCurrentBid();
        Integer previousBid = game.getPreviousBid(player.getId());
        game.wage(player.getId(), currentBid);
        game.putInPot(currentBid - previousBid);
    }

}
