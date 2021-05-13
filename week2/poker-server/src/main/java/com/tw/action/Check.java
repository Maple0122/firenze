package com.tw.action;

import com.tw.Game;
import com.tw.Player;

public class Check implements Action {
    @Override
    public void execute(Player player) {
        int wager = 0;
        Game game = player.getGame();
        game.wage(player.getId(), wager);
    }
}
