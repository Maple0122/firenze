package com.tw;

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
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.List;

class RuleTest {
    @Test
    void should_return_1() {
        List<Poker> pokers = asList(new Poker("红桃", "A"),
                new Poker("红桃", "K"),
                new Poker("红桃", "Q"),
                new Poker("红桃", "J"),
                new Poker("红桃", "10"));
        RoyalFlush royalFlush = new RoyalFlush();
        Integer order = royalFlush.getOrder(pokers);
        assertThat(order).isEqualTo(1);
    }

    @Test
    void should_return_2() {
        List<Poker> pokers = asList(new Poker("红桃", "10"),
                new Poker("红桃", "9"),
                new Poker("红桃", "8"),
                new Poker("红桃", "7"),
                new Poker("红桃", "6"));
        StraightFlush straightFlush = new StraightFlush();
        Integer order = straightFlush.getOrder(pokers);
        assertThat(order).isEqualTo(2);
    }

    @Test
    void should_return_3() {
        List<Poker> pokers = asList(new Poker("红桃", "6"),
                new Poker("黑桃", "6"),
                new Poker("方块", "6"),
                new Poker("梅花", "6"),
                new Poker("黑桃", "10"));
        FourOfAKind fourOfAKind = new FourOfAKind();
        Integer order = fourOfAKind.getOrder(pokers);
        assertThat(order).isEqualTo(3);
    }

    @Test
    void should_return_4() {
        List<Poker> pokers = asList(new Poker("红桃", "6"),
                new Poker("黑桃", "6"),
                new Poker("方块", "6"),
                new Poker("梅花", "3"),
                new Poker("黑桃", "3"));
        FullHouse fullHouse = new FullHouse();
        Integer order = fullHouse.getOrder(pokers);
        assertThat(order).isEqualTo(4);
    }

    @Test
    void should_return_5() {
        List<Poker> pokers = asList(new Poker("红桃", "6"),
                new Poker("红桃", "8"),
                new Poker("红桃", "10"),
                new Poker("红桃", "J"),
                new Poker("红桃", "A"));
        Flush flush = new Flush();
        Integer order = flush.getOrder(pokers);
        assertThat(order).isEqualTo(5);
    }

    @Test
    void should_return_6() {
        List<Poker> pokers = asList(new Poker("红桃", "6"),
                new Poker("黑桃", "7"),
                new Poker("方块", "8"),
                new Poker("梅花", "9"),
                new Poker("红桃", "10"));
        Straight straight = new Straight();
        Integer order = straight.getOrder(pokers);
        assertThat(order).isEqualTo(6);
    }

    @Test
    void should_return_7() {
        List<Poker> pokers = asList(new Poker("黑桃", "8"),
                new Poker("红桃", "8"),
                new Poker("方块", "8"),
                new Poker("梅花", "J"),
                new Poker("黑桃", "4"));
        ThreeOfAKind threeOfAKind = new ThreeOfAKind();
        Integer order = threeOfAKind.getOrder(pokers);
        assertThat(order).isEqualTo(7);
    }

    @Test
    void should_return_8() {
        List<Poker> pokers = asList(new Poker("黑桃", "8"),
                new Poker("红桃", "8"),
                new Poker("方块", "J"),
                new Poker("梅花", "J"),
                new Poker("黑桃", "4"));
        TwoPairs twoPairs = new TwoPairs();
        Integer order = twoPairs.getOrder(pokers);
        assertThat(order).isEqualTo(8);
    }

    @Test
    void should_return_9() {
        List<Poker> pokers = asList(new Poker("黑桃", "8"),
                new Poker("红桃", "8"),
                new Poker("方块", "9"),
                new Poker("梅花", "J"),
                new Poker("黑桃", "4"));
        Pair pair = new Pair();
        Integer order = pair.getOrder(pokers);
        assertThat(order).isEqualTo(9);
    }

    @Test
    void should_return_10() {
        List<Poker> pokers = asList(new Poker("黑桃", "8"),
                new Poker("红桃", "6"),
                new Poker("方块", "9"),
                new Poker("梅花", "J"),
                new Poker("黑桃", "4"));
        HighCard highCard = new HighCard();
        Integer order = highCard.getOrder(pokers);
        assertThat(order).isEqualTo(10);
    }
}