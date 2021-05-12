package com.tw.rule;

import static com.tw.Number.getOrdinal;
import com.tw.Poker;
import static com.tw.rule.Ranking.STRAIGHT;

import java.util.List;

public class Straight implements CompareRule {
    @Override
    public Integer getOrder(List<Poker> pokers) {
        Integer min = pokers.stream().map(poker -> getOrdinal(poker.getNumber())).sorted()
                .min(Integer::compareTo).orElse(0);
        Integer max = pokers.stream().map(poker -> getOrdinal(poker.getNumber())).sorted()
                .max(Integer::compareTo).orElse(0);
        return max - min == 4 ? STRAIGHT.getOrder() : NOT_MATCH;
    }
}
