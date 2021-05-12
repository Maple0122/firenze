package com.tw;

import lombok.Getter;

import java.util.Random;

@Getter
public class Poker {
    private static final String[] TYPES = new String[]{"梅花", "方块", "红桃", "黑桃"};
    private static final String[] NUMBERS = new String[]{"A", "K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2"};
    private final String type;
    private final String number;

    public Poker() {
        this.type = generateType();
        this.number = generateNumber();
    }

    public Poker(String type, String number) {
        this.type = type;
        this.number = number;
    }

    private String generateNumber() {
        int index = new Random().nextInt(13);
        return NUMBERS[index];
    }

    private String generateType() {
        int index = new Random().nextInt(4);
        return TYPES[index];
    }

    @Override
    public String toString() {
        return type + number;
    }
}