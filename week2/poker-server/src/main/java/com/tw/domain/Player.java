package com.tw.domain;

import static com.tw.domain.PlayerStatus.OFFLINE;
import static com.tw.domain.PlayerStatus.ONLINE;
import lombok.Getter;

import java.util.Objects;

@Getter
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

    public void call() {
        Integer wager = poker.getCurrentBid() - poker.getRoundWager().get(id);


        poker.setPot(poker.getPot() + wager);
        remainWager -= wager;
        poker.getRoundWager().put(id, poker.getCurrentBid());
        poker.nextRound();
    }

    public void bet() {
        Integer wager = poker.getCurrentBid();


        poker.setPot(poker.getPot() + wager);
        remainWager -= wager;
        poker.getRoundWager().put(id, poker.getCurrentBid());
        poker.nextRound();
    }

    public void raise(Integer wager) {
        poker.setCurrentBid(wager);


        poker.setPot(poker.getPot() + wager);
        remainWager -= wager;
        poker.getRoundWager().put(id, poker.getCurrentBid());
        poker.nextRound();
    }

    public void check() {
        poker.setCurrentBid(0);


        poker.setPot(poker.getPot() + (Integer) 0);
        remainWager -= 0;
        poker.getRoundWager().put(id, poker.getCurrentBid());
        poker.nextRound();
    }

    public void allIn() {
        potWhenAllIn = poker.getPot();
        poker.setCurrentBid(remainWager);


        poker.setPot(poker.getPot() + remainWager);
        remainWager -= remainWager;
        poker.getRoundWager().put(id, poker.getCurrentBid());
        poker.nextRound();
    }

    public void fold() {
        status = OFFLINE;
        poker.nextRound();

        poker.getRoundWager().remove(id);
        if (poker.getRoundWager().size() == 1) {
            poker.getWinnerIds().add(poker.getRoundWager().keySet().stream().findFirst().orElse(null));
        }
    }

    public Integer getBonus() {
        if (!Objects.equals(potWhenAllIn, null)) {
            return potWhenAllIn;
        }
        return poker.getPot();
    }
}
