package com.tw;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Number {
    ACE("A"),
    KING("K"),
    QUEEN("Q"),
    JACK("J"),
    TEN("10"),
    NINE("9"),
    EIGHT("8"),
    SEVEN("7"),
    SIX("6"),
    FIVE("5"),
    FOUR("4"),
    THREE("3"),
    TWO("2"),
    ;

    private final String number;

    public static Integer getOrdinal(String number) {
        return Arrays.stream(values())
                .filter(value -> value.getNumber().equals(number))
                .findFirst()
                .map(Enum::ordinal)
                .orElse(null);
    }
}
