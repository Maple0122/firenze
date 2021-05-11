package com.tw;

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