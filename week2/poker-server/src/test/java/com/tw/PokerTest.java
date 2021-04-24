package com.tw;

import com.tw.domain.Player;
import com.tw.domain.Poker;
import org.junit.jupiter.api.Test;

import static com.tw.domain.PlayerStatus.PASS;
import static org.assertj.core.api.Assertions.assertThat;

public class PokerTest {
    private static final int AMOUNT_FOR_EACH_PLAYER = 100;
    private static final int PLAYER_NUMBER = 4;
    private static final int PLAYER_SIZE = 4;

    @Test
    void should_pass_when_call_given_a_player_and_a_poker() {
        // Given
        Poker poker = new Poker(PLAYER_SIZE);
        Player player = new Player(PLAYER_NUMBER, AMOUNT_FOR_EACH_PLAYER, poker);

        // When
        player.call();

        // Then
        assertThat(player.getStatus()).isEqualTo(PASS);
    }
}
