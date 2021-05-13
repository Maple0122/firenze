package com.tw;

import com.tw.action.Action;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

@Getter
@Setter
public class Game {
    private Map<Player, Integer> roundWager;
    private Queue<Player> awaitingPlayers;
    private List<Player> players;
    private Map<Player, List<Poker>> privatePokers;
    private List<Poker> publicPokers;
    private List<Integer> winnerIds;
    private Integer minWager;
    private Integer currentBid;
    private Round round;
    private Integer pot;
    private Boolean isOver;

    public Game(Player... players) {
        checkPlayerSize(players.length);
        this.awaitingPlayers = new LinkedBlockingQueue<>(asList(players));
        this.players = asList(players);
        this.roundWager = this.players.stream().collect(Collectors.toMap(Function.identity(), player -> 0));
        this.privatePokers = new HashMap<>();
        this.publicPokers = new ArrayList<>();
        this.winnerIds = new ArrayList<>();
        this.minWager = 1;
        this.currentBid = 1;
        this.round = Round.PREFLOP;
        this.pot = 0;
        this.isOver = false;
        deal();
    }

    public void execute(Action action) {
        Player activePlayer = getActivePlayer();
        action.execute(this, activePlayer);
        nextRound();
        checkGameIsOver();
        awaiting(activePlayer);
    }

    public void nextRound() {
        if (!this.isOver && roundWager.values().stream().allMatch(wager -> currentBid.equals(wager))) {
            round = Round.values()[round.ordinal() + 1];
            resetRoundWager();
            setMinWager(getCurrentBid());
            resetCurrentBid();
            deal();
        }
    }

    public void shutDown(Map<Integer, List<Poker>> selectedPokerMap) {
        if (!this.isOver) {
            return;
        }
        if (this.roundWager.size() == 1) {
            this.winnerIds.add(this.roundWager.keySet().stream().findFirst().map(Player::getId).orElse(null));
        } else {
            this.winnerIds.addAll(Rule.compare(selectedPokerMap));
        }
    }

    public void deal() {
        if (round.equals(Round.PREFLOP)) {
            this.players.forEach(player -> this.privatePokers.put(player, Stream.generate(Poker::new).limit(2).collect(Collectors.toList())));
        } else if (round.equals(Round.FLOP)) {
            this.publicPokers.addAll(Stream.generate(Poker::new).limit(3).collect(Collectors.toList()));
        } else {
            this.publicPokers.addAll(Stream.generate(Poker::new).limit(1).collect(Collectors.toList()));
        }
    }

    public void resetRoundWager() {
        roundWager.keySet().forEach(id -> roundWager.put(id, null));
    }

    public void checkGameIsOver() {
        this.isOver = roundWager.size() == 1 || Objects.equals(round, Round.RIVER);
    }

    public void wage(Player player, Integer wager) {
        this.roundWager.put(player, wager);
    }

    public Integer getPreviousBid(Player player) {
        return this.roundWager.get(player);
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
        this.roundWager.remove(player);
    }

    private Player getActivePlayer() {
        return awaitingPlayers.poll();
    }

    private void awaiting(Player activePlayer) {
        awaitingPlayers.offer(activePlayer);
    }

    private void checkPlayerSize(Integer playerSize) {
        if (playerSize < 2 || playerSize > 10) {
            throw new RuntimeException("Player size is 2 to 10");
        }
    }
}