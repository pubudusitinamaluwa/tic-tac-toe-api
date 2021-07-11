package com.example.tictactoe.game;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardState {
  private final boolean success;
  private final GameStatus gameStatus;
  private final Player nextPlayer;
  private final String[] board;
  private final Player winner;
}
