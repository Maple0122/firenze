package com.tw.action;

import com.tw.Game;
import com.tw.Player;

public class Check implements Action {
    @Override
    public void execute(Game game, Player player) {
        int wager = 0;
        game.wage(player, wager);
        game.awaiting(player);
    }
}
