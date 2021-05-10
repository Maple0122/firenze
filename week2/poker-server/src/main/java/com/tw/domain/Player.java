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
        Integer bid = poker.getRoundWager().get(id);
        int wager = poker.getCurrentBid() - bid;
        remainWager -= wager;
        poker.setPot(poker.getPot() + wager);
        poker.getRoundWager().put(id, poker.getCurrentBid());
        poker.nextRound();
    }

    public void bet() {
        remainWager -= poker.getCurrentBid();
        poker.setPot(poker.getPot() + poker.getCurrentBid());
        poker.getRoundWager().put(id, poker.getCurrentBid());
        poker.nextRound();
    }

    public void raise(Integer wager) {
        poker.setCurrentBid(wager);
        remainWager -= wager;
        poker.setPot(poker.getPot() + wager);
        poker.getRoundWager().put(id, poker.getCurrentBid());
        poker.nextRound();
    }

    public void fold() {
        poker.getRoundWager().remove(id);
        status = OFFLINE;
        if (poker.getRoundWager().size() == 1) {
            poker.getWinnerIds().add(poker.getRoundWager().keySet().stream().findFirst().orElse(null));
        }
        poker.nextRound();
    }

    public void check() {
        poker.setCurrentBid(0);
        poker.getRoundWager().put(id, poker.getCurrentBid());
        poker.nextRound();
    }

    public void allIn() {
        poker.setCurrentBid(remainWager);
        poker.setPot(poker.getPot() + remainWager);
        remainWager = 0;
        potWhenAllIn = poker.getPot();
        poker.getRoundWager().put(id, poker.getCurrentBid());
        poker.nextRound();
    }

    public Integer getBonus() {
        if (!Objects.equals(potWhenAllIn, null)) {
            return potWhenAllIn;
        }
        return poker.getPot();
    }
}
