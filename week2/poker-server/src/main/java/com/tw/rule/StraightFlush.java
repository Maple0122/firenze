package com.tw.rule;

import com.tw.Poker;
import static com.tw.Rule.NOT_MATCH_RULE;
import static com.tw.rule.Ranking.STRAIGHT_FLUSH;

import java.util.List;

public class StraightFlush implements CompareRule {
    @Override
    public Integer getOrder(List<Poker> pokers) {
        return isFlush(pokers) && isStraight(pokers) ? STRAIGHT_FLUSH.getOrder() : NOT_MATCH_RULE;
    }
}
