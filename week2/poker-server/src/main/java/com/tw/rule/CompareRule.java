package com.tw.rule;

import com.tw.Poker;

import java.util.List;

public interface CompareRule {
    Integer NOT_MATCH = 0;

    Integer getOrder(List<Poker> pokers);
}
