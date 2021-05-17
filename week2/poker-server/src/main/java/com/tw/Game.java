package com.tw;

import com.tw.action.Action;
import static java.util.Arrays.asList;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
public class Game {
    private Map<Player, Integer> roundWager;
    private Queue<Player> awaitingPlayers;
    private List<Player> players;
    private Map<Player, List<Poker>> pokers;
    private List<Integer> winnerIds;
    private Integer minWager;
    private Integer currentBid;
    private Round round;
    private Integer pot;
    private Boolean over;

    public Game(Player... players) {
        this.awaitingPlayers = new LinkedBlockingQueue<>(asList(players));
        this.players = asList(players);
        this.roundWager = this.players.stream().collect(Collectors.toMap(Function.identity(), player -> 0));
        this.pokers = new HashMap<>();
        this.winnerIds = new ArrayList<>();
        this.minWager = 1;
        this.currentBid = 1;
        this.round = Round.PREFLOP;
        this.pot = 0;
        this.over = false;
        deal(2);
    }

    public void execute(Action action) {
        Player activePlayer = awaitingPlayers.poll();
        action.execute(this, activePlayer);
        nextRound();
    }

    public void nextRound() {
        this.over = this.roundWager.size() == 1 || Round.RIVER.equals(this.round);
        if (!this.over && this.roundWager.values().stream().allMatch(wager -> this.currentBid.equals(wager))) {
            round = Round.values()[round.ordinal() + 1];
            roundWager.keySet().forEach(id -> roundWager.put(id, null));
            this.minWager = this.currentBid;
            this.currentBid = 0;
            deal(Round.FLOP.equals(this.round) ? 3 : 1);
        }
    }

    public Integer checkout(Map<Integer, List<Poker>> selectedPokerMap) {
        if (!this.over) {
            return null;
        }
        if (this.roundWager.size() == 1) {
            this.winnerIds.add(this.roundWager.keySet().stream().findFirst().map(Player::getId).orElse(null));
        }
        if (this.roundWager.size() > 1) {
            this.winnerIds.addAll(Rule.compare(selectedPokerMap));
        }
        return this.pot / this.winnerIds.size();
    }

    public void deal(int limit) {
        this.players.forEach(player -> this.pokers.put(player, Stream.generate(Poker::new).limit(limit).collect(Collectors.toList())));
    }

    public void wage(Player player, Integer wager) {
        this.roundWager.put(player, wager);
    }

    public Integer getPreviousBid(Player player) {
        return this.roundWager.get(player);
    }

    public void putInPot(Integer bid) {
        this.pot += bid;
    }

    public void inactive(Player player) {
        this.roundWager.remove(player);
    }

    public void awaiting(Player activePlayer) {
        awaitingPlayers.offer(activePlayer);
    }
}