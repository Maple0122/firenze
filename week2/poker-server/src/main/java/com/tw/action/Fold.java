package com.tw.action;

import com.tw.Game;
import com.tw.Player;
import static com.tw.Status.INACTIVE;

public class Fold implements Action {
    @Override
    public Integer execute(Player player) {
        Game game = player.getGame();
        player.setStatus(INACTIVE);
        game.getRoundWager().remove(player.getId());
        return 0;
    }

}