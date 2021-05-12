package com.tw;

import lombok.Getter;

import java.util.Random;

@Getter
public class Poker {
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
        return Number.values()[index].getNumber();
    }

    private String generateType() {
        int index = new Random().nextInt(4);
        return Type.values()[index].getType();
    }

    @Override
    public String toString() {
        return type + number;
    }
}