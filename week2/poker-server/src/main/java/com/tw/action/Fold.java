package com.tw.action;

import com.tw.Game;
import com.tw.Player;

public class Fold implements Action {
    @Override
    public void execute(Game game, Player player) {
        game.inactive(player);
    }
}