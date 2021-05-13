package com.tw.rule;

import static com.tw.Number.getOrdinal;
import com.tw.Poker;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface CompareRule {

    Integer getOrder(List<Poker> pokers);

    default Optional<Poker> first(List<Poker> pokers) {
        if (CollectionUtils.isEmpty(pokers)) {
            return Optional.empty();
        }
        return pokers.stream().findFirst();
    }

    default Set<Integer> sortByNumber(List<Poker> pokers) {
        Set<Integer> sizeSet = new HashSet<>();
        Map<String, List<String>> map = pokers.stream().map(Poker::getNumber)
                .collect(Collectors.groupingBy(Function.identity()));
        map.forEach((k, v) -> sizeSet.add(v.size()));
        return sizeSet;
    }

    default boolean isFlush(List<Poker> pokers) {
        String type = first(pokers).map(Poker::getType).orElse(null);
        return pokers.stream().map(Poker::getType).allMatch(t -> t.equals(type));
    }

    default boolean isStraight(List<Poker> pokers) {
        Integer min = pokers.stream().map(poker -> getOrdinal(poker.getNumber()))
                .min(Integer::compareTo).orElse(0);
        Integer max = pokers.stream().map(poker -> getOrdinal(poker.getNumber()))
                .max(Integer::compareTo).orElse(0);
        return (max - min) == 4;
    }
}
