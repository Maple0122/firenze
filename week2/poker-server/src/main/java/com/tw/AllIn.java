package com.tw;

public class AllIn implements Action {
    @Override
    public Integer getBid(Player player) {
        Game game = player.getGame();
        player.setPotWhenAllIn(game.getPot());
        game.getRoundWager().put(player.getId(), game.getCurrentBid());
        game.setCurrentBid(player.getRemainWager());
        return player.getRemainWager();
    }
}
