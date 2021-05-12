package com.tw.rule;

import com.tw.Poker;

import java.util.List;

public class Flush implements CompareRule {
    @Override
    public Integer getOrder(List<Poker> pokers) {
        String type = pokers.get(0).getType();
        boolean flush = pokers.stream().allMatch(poker -> poker.getType().equals(type));
        return flush ? Ranking.FLUSH.getOrder() : NOT_MATCH;
    }
}
