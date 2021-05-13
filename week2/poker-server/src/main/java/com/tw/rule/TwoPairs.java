package com.tw.rule;

import com.tw.Poker;
import static com.tw.Rule.NOT_MATCH_RULE;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class TwoPairs implements CompareRule {
    @Override
    public Integer getOrder(List<Poker> pokers) {
        Set<Integer> sizeSet = sortByNumber(pokers);
        return sizeSet.containsAll(Arrays.asList(2, 2, 1)) ? Ranking.TWO_PAIRS.getOrder() : NOT_MATCH_RULE;

    }
}
