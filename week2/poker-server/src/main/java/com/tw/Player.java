package com.tw;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

import static com.tw.Status.ACTIVE;

@Getter
@Setter
public class Player {
    private final Integer id;
    private final Game game;
    private final Integer initWager;
    private Integer remainWager;
    private Status status;
    private Integer potWhenAllIn;

    public Player(Integer id, Integer initWager, Game game) {
        this.id = id;
        this.initWager = initWager;
        this.remainWager = initWager;
        this.game = game;
        this.status = ACTIVE;
    }

    public void execute(Action action) {
        Integer bid = action.getBid(this);
        remainWager -= bid;
        game.setPot(game.getPot() + bid);
        game.nextRound();
    }
}