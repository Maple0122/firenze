package com.tw.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

import static com.tw.domain.PlayerStatus.ONLINE;

@Getter
@Setter
public class Player {
    private final Integer id;
    private final Poker poker;
    private final Integer initWager;
    private Integer remainWager;
    private PlayerStatus status;
    private Integer potWhenAllIn;

    public Player(Integer id, Integer initWager, Poker poker) {
        this.id = id;
        this.initWager = initWager;
        this.remainWager = initWager;
        this.poker = poker;
        this.status = ONLINE;
    }

    public void execute(Action action) {
        Integer wager = action.getWager(this);
        remainWager -= wager;
        poker.setPot(poker.getPot() + wager);
        poker.nextRound();
    }

    public Integer getBonus() {
        return Objects.equals(potWhenAllIn, null) ? poker.getPot() : potWhenAllIn;
    }
}
