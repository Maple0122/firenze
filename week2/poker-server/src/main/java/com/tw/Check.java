package com.tw;

public class Check implements Action {
    @Override
    public Integer getBid(Player player) {
        Game game = player.getGame();
        game.setCurrentBid(0);
        game.getRoundWager().put(player.getId(), 0);
        return 0;
    }
}
