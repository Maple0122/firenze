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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Rule {
    public static final int NOT_MATCH_RULE = 0;
    private final List<CompareRule> allRules = new ArrayList<>();
    private final Map<Integer, Integer> compareResult = new HashMap<>();

    public Rule() {
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

    public List<Integer> compare(Map<Integer, List<Poker>> selectedPokerMap) {
        selectedPokerMap.forEach((key, value) -> allRules.stream()
                .map(rule -> rule.getOrder(value))
                .filter(ranking -> ranking > 0)
                .findFirst()
                .ifPresent(ranking -> compareResult.put(key, ranking)));
        int ranking = compareResult.values().stream().mapToInt(Integer::intValue)
                .min().orElse(NOT_MATCH_RULE);
        if (ranking == NOT_MATCH_RULE) {
            return Collections.emptyList();
        }
        return compareResult.entrySet().stream()
                .filter(entry -> entry.getValue().equals(ranking))
                .map(Map.Entry::getKey).collect(Collectors.toList());
    }
}
