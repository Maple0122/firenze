package com.tw.action;

import com.tw.Game;
import com.tw.Player;
import static com.tw.Status.INACTIVE;
import com.tw.action.Action;

public class Fold implements Action {
    @Override
    public Integer execute(Player player) {
        Game game = player.getGame();
        player.setStatus(INACTIVE);
        game.getRoundWager().remove(player.getId());
        game.gameIsOver();
        return 0;
    }

}