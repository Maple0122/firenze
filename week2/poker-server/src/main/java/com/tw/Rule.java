package com.tw;

import com.tw.rule.CompareRule;
import com.tw.rule.Flush;
import com.tw.rule.FourOfAKind;
import com.tw.rule.FullHouse;
import com.tw.rule.HighCard;
import com.tw.rule.Pair;
import com.tw.rule.RoyalFlush;
import com.tw.rule.Straight;
import com.tw.rule.StraightFlush;
import com.tw.rule.ThreeOfAKind;
import com.tw.rule.TwoPairs;
import static java.util.Map.Entry.comparingByValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rule {
    private final List<CompareRule> allRules;
    private final Map<Integer, Integer> compareResult;

    public Rule() {
        this.compareResult = new HashMap<>();
        this.allRules = new ArrayList<>();
        allRules.addAll(Arrays.asList(new RoyalFlush(),
                new StraightFlush(),
                new FourOfAKind(),
                new FullHouse(),
                new Flush(),
                new Straight(),
                new ThreeOfAKind(),
                new TwoPairs(),
                new Pair(),
                new HighCard()));
    }

    public List<Integer> compare(Map<Integer, List<Poker>> selectedPoker) {
        List<Integer> winnerIds = new ArrayList<>();
        selectedPoker.forEach((key, value) -> {
            Integer ranking = allRules.stream()
                    .map(rule -> rule.getOrder(value))
                    .filter(order -> order > 0)
                    .findFirst()
                    .orElse(0);
            if (ranking > 0) {
                compareResult.put(key, ranking);
            }
        });
        Map.Entry<Integer, Integer> maxEntry = compareResult.entrySet().stream().min(comparingByValue()).orElse(null);
        if (maxEntry == null) {
            return Collections.emptyList();
        }
        compareResult.forEach((k, v) -> {
            if (maxEntry.getValue().equals(v)) {
                winnerIds.add(k);
            }
        });
        return winnerIds;
    }
}
