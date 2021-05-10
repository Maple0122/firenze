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
    private static final int PLAYER_SIZE = 4;
    private static final int DEALER_ID = 1;
    public static final int SMALL_BLIND_ID = 2;
    public static final int BIG_BLIND_ID = 3;
    public static final int PLAYER_ID = 4;
    private Poker poker;
    private Player dealer;
    private Player smallBlind;
    private Player bigBlind;
    private Player player;

    @BeforeEach
    public void blind() {
        poker = new Poker(PLAYER_SIZE, MIN_WAGER);
        dealer = new Player(DEALER_ID, INIT_WAGER, poker);
        smallBlind = new Player(SMALL_BLIND_ID, INIT_WAGER, poker);
        bigBlind = new Player(BIG_BLIND_ID, INIT_WAGER, poker);
        player = new Player(PLAYER_ID, INIT_WAGER, poker);
    }

    @Test
    void should_is_1_when_bet_given_a_poker() {
        assertThat(poker.getPot()).isEqualTo(0);
        player.execute(new Bet());
        assertThat(poker.getPot()).isEqualTo(1);
    }

    @Test
    void should_is_3_when_raise_given_a_poker() {
        assertThat(poker.getPot()).isEqualTo(0);
        smallBlind.execute(new Bet());
        assertThat(poker.getPot()).isEqualTo(1);
        bigBlind.execute(new Raise(2));
        assertThat(poker.getPot()).isEqualTo(3);
    }

    @Test
    void should_enter_next_round_when_raise_given_a_poker() {
        assertThat(poker.getRound()).isEqualTo(Round.PREFLOP);
        smallBlind.execute(new Bet());
        bigBlind.execute(new Raise(2));
        player.execute(new Bet());
        dealer.execute(new Bet());
        smallBlind.execute(new Call());
        assertThat(poker.getPot()).isEqualTo(8);
        assertThat(poker.getRound()).isEqualTo(Round.FLOP);
    }

    @Test
    void should_is_80_when_fold_given_a_poker() {
        smallBlind.execute(new Bet());
        bigBlind.execute(new Raise(2));
        player.execute(new Fold());
        assertThat(player.getRemainWager()).isEqualTo(INIT_WAGER);
        assertThat(player.getStatus()).isEqualTo(OFFLINE);
    }

    @Test
    void should_is_8_when_check_given_a_poker() {
        smallBlind.execute(new Bet());
        bigBlind.execute(new Raise(2));
        player.execute(new Bet());
        dealer.execute(new Bet());
        smallBlind.execute(new Call());
        assertThat(poker.getRound()).isEqualTo(Round.FLOP);
        bigBlind.execute(new Check());
        assertThat(poker.getPot()).isEqualTo(8);
        assertThat(poker.getCurrentBid()).isEqualTo(0);
    }

    @Test
    void should_is_103_when_all_in_given_a_poker() {
        smallBlind.execute(new Bet());
        bigBlind.execute(new Raise(2));
        player.execute(new AllIn());
        assertThat(poker.getPot()).isEqualTo(103);
        assertThat(poker.getCurrentBid()).isEqualTo(100);
        assertThat(player.getRemainWager()).isEqualTo(0);
        assertThat(player.getStatus()).isEqualTo(ONLINE);
    }

    @Test
    void should_is_90_when_one_player_online_given_a_poker() {
        smallBlind.execute(new Bet());
        bigBlind.execute(new Raise(2));
        player.execute(new Fold());
        dealer.execute(new Fold());
        smallBlind.execute(new Fold());
        assertThat(poker.getPot()).isEqualTo(3);
        assertThat(poker.getWinnerIds().get(0)).isEqualTo(BIG_BLIND_ID);
        assertThat(bigBlind.getBonus()).isEqualTo(3);
    }

    @Test
    void should_is_300_when_shutdown_given_a_poker() {
        smallBlind.execute(new Bet());
        bigBlind.execute(new Raise(2));
        player.execute(new AllIn());
        dealer.execute(new Fold());
        smallBlind.execute(new AllIn());
        bigBlind.execute(new AllIn());
        assertThat(poker.getPot()).isEqualTo(300);
    }
}
