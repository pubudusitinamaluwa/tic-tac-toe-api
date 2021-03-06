package com.example.tictactoe.game;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardState {
  private final boolean success;
  private final GameStatus gameStatus;
  private final Player nextStriker;
  private final String[] board;
  private final Player winner;
  private final String message;
}
