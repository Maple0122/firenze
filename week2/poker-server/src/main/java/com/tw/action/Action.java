package com.tw.action;

import com.tw.Game;
import com.tw.Player;

public interface Action {
    void execute(Game game, Player player);
}
