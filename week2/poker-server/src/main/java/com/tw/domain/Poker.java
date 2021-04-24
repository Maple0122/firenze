package com.tw.domain;

import com.tw.exception.InvalidParameterException;

import java.util.HashMap;
import java.util.Map;

public class Poker {
    private final Map<Integer, Integer> pot = new HashMap<>();
    private Integer maximumBetAmount;

    public Poker(Integer playerSize) {
        initPoker(playerSize);
        this.maximumBetAmount = 0;
    }

    private void initPoker(Integer playerSize) {
        if (playerSize < 2 || playerSize > 10) {
            throw new InvalidParameterException("Number of participants is invalid, limit 2 to 10");
        }
        for (int id = 1; id <= playerSize; id++) {
            this.pot.put(id, 0);
        }
    }

    public Map<Integer, Integer> getPot() {
        return pot;
    }

    public Integer getMaximumBetAmount() {
        return maximumBetAmount;
    }
}
