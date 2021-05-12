package com.tw;

import static java.util.Arrays.asList;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Game {
    private final Map<Integer, Integer> roundWager = new HashMap<>();
    private Map<Integer, List<Poker>> privatePokers = new HashMap<>();
    private List<Poker> publicPokers = new ArrayList<>();
    private List<Integer> winnerIds = new ArrayList<>();
    private Integer minWager;
    private Integer currentBid;
    private Round round;
    private Integer pot;

    public Game(Integer playerSize, Integer minWager) {
        startGame(playerSize);
        this.minWager = minWager;
        this.currentBid = minWager;
        this.round = Round.PREFLOP;
        this.pot = 0;
    }

    private void startGame(Integer playerSize) {
        if (playerSize < 2 || playerSize > 10) {
            throw new RuntimeException("Player size is 2 to 10");
        }
        for (int id = 1; id <= playerSize; id++) {
            roundWager.put(id, null);
            privatePokers.put(id, asList(new Poker(), new Poker()));
        }
    }

    public void nextRound() {
        if (!round.equals(Round.RIVER) && wagerAllMatchCurrentBid()) {
            round = Round.values()[round.ordinal() + 1];
            roundWager.keySet().forEach(id -> roundWager.put(id, null));
            setMinWager(getCurrentBid());
            setCurrentBid(0);
        }
    }

    public void shutDown(Map<Integer, List<Poker>> selectedPoker) {
        if (round.equals(Round.RIVER) && wagerAllMatchCurrentBid()) {
            List<Integer> winnerIds = new Rule().compare(selectedPoker);
            this.winnerIds.addAll(winnerIds);
        }
    }

    public void deal() {
        if (!round.equals(Round.RIVER) && wagerAllMatchCurrentBid()) {
            if (round.equals(Round.FLOP)) {
                publicPokers.addAll(asList(new Poker(), new Poker(), new Poker()));
            } else {
                publicPokers.add(new Poker());
            }
        }
    }

    public Integer checkout(Player player) {
        return player.getPotWhenAllIn() == null ? pot / winnerIds.size() : player.getPotWhenAllIn();
    }

    public void gameIsOver() {
        if (getRoundWager().size() == 1) {
            Integer winnerId = getRoundWager().keySet().stream().findFirst().orElse(null);
            getWinnerIds().add(winnerId);
        }
    }

    public void putInPot(Integer bid) {
        pot += bid;
    }

    private boolean wagerAllMatchCurrentBid() {
        return roundWager.values().stream().allMatch(wager -> currentBid.equals(wager));
    }
}
