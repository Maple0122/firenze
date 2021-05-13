package com.tw.rule;

import com.tw.Poker;
import static com.tw.Rule.NOT_MATCH_RULE;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Pair implements CompareRule {
    @Override
    public Integer getOrder(List<Poker> pokers) {
        Set<Integer> pokerSort = sortByNumber(pokers);
        return pokerSort.containsAll(Arrays.asList(2, 1, 1, 1)) ? Ranking.PAIR.getOrder() : NOT_MATCH_RULE;
    }
}
