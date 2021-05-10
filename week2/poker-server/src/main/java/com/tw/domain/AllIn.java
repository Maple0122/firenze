package com.tw.domain;

public class AllIn implements Action {
    @Override
    public Integer getWager(Player player) {
        player.setPotWhenAllIn(player.getPoker().getPot());
        player.getPoker().getRoundWager().put(player.getId(), player.getPoker().getCurrentBid());
        player.getPoker().setCurrentBid(player.getRemainWager());
        return player.getRemainWager();
    }
}
