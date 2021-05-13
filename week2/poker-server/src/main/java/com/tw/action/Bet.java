package com.tw.action;

import com.tw.Game;
import com.tw.Player;

public class Bet implements Action {
    @Override
    public void execute(Player player) {
        Game game = player.getGame();
        if (game.getCurrentBid() < game.getMinWager()) {
            game.setCurrentBid(game.getMinWager());
        }
        Integer currentBid = game.getCurrentBid();
        game.wage(player.getId(), currentBid);
        game.putInPot(currentBid);
    }

}