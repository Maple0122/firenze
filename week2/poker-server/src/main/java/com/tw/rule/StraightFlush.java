package com.tw.rule;

import com.tw.Poker;
import com.tw.rule.CompareRule;

import java.util.List;

public class StraightFlush implements CompareRule {
    @Override
    public Integer getOrder(List<Poker> pokers) {
        return 0;
    }
}
