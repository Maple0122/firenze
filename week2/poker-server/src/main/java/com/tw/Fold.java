package com.tw;

import static com.tw.Status.INACTIVE;

public class Fold implements Action {
    @Override
    public Integer getBid(Player player) {
        Game game = player.getGame();
        player.setStatus(INACTIVE);
        game.getRoundWager().remove(player.getId());
        if (game.getRoundWager().size() == 1) {
            game.getWinnerIds().add(game.getRoundWager().keySet().stream().findFirst().orElse(null));
        }
        return 0;
    }
}