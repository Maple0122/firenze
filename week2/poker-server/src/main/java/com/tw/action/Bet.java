package com.tw.action;

import com.tw.Game;
import com.tw.Player;

public class Bet implements Action {
    @Override
    public Integer execute(Player player) {
        Game game = player.getGame();
        Integer currentBid = game.getCurrentBid();
        game.wage(player.getId(), currentBid);
        return currentBid;
    }

}