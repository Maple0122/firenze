package com.tw;

import java.util.Objects;

public enum Round {
    PREFLOP,
    FLOP,
    TURN,
    RIVER,
    ;

    public boolean isLastRound() {
        return Objects.equals(this, RIVER);
    }
}
