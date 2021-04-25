package com.tw;

import com.tw.domain.Player;
import static com.tw.domain.PlayerStatus.OFFLINE;
import static com.tw.domain.PlayerStatus.ONLINE;
import com.tw.domain.Poker;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PokerTest {
    private static final int AMOUNT_FOR_EACH_PLAYER = 100;
    private static final int PLAYER_SIZE = 4;

    @Test
    void should_pot_is_30_when_bet_given_two_player_and_a_poker() {
        // Given
        Poker poker = new Poker(PLAYER_SIZE);
        Player smallBlind = new Player(2, AMOUNT_FOR_EACH_PLAYER, poker);
        Player bigBlind = new Player(3, AMOUNT_FOR_EACH_PLAYER, poker);

        // When
        smallBlind.bet(10);
        smallBlind.bet(20);

        // Then
        assertThat(poker.getAmountOfPot()).isEqualTo(30);
    }

    @Test
    void should_pot_is_50_when_player_call_given_three_player_and_a_poker() {
        // Given
        Poker poker = new Poker(PLAYER_SIZE);
        Player smallBlind = new Player(2, AMOUNT_FOR_EACH_PLAYER, poker);
        Player bigBlind = new Player(3, AMOUNT_FOR_EACH_PLAYER, poker);
        Player player = new Player(4, AMOUNT_FOR_EACH_PLAYER, poker);

        // When
        smallBlind.bet(10);
        bigBlind.bet(20);
        player.call();

        // Then
        assertThat(poker.getAmountOfPot()).isEqualTo(50);
    }

    @Test
    void should_pot_is_70_when_raise_given_three_player_and_a_poker() {
        // Given
        Poker poker = new Poker(PLAYER_SIZE);
        Player smallBlind = new Player(2, AMOUNT_FOR_EACH_PLAYER, poker);
        Player bigBlind = new Player(3, AMOUNT_FOR_EACH_PLAYER, poker);
        Player player = new Player(4, AMOUNT_FOR_EACH_PLAYER, poker);

        // When
        smallBlind.bet(10);
        bigBlind.bet(20);
        player.raise(20);

        // Then
        assertThat(poker.getAmountOfPot()).isEqualTo(70);
    }

    @Test
    void should_pot_is_80_when_raise_given_four_player_and_a_poker() {
        // Given
        Poker poker = new Poker(PLAYER_SIZE);
        Player dealer = new Player(1, AMOUNT_FOR_EACH_PLAYER, poker);
        Player smallBlind = new Player(2, AMOUNT_FOR_EACH_PLAYER, poker);
        Player bigBlind = new Player(3, AMOUNT_FOR_EACH_PLAYER, poker);
        Player player = new Player(4, AMOUNT_FOR_EACH_PLAYER, poker);

        // When
        smallBlind.bet(5);
        bigBlind.bet(10);
        player.raise(10);
        dealer.call();
        smallBlind.call();
        bigBlind.call();

        // Then
        assertThat(poker.getAmountOfPot()).isEqualTo(80);
    }

    @Test
    void should_player_coin_is_80_and_offline_when_fold_given_four_player_and_a_poker() {
        // Given
        Poker poker = new Poker(PLAYER_SIZE);
        Player dealer = new Player(1, AMOUNT_FOR_EACH_PLAYER, poker);
        Player smallBlind = new Player(2, AMOUNT_FOR_EACH_PLAYER, poker);
        Player bigBlind = new Player(3, AMOUNT_FOR_EACH_PLAYER, poker);
        Player player = new Player(4, AMOUNT_FOR_EACH_PLAYER, poker);

        // When
        smallBlind.bet(10);
        bigBlind.bet(20);
        player.call();
        dealer.call();
        smallBlind.call();
        bigBlind.call();
        player.fold();

        // Then
        assertThat(player.getAmount()).isEqualTo(80);
        assertThat(player.getStatus()).isEqualTo(OFFLINE);
    }

    @Test
    void should_pot_is_110_when_first_player_check_on_flop_given_four_player_and_a_poker() {
        // Given
        Poker poker = new Poker(PLAYER_SIZE);
        Player dealer = new Player(1, AMOUNT_FOR_EACH_PLAYER, poker);
        Player smallBlind = new Player(2, AMOUNT_FOR_EACH_PLAYER, poker);
        Player bigBlind = new Player(3, AMOUNT_FOR_EACH_PLAYER, poker);
        Player player = new Player(4, AMOUNT_FOR_EACH_PLAYER, poker);

        // When
        smallBlind.bet(10);
        bigBlind.bet(20);
        player.call();
        dealer.call();
        smallBlind.check();
        bigBlind.check();
        player.bet(20);
        dealer.call();

        // Then
        assertThat(poker.getAmountOfPot()).isEqualTo(110);
    }

    @Test
    void should_pot_is_130_and_player_coin_is_0_and_online_when_player_all_in_given_four_player_and_a_poker() {
        // Given
        Poker poker = new Poker(PLAYER_SIZE);
        Player dealer = new Player(1, AMOUNT_FOR_EACH_PLAYER, poker);
        Player smallBlind = new Player(2, AMOUNT_FOR_EACH_PLAYER, poker);
        Player bigBlind = new Player(3, AMOUNT_FOR_EACH_PLAYER, poker);
        Player player = new Player(4, AMOUNT_FOR_EACH_PLAYER, poker);

        // When
        smallBlind.bet(10);
        bigBlind.bet(20);
        player.allIn();
        dealer.fold();

        // Then
        assertThat(poker.getAmountOfPot()).isEqualTo(130);
        assertThat(player.getAmount()).isEqualTo(0);
        assertThat(player.getStatus()).isEqualTo(ONLINE);
    }
}
