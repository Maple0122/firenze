package com.tw;

import com.tw.rule.*;

import java.util.*;
import java.util.stream.Collectors;

public class Rule {
    public static final int NOT_MATCH_RULE = 0;
    private static final Map<Integer, Integer> compareResult = new HashMap<>();
    private static final List<CompareRule> allRules = Arrays.asList(new RoyalFlush(),
            new StraightFlush(),
            new FourOfAKind(),
            new FullHouse(),
            new Flush(),
            new Straight(),
            new ThreeOfAKind(),
            new TwoPairs(),
            new Pair(),
            new HighCard());

    public static List<Integer> compare(Map<Integer, List<Poker>> selectedPokerMap) {
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
