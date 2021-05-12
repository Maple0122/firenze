package com.tw.action;

import com.tw.Game;
import com.tw.Player;

public class Check implements Action {
    @Override
    public Integer execute(Player player) {
        int wager = 0;
        Game game = player.getGame();
        game.getRoundWager().put(player.getId(), wager);
        return wager;
    }
}
