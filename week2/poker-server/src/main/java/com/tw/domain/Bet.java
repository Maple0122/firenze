package com.tw.domain;

public class Bet implements Action {
    @Override
    public Integer getWager(Player player) {
        player.getPoker().getRoundWager().put(player.getId(), player.getPoker().getCurrentBid());
        return player.getPoker().getCurrentBid();
    }
}
