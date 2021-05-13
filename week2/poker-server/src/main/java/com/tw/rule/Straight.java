package com.tw.rule;

import com.tw.Poker;
import static com.tw.Rule.NOT_MATCH_RULE;
import static com.tw.rule.Ranking.STRAIGHT;

import java.util.List;

public class Straight implements CompareRule {
    @Override
    public Integer getOrder(List<Poker> pokers) {
        return isStraight(pokers) ? STRAIGHT.getOrder() : NOT_MATCH_RULE;
    }
}
