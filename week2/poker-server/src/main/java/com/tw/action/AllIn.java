package com.tw.action;

import com.tw.Game;
import com.tw.Player;

public class AllIn implements Action {
    @Override
    public Integer execute(Player player) {
        Game game = player.getGame();
        player.setPotWhenAllIn(game.getPot());
        Integer allInWager = player.getRemainWager();
        if (allInWager > game.getCurrentBid()) {
            game.setCurrentBid(allInWager);
        }
        game.getRoundWager().remove(player.getId());
        return allInWager;
    }
}