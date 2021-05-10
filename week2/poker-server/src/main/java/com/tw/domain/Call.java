package com.tw.domain;

public class Call implements Action {
    @Override
    public Integer getWager(Player player) {
        Integer currentBid = player.getPoker().getCurrentBid();
        Integer bid = player.getPoker().getRoundWager().get(player.getId());
        player.getPoker().getRoundWager().put(player.getId(), player.getPoker().getCurrentBid());
        return currentBid - bid;
    }
}
