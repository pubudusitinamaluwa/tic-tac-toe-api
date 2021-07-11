package com.example.tictactoe.game;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardUpdateStatus {
  private final boolean success;
  private final BoardStatus boardStatus;
  private final Player nextPlayer;
  private final String[] board;
}
