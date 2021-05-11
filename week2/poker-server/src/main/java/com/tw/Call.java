package com.tw;

public class Call implements Action {
    @Override
    public Integer execute(Player player) {
        Game game = player.getGame();
        Integer currentBid = game.getCurrentBid();
        Integer bid = game.getRoundWager().get(player.getId());
        game.getRoundWager().put(player.getId(), game.getCurrentBid());
        return currentBid - bid;
    }
}
