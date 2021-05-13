package com.tw.action;

import com.tw.Game;
import com.tw.Player;

public class Call implements Action {
    @Override
    public void execute(Game game, Player player) {
        Integer currentBid = game.getCurrentBid();
        Integer previousBid = game.getPreviousBid(player);
        game.wage(player, currentBid);
        game.putInPot(currentBid - previousBid);
    }

}
