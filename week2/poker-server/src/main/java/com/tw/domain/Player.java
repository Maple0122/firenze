package com.tw.domain;

import static com.tw.domain.PlayerStatus.OFFLINE;
import static com.tw.domain.PlayerStatus.ONLINE;
import lombok.Getter;

import static java.lang.Math.subtractExact;

@Getter
public class Player {
    private final Integer id;
    private Poker poker;
    private Integer amount;
    private PlayerStatus status;

    public Player(Integer id, Integer amount, Poker poker) {
        this.id = id;
        this.amount = amount;
        this.poker = poker;
        this.status = ONLINE;
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

    public void fold() {
        this.poker = null;
        this.status = OFFLINE;
    }

    public void check() {
        this.bet(0);
    }

    public void allIn() {
        this.bet(this.amount);
    }
}
