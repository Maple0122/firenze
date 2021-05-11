package com.tw;

import static com.tw.Status.ACTIVE;
import static com.tw.Status.INACTIVE;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameTest {
    private static final int INIT_WAGER = 100;
    private static final int MIN_WAGER = 1;
    private static final int PLAYER_SIZE = 3;
    public static final int A_ID = 1;
    public static final int B_ID = 2;
    public static final int C_ID = 3;
    private Game game;
    private Player a;
    private Player b;
    private Player c;

    @BeforeEach
    public void setup() {
        game = new Game(PLAYER_SIZE, MIN_WAGER);
        a = new Player(A_ID, INIT_WAGER, game);
        b = new Player(B_ID, INIT_WAGER, game);
        c = new Player(C_ID, INIT_WAGER, game);
    }

    @Test
    void should_is_1_when_player_a_bet_given_min_wager() {
        assertThat(game.getPot()).isEqualTo(0);
        a.execute(new Bet());
        assertThat(game.getPot()).isEqualTo(1);
    }

    @Test
    void should_is_3_when_player_a_bet_and_player_b_raise_given_min_wager() {
        assertThat(game.getPot()).isEqualTo(0);
        a.execute(new Bet());
        assertThat(game.getPot()).isEqualTo(1);
        b.execute(new Raise(2));
        assertThat(game.getPot()).isEqualTo(3);
        assertThat(game.getCurrentBid()).isEqualTo(2);
    }

    @Test
    void should_enter_next_round_when_player_b_raise_given_min_wager() {
        assertThat(game.getRound()).isEqualTo(Round.PREFLOP);
        a.execute(new Bet());
        b.execute(new Raise(2));
        c.execute(new Bet());
        assertThat(game.getPot()).isEqualTo(5);
        assertThat(game.getRound()).isEqualTo(Round.PREFLOP);
        a.execute(new Call());
        assertThat(game.getPot()).isEqualTo(6);
        assertThat(game.getRound()).isEqualTo(Round.FLOP);
        assertThat(game.getCurrentBid()).isEqualTo(2);
    }

    @Test
    void should_enter_next_round_when_bet_same_wager_given_min_wager() {
        assertThat(game.getRound()).isEqualTo(Round.PREFLOP);
        a.execute(new Bet());
        b.execute(new Bet());
        c.execute(new Bet());
        assertThat(game.getRound()).isEqualTo(Round.FLOP);
        assertThat(game.getPot()).isEqualTo(3);
    }

    @Test
    void should_inactive_when_player_a_fold_given_min_wager() {
        a.execute(new Fold());
        assertThat(a.getStatus()).isEqualTo(INACTIVE);
        assertThat(game.getPot()).isEqualTo(0);
    }

    @Test
    void should_enter_next_round_and_not_increase_pot_when_all_players_check_given_min_wager() {
        assertThat(game.getRound()).isEqualTo(Round.PREFLOP);
        a.execute(new Bet());
        b.execute(new Bet());
        c.execute(new Bet());
        assertThat(game.getRound()).isEqualTo(Round.FLOP);
        assertThat(game.getPot()).isEqualTo(3);
        a.execute(new Check());
        b.execute(new Check());
        c.execute(new Check());
        assertThat(game.getPot()).isEqualTo(3);
        assertThat(game.getCurrentBid()).isEqualTo(0);
        assertThat(game.getRound()).isEqualTo(Round.TURN);
    }

    @Test
    void should_is_100_when_player_a_all_in_given_min_wager() {
        assertThat(game.getPot()).isEqualTo(0);
        a.execute(new AllIn());
        assertThat(game.getPot()).isEqualTo(100);
        assertThat(game.getCurrentBid()).isEqualTo(100);
    }

    @Test
    void should_get_bonus_3_when_player_a_and_b_both_fold_given_min_wager() {
        a.execute(new Bet());
        b.execute(new Bet());
        c.execute(new Bet());
        assertThat(game.getRound()).isEqualTo(Round.FLOP);
        assertThat(game.getPot()).isEqualTo(3);
        a.execute(new Fold());
        b.execute(new Fold());
        assertThat(game.getPot()).isEqualTo(3);
        assertThat(game.getWinnerIds().get(0)).isEqualTo(C_ID);
        assertThat(game.getBonus(c)).isEqualTo(3);
    }
}
