package com.tw.rule;

import com.tw.Poker;
import static com.tw.Rule.NOT_MATCH_RULE;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class FullHouse implements CompareRule {
    @Override
    public Integer getOrder(List<Poker> pokers) {
        Set<Integer> pokerSort = sortByNumber(pokers);
        return pokerSort.containsAll(Arrays.asList(2, 3)) ? Ranking.FULL_HOUSE.getOrder() : NOT_MATCH_RULE;
    }
}
