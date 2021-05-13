package com.tw;

import static com.tw.Status.ACTIVE;
import com.tw.action.Action;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player {
    private final Integer id;
    private final Game game;
    private final Integer initWager;
    private Integer remainWager;
    private Status status;

    public Player(Integer id, Integer initWager, Game game) {
        this.id = id;
        this.initWager = initWager;
        this.remainWager = initWager;
        this.game = game;
        this.status = ACTIVE;
    }

    public void execute(Action action) {
        Integer bid = action.execute(this);
        wage(bid);
        game.putInPot(bid);
        game.nextRound();
        game.isOver();
    }

    public void wage(Integer bid) {
        remainWager -= bid;
    }
}