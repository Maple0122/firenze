package com.tw;

import com.tw.domain.*;

import static com.tw.domain.PlayerStatus.OFFLINE;
import static com.tw.domain.PlayerStatus.ONLINE;
import static org.assertj.core.api.Assertions.assertThat;

import com.tw.domain.Poker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PokerTest {
    private static final int INIT_WAGER = 100;
    private static final int MIN_WAGER = 1;
    private static final int PLAYER_SIZE = 3;
    public static final int A_ID = 1;
    public static final int B_ID = 2;
    public static final int C_ID = 3;
    private Poker poker;
    private Player a;
    private Player b;
    private Player c;

    @BeforeEach
    public void blind() {
        poker = new Poker(PLAYER_SIZE, MIN_WAGER);
        a = new Player(A_ID, INIT_WAGER, poker);
        b = new Player(B_ID, INIT_WAGER, poker);
        c = new Player(C_ID, INIT_WAGER, poker);
    }

    @Test
    void should_is_1_when_bet_given_a_poker() {
        assertThat(poker.getPot()).isEqualTo(0);
        a.execute(new Bet());
        assertThat(poker.getPot()).isEqualTo(1);
    }

    @Test
    void should_is_3_when_raise_given_a_poker() {
        assertThat(poker.getPot()).isEqualTo(0);
        a.execute(new Bet());
        assertThat(poker.getPot()).isEqualTo(1);
        b.execute(new Raise(2));
        assertThat(poker.getPot()).isEqualTo(3);
    }

    @Test
    void should_enter_next_round_when_raise_given_a_poker() {
        assertThat(poker.getRound()).isEqualTo(Round.PREFLOP);
        a.execute(new Bet());
        b.execute(new Raise(2));
        c.execute(new Bet());
        a.execute(new Call());
        assertThat(poker.getPot()).isEqualTo(6);
        assertThat(poker.getRound()).isEqualTo(Round.FLOP);
    }

    @Test
    void should_is_80_when_fold_given_a_poker() {
        a.execute(new Bet());
        b.execute(new Raise(2));
        c.execute(new Fold());
        assertThat(c.getRemainWager()).isEqualTo(INIT_WAGER);
        assertThat(c.getStatus()).isEqualTo(OFFLINE);
    }

    @Test
    void should_is_8_when_check_given_a_poker() {
        a.execute(new Bet());
        b.execute(new Raise(2));
        c.execute(new Bet());
        a.execute(new Call());
        assertThat(poker.getRound()).isEqualTo(Round.FLOP);
        b.execute(new Check());
        assertThat(poker.getPot()).isEqualTo(6);
        assertThat(poker.getCurrentBid()).isEqualTo(0);
    }

    @Test
    void should_is_103_when_all_in_given_a_poker() {
        a.execute(new Bet());
        b.execute(new Raise(2));
        c.execute(new AllIn());
        assertThat(poker.getPot()).isEqualTo(103);
        assertThat(poker.getCurrentBid()).isEqualTo(100);
        assertThat(c.getRemainWager()).isEqualTo(0);
        assertThat(c.getStatus()).isEqualTo(ONLINE);
    }

    @Test
    void should_is_90_when_one_player_online_given_a_poker() {
        a.execute(new Bet());
        b.execute(new Raise(2));
        c.execute(new Fold());
        a.execute(new Fold());
        assertThat(poker.getPot()).isEqualTo(3);
        assertThat(poker.getWinnerIds().get(0)).isEqualTo(B_ID);
        assertThat(b.getBonus()).isEqualTo(3);
    }

    @Test
    void should_is_300_when_shutdown_given_a_poker() {
        a.execute(new Bet());
        b.execute(new Raise(2));
        c.execute(new AllIn());
        a.execute(new AllIn());
        b.execute(new AllIn());
        assertThat(poker.getPot()).isEqualTo(300);
    }
}
