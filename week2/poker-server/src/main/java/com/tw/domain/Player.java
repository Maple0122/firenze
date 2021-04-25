package com.tw.domain;

import lombok.Getter;

import static java.lang.Math.subtractExact;

@Getter
public class Player {
    private final Integer id;
    private final Poker poker;
    private Integer amount;

    public Player(Integer id, Integer amount, Poker poker) {
        this.id = id;
        this.amount = amount;
        this.poker = poker;
    }

    public void call() {
        Integer betAmount = poker.getPot().get(this.id);
        int callAmount = subtractExact(poker.getMaximumBetAmount(), betAmount);
        this.amount = subtractExact(this.amount, callAmount);
        poker.getPot().put(this.id, poker.getMaximumBetAmount());
    }

    public void bet(Integer coin) {
        this.amount -= coin;
        Integer betAmount = poker.getPot().get(this.id);
        poker.setMaximumBetAmount(betAmount + coin);
        poker.getPot().put(this.id, poker.getMaximumBetAmount());
    }

    public void raise(Integer addCoin) {
        Integer betAmount = poker.getPot().get(this.id);
        poker.setMaximumBetAmount(poker.getMaximumBetAmount() + addCoin);
        this.amount -= (poker.getMaximumBetAmount() - betAmount);
        poker.getPot().put(this.id, poker.getMaximumBetAmount());
    }
}
