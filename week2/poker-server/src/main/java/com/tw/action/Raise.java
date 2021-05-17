package com.tw.action;

import com.tw.Game;
import com.tw.Player;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Raise implements Action {
    private final Integer wager;

    @Override
    public void execute(Game game, Player player) {
        game.setCurrentBid(wager);
        game.wage(player, wager);
        game.putInPot(wager);
        game.awaiting(player);
    }
}
