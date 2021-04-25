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
    private final Map<Integer, Integer> pot = new HashMap<>();
    private Integer maximumBetAmount;
    private Integer amountOfPot;
    private List<Integer> winnerIds = new ArrayList<>();

    public Poker(Integer playerSize) {
        initPoker(playerSize);
        this.maximumBetAmount = 0;
        this.amountOfPot = 0;
    }

    private void initPoker(Integer playerSize) {
        if (playerSize < 2 || playerSize > 10) {
            throw new InvalidParameterException("Number of participants is invalid, limit 2 to 10");
        }
        for (int id = 1; id <= playerSize; id++) {
            this.pot.put(id, 0);
        }
    }

    public void addAmountOfPot(Integer coin) {
        this.amountOfPot += coin;
    }
}
