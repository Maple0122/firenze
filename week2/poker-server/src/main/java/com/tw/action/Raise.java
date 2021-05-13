package com.tw.action;

import com.tw.Game;
import com.tw.Player;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Raise implements Action {
    private final Integer wager;

    @Override
    public Integer execute(Player player) {
        Game game = player.getGame();
        game.setCurrentBid(wager);
        game.wage(player.getId(), wager);
        return wager;
    }
}
