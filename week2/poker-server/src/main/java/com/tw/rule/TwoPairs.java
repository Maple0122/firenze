package com.tw.rule;

import com.tw.Poker;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TwoPairs implements CompareRule {
    @Override
    public Integer getOrder(List<Poker> pokers) {
        Set<Integer> sizeSet = new HashSet<>();
        Map<String, List<String>> map = pokers.stream().map(Poker::getNumber)
                .collect(Collectors.groupingBy(Function.identity()));
        map.forEach((k, v) -> sizeSet.add(v.size()));
        return sizeSet.containsAll(Arrays.asList(2, 2, 1)) ? Ranking.TWO_PAIRS.getOrder() : NOT_MATCH;

    }
}
