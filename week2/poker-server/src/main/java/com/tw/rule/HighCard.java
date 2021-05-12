package com.tw.rule;

import com.tw.Poker;

import java.util.List;

public class HighCard implements CompareRule {
    @Override
    public Integer getOrder(List<Poker> pokers) {
        return 0;
    }
}
