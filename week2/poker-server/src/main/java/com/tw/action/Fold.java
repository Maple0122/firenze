package com.tw.action;

import com.tw.Game;
import com.tw.Player;
import static com.tw.Status.INACTIVE;

public class Fold implements Action {
    @Override
    public void execute(Player player) {
        Game game = player.getGame();
        player.setStatus(INACTIVE);
        game.inactive(player);
    }
}