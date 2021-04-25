package com.tw.domain;

import static com.tw.domain.PlayerStatus.OFFLINE;
import static com.tw.domain.PlayerStatus.ONLINE;
import lombok.Getter;

@Getter
public class Player {
    private final Integer id;
    private final Poker poker;
    private final Integer initAmount;
    private Integer remainAmount;
    private PlayerStatus status;
    private Integer amountOfPotBeforeAllIn;

    public Player(Integer id, Integer amount, Poker poker) {
        this.id = id;
        this.initAmount = amount;
        this.remainAmount = amount;
        this.poker = poker;
        this.status = ONLINE;
    }

    public void call() {
        Integer betAmount = poker.getPot().get(this.id);
        int callAmount = poker.getMaximumBetAmount() - betAmount;
        this.remainAmount -= callAmount;
        poker.addAmountOfPot(callAmount);
        poker.getPot().put(this.id, poker.getMaximumBetAmount());
    }

    public void bet(Integer coin) {
        this.remainAmount -= coin;
        Integer betAmount = poker.getPot().get(this.id);
        poker.setMaximumBetAmount(betAmount + coin);
        poker.addAmountOfPot(coin);
        poker.getPot().put(this.id, poker.getMaximumBetAmount());
    }

    public void raise(Integer addCoin) {
        Integer betAmount = poker.getPot().get(this.id);
        poker.setMaximumBetAmount(poker.getMaximumBetAmount() + addCoin);
        this.remainAmount -= (poker.getMaximumBetAmount() - betAmount);
        poker.addAmountOfPot(poker.getMaximumBetAmount() - betAmount);
        poker.getPot().put(this.id, poker.getMaximumBetAmount());
    }

    public void fold() {
        this.poker.getPot().remove(this.id);
        this.status = OFFLINE;
        if (this.poker.getPot().size() == 1) {
            this.poker.getWinnerIds().add(this.poker.getPot().keySet().stream().findFirst().orElse(null));
        }
    }

    public void check() {
        this.bet(0);
    }

    public void allIn() {
        this.bet(this.remainAmount);
        this.amountOfPotBeforeAllIn = this.poker.getAmountOfPot();
    }

    public Integer calculateWinAmount() {
        return this.poker.getAmountOfPot() - this.poker.getPot().get(this.id);
    }

    public Integer calculateWinAmountForAllInPlayer() {
        return this.amountOfPotBeforeAllIn - this.initAmount;
    }
}
