package com.tw.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Raise implements Action {
    private final Integer wager;

    @Override
    public Integer getWager(Player player) {
        player.getPoker().setCurrentBid(wager);
        player.getPoker().getRoundWager().put(player.getId(), player.getPoker().getCurrentBid());
        return wager;
    }
}
