package com.tw.rule;

import com.tw.Poker;

import java.util.List;

public class FourOfAKind implements CompareRule {
    @Override
    public Integer getOrder(List<Poker> pokers) {
        boolean fourOfAKind = pokers.stream().map(Poker::getNumber).distinct().count() == 2;
        return fourOfAKind ? Ranking.FOUR_OF_A_KIND.getOrder() : NOT_MATCH;
    }
}
