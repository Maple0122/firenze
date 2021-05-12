package com.tw.rule;

import static com.tw.Number.getOrdinal;
import com.tw.Poker;
import static com.tw.rule.Ranking.STRAIGHT_FLUSH;

import java.util.List;

public class StraightFlush implements CompareRule {
    @Override
    public Integer getOrder(List<Poker> pokers) {
        String type = pokers.get(0).getType();
        boolean flush = pokers.stream().allMatch(poker -> poker.getType().equals(type));
        Integer min = pokers.stream().map(poker -> getOrdinal(poker.getNumber())).sorted()
                .min(Integer::compareTo).orElse(0);
        Integer max = pokers.stream().map(poker -> getOrdinal(poker.getNumber())).sorted()
                .max(Integer::compareTo).orElse(0);
        return flush && (max - min == 4) ? STRAIGHT_FLUSH.getOrder() : NOT_MATCH;
    }
}
