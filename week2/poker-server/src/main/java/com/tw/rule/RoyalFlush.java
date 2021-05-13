package com.tw.rule;

import com.tw.Number;
import com.tw.Poker;
import static com.tw.Rule.NOT_MATCH_RULE;
import static com.tw.rule.Ranking.ROYAL_FLUSH;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RoyalFlush implements CompareRule {
    @Override
    public Integer getOrder(List<Poker> pokers) {
        List<String> royals = Stream.of(Number.ACE, Number.KING, Number.QUEEN, Number.JACK, Number.TEN)
                .map(Number::getNumber)
                .collect(Collectors.toList());
        boolean isRoyal = pokers.stream().map(Poker::getNumber).distinct()
                .collect(Collectors.toList()).containsAll(royals);
        return isFlush(pokers) && isRoyal ? ROYAL_FLUSH.getOrder() : NOT_MATCH_RULE;
    }
}
