package com.tw;

import static com.tw.Status.INACTIVE;

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