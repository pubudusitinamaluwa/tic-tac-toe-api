package com.example.tictactoe.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GameBoardTest {
  GameBoard gameBoard;

  @BeforeEach
  void init() {
    gameBoard = new GameBoard("1");
  }

  @Test
  void invalid_turn_skips_updating_board() {
    BoardUpdateStatus updateStatus = gameBoard.updateBoard(Player.O, 1);
    assert !updateStatus.isSuccess();
    assert updateStatus.getNextPlayer().equals(Player.X);
    assert updateStatus.getBoardStatus() == BoardStatus.ACTIVE;
    assert updateStatus.getBoard()[1] == null;
    assert updateStatus.getWinner().equals(Player.NONE);
  }

  @Test
  void valid_turn_updates_board() {
    BoardUpdateStatus updateStatus = gameBoard.updateBoard(Player.X, 1);
    assert updateStatus.isSuccess();
    assert updateStatus.getNextPlayer().equals(Player.O);
    assert updateStatus.getBoardStatus() == BoardStatus.ACTIVE;
    assert updateStatus.getBoard()[1].equals("X");
    assert updateStatus.getWinner().equals(Player.NONE);
  }

}