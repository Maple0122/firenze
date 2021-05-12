package com.tw;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Type {
    CLUB("梅花"),
    DIAMOND("方块"),
    HEART("红桃"),
    SPADE("黑桃"),
    ;

    private final String type;
}
