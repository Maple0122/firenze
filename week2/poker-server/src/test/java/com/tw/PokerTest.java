package com.tw;

import com.tw.domain.Player;
import com.tw.domain.Poker;
import org.junit.jupiter.api.Test;

import static com.tw.domain.PlayerStatus.PASS;
import static org.assertj.core.api.Assertions.assertThat;

public class PokerTest {
    private static final int AMOUNT_FOR_EACH_PLAYER = 100;
    private static final int PLAYER_SIZE = 4;

    @Test
    void should_pot_is_10_when_bet_10_given_a_player_and_a_poker() {
        // Given
        Poker poker = new Poker(PLAYER_SIZE);
        Player player = new Player(1, AMOUNT_FOR_EACH_PLAYER, poker);

        // When
        player.bet(10);

        // Then
        assertThat(poker.getAmountOfPot()).isEqualTo(10);
    }

    @Test
    void should_pot_is_50_when_player_call_given_a_player_and_a_poker() {
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
}
