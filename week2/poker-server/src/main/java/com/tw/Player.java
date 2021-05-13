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
    private Status status;

    public Player(Integer id, Game game) {
        this.id = id;
        this.game = game;
        this.status = ACTIVE;
    }

    public void execute(Action action) {
        action.execute(this);
        boolean nextRound = game.nextRound();
        if (nextRound) {
            game.resetRoundWager();
            game.setMinWager(game.getCurrentBid());
            game.resetCurrentBid();
            game.deal();
        }
        game.checkGameIsOver();
    }

}