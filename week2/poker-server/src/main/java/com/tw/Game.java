package com.tw;

import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

@Getter
@Setter
public class Game {
    private Map<Integer, Integer> roundWager;
    private Map<Integer, List<Poker>> privatePokers;
    private List<Poker> publicPokers;
    private List<Integer> winnerIds;
    private Integer minWager;
    private Integer currentBid;
    private Round round;
    private Integer pot;
    private Boolean isOver;

    public Game(Integer playerSize, Integer minWager) {
        checkPlayerSize(playerSize);
        this.roundWager = new HashMap<>();
        this.privatePokers = new HashMap<>();
        this.publicPokers = new ArrayList<>();
        this.winnerIds = new ArrayList<>();
        this.minWager = minWager;
        this.currentBid = minWager;
        this.round = Round.PREFLOP;
        this.pot = 0;
        this.isOver = false;
        initGameAndDealBlindPoker(playerSize);
    }

    private void initGameAndDealBlindPoker(Integer playerSize) {
        Stream.iterate(1, id -> id + 1).limit(playerSize).forEach(id -> {
            roundWager.put(id, null);
            privatePokers.put(id, asList(new Poker(), new Poker()));
        });
    }

    private void checkPlayerSize(Integer playerSize) {
        if (playerSize < 2 || playerSize > 10) {
            throw new RuntimeException("Player size is 2 to 10");
        }
    }

    public boolean nextRound() {
        if (!this.isOver && roundWager.values().stream().allMatch(wager -> currentBid.equals(wager))) {
            round = Round.values()[round.ordinal() + 1];
            return true;
        }
        return false;
    }

    public void shutDown(Map<Integer, List<Poker>> selectedPokerMap) {
        if (!isOver) {
            return;
        }
        if (this.roundWager.size() == 1) {
            this.winnerIds.add(this.roundWager.keySet().stream().findFirst().orElse(null));
        } else {
            this.winnerIds.addAll(Rule.compare(selectedPokerMap));
        }
    }

    public void deal() {
        int limit = round.equals(Round.FLOP) ? 3 : 1;
        publicPokers.addAll(Stream.generate(Poker::new).limit(limit).collect(Collectors.toList()));
    }

    public void resetRoundWager() {
        roundWager.keySet().forEach(id -> roundWager.put(id, null));
    }

    public void checkGameIsOver() {
        this.isOver = roundWager.size() == 1 || Objects.equals(round, Round.RIVER);
    }

    public void wage(Integer playerId, Integer wager) {
        getRoundWager().put(playerId, wager);
    }

    public Integer getPreviousBid(Integer playerId) {
        return getRoundWager().get(playerId);
    }

    public Integer checkout() {
        return this.pot / this.winnerIds.size();
    }

    public void putInPot(Integer bid) {
        this.pot += bid;
    }

    public void resetCurrentBid() {
        this.currentBid = 0;
    }

    public void inactive(Player player) {
        getRoundWager().remove(player.getId());
    }
}