package com.tw.domain;

import static com.tw.domain.PlayerStatus.OFFLINE;

public class Fold implements Action {
    @Override
    public Integer getWager(Player player) {
        player.setStatus(OFFLINE);
        player.getPoker().getRoundWager().remove(player.getId());
        if (player.getPoker().getRoundWager().size() == 1) {
            player.getPoker().getWinnerIds().add(player.getPoker().getRoundWager().keySet().stream().findFirst().orElse(null));
        }
        return 0;
    }
}
