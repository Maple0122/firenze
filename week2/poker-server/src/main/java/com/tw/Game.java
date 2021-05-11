package com.tw;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class Game {
    private final Map<Integer, Integer> roundWager = new HashMap<>();
    private Integer currentBid;
    private Integer minWager;
    private Integer pot;
    private Round round;
    private List<Integer> winnerIds = new ArrayList<>();

    public Game(Integer playerSize, Integer minWager) {
        initGame(playerSize);
        this.minWager = minWager;
        this.currentBid = minWager;
        this.pot = 0;
        this.round = Round.PREFLOP;
    }

    private void initGame(Integer playerSize) {
        if (playerSize < 2 || playerSize > 10) {
            throw new RuntimeException("Number of participants is invalid, limit 2 to 10");
        }
        for (int id = 1; id <= playerSize; id++) {
            this.roundWager.put(id, null);
        }
    }

    public void nextRound() {
        if (roundWager.values().stream().allMatch(wager -> currentBid.equals(wager))) {
            round = Round.values()[round.ordinal() + 1];
            roundWager.keySet().forEach(id -> roundWager.put(id, null));
            setMinWager(getCurrentBid());
            setCurrentBid(0);
        }
    }

    public Integer getBonus(Player player) {
        return Objects.equals(player.getPotWhenAllIn(), null) ? pot : player.getPotWhenAllIn();
    }

    public void gameIsOver() {
        if (getRoundWager().size() == 1) {
            Integer winnerId = getRoundWager().keySet().stream().findFirst().orElse(null);
            getWinnerIds().add(winnerId);
        }
    }
}
