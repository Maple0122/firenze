package com.tw.domain;

import com.tw.exception.InvalidParameterException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Poker {
    private final Map<Integer, Integer> roundWager = new HashMap<>();
    private Integer currentBid;
    private Integer minWager;
    private Integer pot;
    private Round round;
    private List<Integer> winnerIds = new ArrayList<>();

    public Poker(Integer playerSize, Integer minWager) {
        initPoker(playerSize);
        this.minWager = minWager;
        this.currentBid = this.minWager;
        this.pot = 0;
        this.round = Round.PREFLOP;
    }

    private void initPoker(Integer playerSize) {
        if (playerSize < 2 || playerSize > 10) {
            throw new InvalidParameterException("Number of participants is invalid, limit 2 to 10");
        }
        for (int id = 1; id <= playerSize; id++) {
            this.roundWager.put(id, 0);
        }
    }

    public void nextRound() {
        if (roundWager.values().stream().allMatch(v -> v.equals(currentBid))) {
            round = Round.values()[round.ordinal() + 1];
        }
    }
}
