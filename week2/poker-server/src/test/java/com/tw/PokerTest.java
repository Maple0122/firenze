package com.tw;

import com.tw.domain.Player;
import static com.tw.domain.PlayerStatus.OFFLINE;
import static com.tw.domain.PlayerStatus.ONLINE;
import com.tw.domain.Poker;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public class PokerTest {
    private static final int COIN_FOR_EACH_PLAYER = 100;
    private static final int PLAYER_SIZE = 4;

    @Test
    void should_pot_is_30_when_bet_given_two_player_and_a_poker() {
        // Given
        Poker poker = new Poker(PLAYER_SIZE);
        Player smallBlind = new Player(2, COIN_FOR_EACH_PLAYER, poker);
        Player bigBlind = new Player(3, COIN_FOR_EACH_PLAYER, poker);

        // When
        smallBlind.bet(10);
        smallBlind.bet(20);

        // Then
        assertThat(poker.getPotCoin()).isEqualTo(30);
    }

    @Test
    void should_pot_is_50_when_player_call_given_three_player_and_a_poker() {
        // Given
        Poker poker = new Poker(PLAYER_SIZE);
        Player smallBlind = new Player(2, COIN_FOR_EACH_PLAYER, poker);
        Player bigBlind = new Player(3, COIN_FOR_EACH_PLAYER, poker);
        Player player = new Player(4, COIN_FOR_EACH_PLAYER, poker);

        // When
        smallBlind.bet(10);
        bigBlind.bet(20);
        player.call();

        // Then
        assertThat(poker.getPotCoin()).isEqualTo(50);
    }

    @Test
    void should_pot_is_70_when_raise_given_three_player_and_a_poker() {
        // Given
        Poker poker = new Poker(PLAYER_SIZE);
        Player smallBlind = new Player(2, COIN_FOR_EACH_PLAYER, poker);
        Player bigBlind = new Player(3, COIN_FOR_EACH_PLAYER, poker);
        Player player = new Player(4, COIN_FOR_EACH_PLAYER, poker);

        // When
        smallBlind.bet(10);
        bigBlind.bet(20);
        player.raise(20);

        // Then
        assertThat(poker.getPotCoin()).isEqualTo(70);
    }

    @Test
    void should_pot_is_80_when_raise_given_four_player_and_a_poker() {
        // Given
        Poker poker = new Poker(PLAYER_SIZE);
        Player dealer = new Player(1, COIN_FOR_EACH_PLAYER, poker);
        Player smallBlind = new Player(2, COIN_FOR_EACH_PLAYER, poker);
        Player bigBlind = new Player(3, COIN_FOR_EACH_PLAYER, poker);
        Player player = new Player(4, COIN_FOR_EACH_PLAYER, poker);

        // When
        smallBlind.bet(5);
        bigBlind.bet(10);
        player.raise(10);
        dealer.call();
        smallBlind.call();
        bigBlind.call();

        // Then
        assertThat(poker.getPotCoin()).isEqualTo(80);
    }

    @Test
    void should_player_coin_is_80_and_offline_when_fold_given_four_player_and_a_poker() {
        // Given
        Poker poker = new Poker(PLAYER_SIZE);
        Player dealer = new Player(1, COIN_FOR_EACH_PLAYER, poker);
        Player smallBlind = new Player(2, COIN_FOR_EACH_PLAYER, poker);
        Player bigBlind = new Player(3, COIN_FOR_EACH_PLAYER, poker);
        Player player = new Player(4, COIN_FOR_EACH_PLAYER, poker);

        // When
        smallBlind.bet(10);
        bigBlind.bet(20);
        player.call();
        dealer.call();
        smallBlind.call();
        bigBlind.call();
        player.fold();

        // Then
        assertThat(player.getRemainCoin()).isEqualTo(80);
        assertThat(player.getStatus()).isEqualTo(OFFLINE);
    }

    @Test
    void should_pot_is_110_when_first_player_check_on_flop_given_four_player_and_a_poker() {
        // Given
        Poker poker = new Poker(PLAYER_SIZE);
        Player dealer = new Player(1, COIN_FOR_EACH_PLAYER, poker);
        Player smallBlind = new Player(2, COIN_FOR_EACH_PLAYER, poker);
        Player bigBlind = new Player(3, COIN_FOR_EACH_PLAYER, poker);
        Player player = new Player(4, COIN_FOR_EACH_PLAYER, poker);

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
        assertThat(poker.getPotCoin()).isEqualTo(110);
    }

    @Test
    void should_pot_is_130_and_player_coin_is_0_and_online_when_player_all_in_given_four_player_and_a_poker() {
        // Given
        Poker poker = new Poker(PLAYER_SIZE);
        Player dealer = new Player(1, COIN_FOR_EACH_PLAYER, poker);
        Player smallBlind = new Player(2, COIN_FOR_EACH_PLAYER, poker);
        Player bigBlind = new Player(3, COIN_FOR_EACH_PLAYER, poker);
        Player player = new Player(4, COIN_FOR_EACH_PLAYER, poker);

        // When
        smallBlind.bet(10);
        bigBlind.bet(20);
        player.allIn();
        dealer.fold();

        // Then
        assertThat(poker.getPotCoin()).isEqualTo(130);
        assertThat(player.getRemainCoin()).isEqualTo(0);
        assertThat(player.getStatus()).isEqualTo(ONLINE);
    }

    @Test
    void should_pot_is_90_and_small_blind_win_40_when_only_small_blind_online_given_four_player_and_a_poker() {
        // Given
        Poker poker = new Poker(PLAYER_SIZE);
        Player dealer = new Player(1, COIN_FOR_EACH_PLAYER, poker);
        Player smallBlind = new Player(2, COIN_FOR_EACH_PLAYER, poker);
        Player bigBlind = new Player(3, COIN_FOR_EACH_PLAYER, poker);
        Player player = new Player(4, COIN_FOR_EACH_PLAYER, poker);

        // When
        smallBlind.bet(10);
        bigBlind.bet(20);
        player.call();
        dealer.fold();
        smallBlind.bet(40);
        bigBlind.fold();
        player.fold();

        // Then
        assertThat(poker.getPotCoin()).isEqualTo(90);
        assertThat(poker.getWinnerIds().get(0)).isEqualTo(2);
        assertThat(smallBlind.calculateWinCoin()).isEqualTo(40);
    }

    @Test
    void should_pot_is_300_and_player_win_30_and_big_blind_win_70_when_shutdown_given_four_player_and_a_poker() {
        // Given
        Poker poker = new Poker(PLAYER_SIZE);
        Player dealer = new Player(1, COIN_FOR_EACH_PLAYER, poker);
        Player smallBlind = new Player(2, COIN_FOR_EACH_PLAYER, poker);
        Player bigBlind = new Player(3, COIN_FOR_EACH_PLAYER, poker);
        Player player = new Player(4, COIN_FOR_EACH_PLAYER, poker);
        poker.getWinnerIds().addAll(asList(4, 3));

        // When
        smallBlind.bet(10);
        bigBlind.bet(20);
        player.allIn();
        dealer.fold();
        smallBlind.allIn();
        bigBlind.allIn();

        // Then
        assertThat(poker.getPotCoin()).isEqualTo(300);
        assertThat(player.calculateWinCoinOfAllInPlayer()).isEqualTo(30);
        assertThat(bigBlind.calculateWinCoinOfAllInPlayer() - player.getCoinOfPotWhenAllIn()).isEqualTo(70);
    }
}
