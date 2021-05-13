package com.tw;

import static java.util.Arrays.asList;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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
    private Rule rule = new Rule();
    private boolean isOver;

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
        Stream.iterate(1, id -> id + 1).limit(playerSize).forEach(id -> {
            roundWager.put(id, null);
            privatePokers.put(id, asList(new Poker(), new Poker()));
        });
    }

    public void nextRound() {
        if (!round.isLastRound() && nextTurn()) {
            round = Round.values()[round.ordinal() + 1];
            reset();
            deal();
        }
    }

    public void shutDown(Map<Integer, List<Poker>> selectedPokerMap) {
        if (isOver && roundWager.size() > 1) {
            this.winnerIds.addAll(rule.compare(selectedPokerMap));
        }
    }

    public Integer checkout() {
        return this.pot / this.winnerIds.size();
    }

    public void putInPot(Integer bid) {
        this.pot += bid;
    }

    public void isOver() {
        if (getRoundWager().size() == 1) {
            Integer winnerId = getRoundWager().keySet().stream().findFirst().orElse(null);
            getWinnerIds().add(winnerId);
            this.isOver = true;
        }
        if (round.isLastRound() && nextTurn()) {
            this.isOver = true;
        }
    }

    private void deal() {
        publicPokers.addAll(round.equals(Round.FLOP) ? asList(new Poker(), new Poker(), new Poker()) :
                Collections.singletonList(new Poker()));
    }

    private void reset() {
        roundWager.keySet().forEach(id -> roundWager.put(id, null));
        setMinWager(getCurrentBid());
        setCurrentBid(0);
    }

    private boolean nextTurn() {
        return roundWager.values().stream().allMatch(wager -> currentBid.equals(wager));
    }

    public void wage(Integer playerId, Integer wager) {
        getRoundWager().put(playerId, wager);
    }

    public Integer getCurrentBid() {
        if (this.currentBid < this.minWager) {
            this.currentBid = this.minWager;
        }
        return this.currentBid;
    }

    public Integer getPreviousBid(Integer playerId) {
        return getRoundWager().get(playerId);
    }
}