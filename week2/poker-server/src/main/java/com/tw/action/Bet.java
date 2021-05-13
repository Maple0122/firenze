package com.tw.action;

import com.tw.Game;
import com.tw.Player;

public class Bet implements Action {
    @Override
    public void execute(Game game, Player player) {
        if (game.getCurrentBid() < game.getMinWager()) {
            game.setCurrentBid(game.getMinWager());
        }
        Integer currentBid = game.getCurrentBid();
        game.wage(player, currentBid);
        game.putInPot(currentBid);
    }

}