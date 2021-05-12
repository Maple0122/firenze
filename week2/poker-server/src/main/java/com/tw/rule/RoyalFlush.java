package com.tw.rule;

import com.tw.Poker;
import static com.tw.rule.Ranking.ROYAL_FLUSH;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RoyalFlush implements CompareRule {
    @Override
    public Integer getOrder(List<Poker> pokers) {
        List<String> royal = Arrays.asList("A", "K", "Q", "J", "10");
        String type = pokers.get(0).getType();
        boolean flush = pokers.stream().allMatch(poker -> poker.getType().equals(type));
        boolean isRoyal = pokers.stream().map(Poker::getNumber).distinct()
                .collect(Collectors.toList()).containsAll(royal);
        return flush && isRoyal ? ROYAL_FLUSH.getOrder() : NOT_MATCH;
    }
}
