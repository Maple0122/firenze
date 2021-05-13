package com.tw;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Player {
    private final Integer id;

    public Player(Integer id) {
        this.id = id;
    }

}