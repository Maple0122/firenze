package com.tw.rule;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Ranking {
    ROYAL_FLUSH(1),
    STRAIGHT_FLUSH(2),
    FOUR_OF_A_KIND(3),
    FULL_HOUSE(4),
    FLUSH(5),
    STRAIGHT(6),
    THREE_OF_A_KIND(7),
    TWO_PAIRS(8),
    PAIR(9),
    HIGH_CARD(10),
    ;

    private final Integer order;
}
