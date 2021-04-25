package com.tw.domain;

import static com.tw.domain.PlayerStatus.OFFLINE;
import static com.tw.domain.PlayerStatus.ONLINE;
import lombok.Getter;

@Getter
public class Player {
    private final Integer id;
    private final Poker poker;
    private final Integer initCoin;
    private Integer remainCoin;
    private PlayerStatus status;
    private Integer coinOfPotWhenAllIn;

    public Player(Integer id, Integer coin, Poker poker) {
        this.id = id;
        this.initCoin = coin;
        this.remainCoin = coin;
        this.poker = poker;
        this.status = ONLINE;
    }

    public void call() {
        Integer betCoin = poker.getPot().get(this.id);
        int callCoin = poker.getMaximumBetCoin() - betCoin;
        this.remainCoin -= callCoin;
        poker.addPotCoin(callCoin);
        poker.getPot().put(this.id, poker.getMaximumBetCoin());
    }

    public void bet(Integer coin) {
        this.remainCoin -= coin;
        Integer betCoin = poker.getPot().get(this.id);
        poker.setMaximumBetCoin(betCoin + coin);
        poker.addPotCoin(coin);
        poker.getPot().put(this.id, poker.getMaximumBetCoin());
    }

    public void raise(Integer addCoin) {
        Integer betCoin = poker.getPot().get(this.id);
        poker.setMaximumBetCoin(poker.getMaximumBetCoin() + addCoin);
        this.remainCoin -= (poker.getMaximumBetCoin() - betCoin);
        poker.addPotCoin(poker.getMaximumBetCoin() - betCoin);
        poker.getPot().put(this.id, poker.getMaximumBetCoin());
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
        this.bet(this.remainCoin);
        this.coinOfPotWhenAllIn = this.poker.getPotCoin();
    }

    public Integer calculateWinCoin() {
        return this.poker.getPotCoin() - this.poker.getPot().get(this.id);
    }

    public Integer calculateWinCoinOfAllInPlayer() {
        return this.coinOfPotWhenAllIn - this.initCoin;
    }
}
