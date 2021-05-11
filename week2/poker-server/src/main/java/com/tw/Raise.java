package com.tw;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Raise implements Action {
    private final Integer wager;

    @Override
    public Integer getBid(Player player) {
        Game game = player.getGame();
        game.setCurrentBid(wager);
        game.getRoundWager().put(player.getId(), game.getCurrentBid());
        return wager;
    }
}
