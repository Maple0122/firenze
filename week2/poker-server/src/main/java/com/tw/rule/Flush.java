package com.tw.rule;

import com.tw.Poker;
import static com.tw.Rule.NOT_MATCH_RULE;

import java.util.List;

public class Flush implements CompareRule {
    @Override
    public Integer getOrder(List<Poker> pokers) {
        return isFlush(pokers) ? Ranking.FLUSH.getOrder() : NOT_MATCH_RULE;
    }
}