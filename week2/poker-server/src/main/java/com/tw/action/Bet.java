package com.tw.action;

import com.tw.Game;
import com.tw.Player;

public class Bet implements Action {
    @Override
    public Integer execute(Player player) {
        Game game = player.getGame();
        if (game.getCurrentBid() < game.getMinWager()) {
            game.setCurrentBid(game.getMinWager());
        }
        game.getRoundWager().put(player.getId(), game.getCurrentBid());
        return game.getCurrentBid();
    }
}