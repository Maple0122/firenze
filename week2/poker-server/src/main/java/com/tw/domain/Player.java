package com.tw.domain;

import static com.tw.domain.PlayerStatus.PASS;
import static java.lang.Math.subtractExact;

public class Player {
    private final Integer id;
    private final Poker poker;
    private Integer amount;
    private PlayerStatus status;

    public Player(Integer id, Integer amount, Poker poker) {
        this.id = id;
        this.amount = amount;
        this.poker = poker;
    }

    public void call() {
        Integer betAmount = poker.getPot().get(this.id);
        int callAmount = subtractExact(poker.getMaximumBetAmount(), betAmount);
        this.amount = subtractExact(this.amount, callAmount);
        this.status = PASS;
        poker.getPot().put(this.id, poker.getMaximumBetAmount());
    }

    public PlayerStatus getStatus() {
        return status;
    }
}
