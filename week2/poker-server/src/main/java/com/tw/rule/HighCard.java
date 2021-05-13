package com.tw.rule;

import com.tw.Poker;
import static com.tw.Rule.NOT_MATCH_RULE;

import java.util.List;

public class HighCard implements CompareRule {
    @Override
    public Integer getOrder(List<Poker> pokers) {
        boolean highCard = pokers.stream().map(Poker::getNumber).distinct().count() == 5;
        return highCard ? Ranking.HIGH_CARD.getOrder() : NOT_MATCH_RULE;
    }
}
