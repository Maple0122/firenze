package com.tw;

import com.tw.action.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class GameTest {
    public static final int A_ID = 1;
    public static final int B_ID = 2;
    public static final int C_ID = 3;
    private Game game;

    @BeforeEach
    public void setup() {
        game = new Game(new Player(A_ID), new Player(B_ID), new Player(C_ID));
    }

    @Test
    void should_return_1_pot_when_player_a_bet_1_given_players() {
        assertThat(game.getPot()).isEqualTo(0);
        game.execute(new Bet());
        assertThat(game.getPot()).isEqualTo(1);
    }

    @Test
    void should_return_3_pot_and_current_bid_is_2_when_player_a_bet_1_and_player_b_raise_2_given_players() {
        assertThat(game.getPot()).isEqualTo(0);
        game.execute(new Bet());
        assertThat(game.getPot()).isEqualTo(1);
        game.execute(new Raise(2));
        assertThat(game.getPot()).isEqualTo(3);
        assertThat(game.getCurrentBid()).isEqualTo(2);
    }

    @Test
    void should_return_3_pot_and_enter_next_round_when_all_players_bet_same_wager_given_players() {
        assertThat(game.getRound()).isEqualTo(Round.PREFLOP);
        game.execute(new Bet());
        game.execute(new Bet());
        game.execute(new Bet());
        assertThat(game.getRound()).isEqualTo(Round.FLOP);
        assertThat(game.getPot()).isEqualTo(3);
    }

    @Test
    void should_enter_next_round_when_player_b_raise_2_player_a_call_given_players() {
        assertThat(game.getRound()).isEqualTo(Round.PREFLOP);
        game.execute(new Bet());
        game.execute(new Raise(2));
        game.execute(new Bet());
        assertThat(game.getRound()).isEqualTo(Round.PREFLOP);
        game.execute(new Call());
        assertThat(game.getRound()).isEqualTo(Round.FLOP);
    }

    @Test
    void should_return_0_pot_and_player_a_is_inactive_when_player_a_fold_given_player_a() {
        game.execute(new Fold());
        assertThat(game.getPot()).isEqualTo(0);
    }

    @Test
    void should_enter_next_round_when_all_players_check_given_players() {
        assertThat(game.getRound()).isEqualTo(Round.PREFLOP);
        game.execute(new Bet());
        game.execute(new Bet());
        game.execute(new Bet());
        assertThat(game.getRound()).isEqualTo(Round.FLOP);
        game.execute(new Check());
        game.execute(new Check());
        game.execute(new Check());
        assertThat(game.getRound()).isEqualTo(Round.TURN);
    }

    @Test
    void should_player_c_win_3_bonus_when_player_a_and_player_b_both_fold_given_players() {
        game.execute(new Bet());
        game.execute(new Bet());
        game.execute(new Bet());
        assertThat(game.getRound()).isEqualTo(Round.FLOP);
        assertThat(game.getPot()).isEqualTo(3);
        game.execute(new Fold());
        game.execute(new Fold());
        game.shutDown(new HashMap<>());
        assertThat(game.getPot()).isEqualTo(3);
        assertThat(game.getWinnerIds().get(0)).isEqualTo(C_ID);
        assertThat(game.checkout()).isEqualTo(3);
    }

    @Test
    void should_player_b_win_12_bonus_when_all_players_bet_given_players() {
        Map<Integer, List<Poker>> selectedPoker = new HashMap<>();
        selectedPoker.put(A_ID, asList(new Poker("黑桃", "8"),
                new Poker("红桃", "8"),
                new Poker("方块", "8"),
                new Poker("梅花", "J"),
                new Poker("黑桃", "4")));
        selectedPoker.put(B_ID, asList(new Poker("红桃", "A"),
                new Poker("红桃", "K"),
                new Poker("红桃", "Q"),
                new Poker("红桃", "J"),
                new Poker("红桃", "10")));
        selectedPoker.put(C_ID, asList(new Poker("红桃", "6"),
                new Poker("黑桃", "6"),
                new Poker("方块", "6"),
                new Poker("梅花", "6"),
                new Poker("黑桃", "10")));
        game.execute(new Bet());
        game.execute(new Bet());
        game.execute(new Bet());
        game.execute(new Bet());
        game.execute(new Bet());
        game.execute(new Bet());
        game.execute(new Bet());
        game.execute(new Bet());
        game.execute(new Bet());
        assertThat(game.getRound()).isEqualTo(Round.RIVER);
        assertThat(game.getPot()).isEqualTo(9);
        game.execute(new Bet());
        game.execute(new Bet());
        game.execute(new Bet());
        game.shutDown(selectedPoker);
        assertThat(game.getPot()).isEqualTo(12);
        assertThat(game.getWinnerIds().get(0)).isEqualTo(B_ID);
        assertThat(game.checkout()).isEqualTo(12);
    }

    @Test
    void should_player_b_win_6_bonus_and_player_c_win_6_bonus_when_all_players_bet_given_players() {
        Map<Integer, List<Poker>> selectedPoker = new HashMap<>();
        selectedPoker.put(A_ID, asList(new Poker("黑桃", "8"),
                new Poker("红桃", "8"),
                new Poker("方块", "8"),
                new Poker("梅花", "J"),
                new Poker("黑桃", "4")));
        selectedPoker.put(B_ID, asList(new Poker("黑桃", "A"),
                new Poker("黑桃", "K"),
                new Poker("黑桃", "Q"),
                new Poker("黑桃", "J"),
                new Poker("黑桃", "10")));
        selectedPoker.put(C_ID, asList(new Poker("红桃", "A"),
                new Poker("红桃", "K"),
                new Poker("红桃", "Q"),
                new Poker("红桃", "J"),
                new Poker("红桃", "10")));
        game.execute(new Bet());
        game.execute(new Bet());
        game.execute(new Bet());
        game.execute(new Bet());
        game.execute(new Bet());
        game.execute(new Bet());
        game.execute(new Bet());
        game.execute(new Bet());
        game.execute(new Bet());
        assertThat(game.getRound()).isEqualTo(Round.RIVER);
        assertThat(game.getPot()).isEqualTo(9);
        game.execute(new Bet());
        game.execute(new Bet());
        game.execute(new Bet());
        game.shutDown(selectedPoker);
        assertThat(game.getPot()).isEqualTo(12);
        assertThat(game.getWinnerIds().get(0)).isEqualTo(B_ID);
        assertThat(game.getWinnerIds().get(1)).isEqualTo(C_ID);
        assertThat(game.checkout()).isEqualTo(6);
        assertThat(game.checkout()).isEqualTo(6);
    }
}
