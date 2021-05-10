package com.tw.domain;

public class Check implements Action {
    @Override
    public Integer getWager(Player player) {
        player.getPoker().setCurrentBid(0);
        player.getPoker().getRoundWager().put(player.getId(), player.getPoker().getCurrentBid());
        return 0;
    }
}
