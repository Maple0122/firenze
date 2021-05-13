package com.tw.rule;

import com.tw.Poker;
import static com.tw.Rule.NOT_MATCH_RULE;

import java.util.List;

public class ThreeOfAKind implements CompareRule {
    @Override
    public Integer getOrder(List<Poker> pokers) {
        boolean threeOfAKind = pokers.stream().map(Poker::getNumber).distinct().count() == 3;
        return threeOfAKind ? Ranking.THREE_OF_A_KIND.getOrder() : NOT_MATCH_RULE;
    }
}
