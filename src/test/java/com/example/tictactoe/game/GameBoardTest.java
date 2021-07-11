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
    BoardState updateStatus = gameBoard.updateBoard(Player.O, 1);
    assert !updateStatus.isSuccess();
    assert updateStatus.getNextPlayer().equals(Player.X);
    assert updateStatus.getGameStatus() == GameStatus.ACTIVE;
    assert updateStatus.getBoard()[1] == null;
    assert updateStatus.getWinner().equals(Player.NONE);
  }

  @Test
  void board_update_fails_on_updating_pre_written_index() {
    gameBoard.updateBoard(Player.X, 1);
    BoardState updateStatus = gameBoard.updateBoard(Player.O, 1);
    assert !updateStatus.isSuccess();
    assert updateStatus.getNextPlayer().equals(Player.O);
    assert updateStatus.getGameStatus() == GameStatus.ACTIVE;
    assert "X".equals(updateStatus.getBoard()[1]);
    assert updateStatus.getWinner().equals(Player.NONE);
  }

  @Test
  void valid_turn_updates_board() {
    BoardState updateStatus = gameBoard.updateBoard(Player.X, 1);
    assert updateStatus.isSuccess();
    assert updateStatus.getNextPlayer().equals(Player.O);
    assert updateStatus.getGameStatus() == GameStatus.ACTIVE;
    assert updateStatus.getBoard()[1].equals("X");
    assert updateStatus.getWinner().equals(Player.NONE);
  }

  @Test
  void board_evaluates_win() {
    /* |---|---|---|  |---|---|---|
       | 0 | 1 | 2 |  | X |   | O |
       |-----------|  |-----------|
       | 3 | 4 | 5 |  | X | O |   |
       |-----------|  |-----------|
       | 6 | 7 | 8 |  | X | O | X |
       |---|---|---|  |---|---|---|*/
    gameBoard.updateBoard(Player.X, 0);
    gameBoard.updateBoard(Player.O, 4);
    gameBoard.updateBoard(Player.X, 8);
    gameBoard.updateBoard(Player.O, 2);
    gameBoard.updateBoard(Player.X, 6);
    gameBoard.updateBoard(Player.O, 7);
    BoardState updateStatus = gameBoard.updateBoard(Player.X, 3);

    assert updateStatus.isSuccess();
    assert updateStatus.getNextPlayer().equals(Player.NONE);
    assert updateStatus.getGameStatus().equals(GameStatus.WIN);
    assert updateStatus.getWinner().equals(Player.X);
  }

  @Test
  void board_update_fails_after_win() {
    /* |---|---|---|  |---|---|---|
       | 0 | 1 | 2 |  | X |   | O |
       |-----------|  |-----------|
       | 3 | 4 | 5 |  | X | O |   |
       |-----------|  |-----------|
       | 6 | 7 | 8 |  | X | O | X |
       |---|---|---|  |---|---|---|*/
    gameBoard.updateBoard(Player.X, 0);
    gameBoard.updateBoard(Player.O, 4);
    gameBoard.updateBoard(Player.X, 8);
    gameBoard.updateBoard(Player.O, 2);
    gameBoard.updateBoard(Player.X, 6);
    gameBoard.updateBoard(Player.O, 7);
    gameBoard.updateBoard(Player.X, 3);

    BoardState updateStatus = gameBoard.updateBoard(Player.O, 1);
    assert !updateStatus.isSuccess();
    assert updateStatus.getNextPlayer().equals(Player.NONE);
    assert updateStatus.getGameStatus().equals(GameStatus.WIN);
    assert updateStatus.getWinner().equals(Player.X);
    String[] board = updateStatus.getBoard();
    assert board[1] == null;
    assert board[5] == null;
  }

  @Test
  void board_evaluates_draw() {
    /* |---|---|---|  |---|---|---|
       | 0 | 1 | 2 |  | X | X | O |
       |-----------|  |-----------|
       | 3 | 4 | 5 |  | O | O | X |
       |-----------|  |-----------|
       | 6 | 7 | 8 |  | X | O | X |
       |---|---|---|  |---|---|---|*/
    gameBoard.updateBoard(Player.X, 0);
    gameBoard.updateBoard(Player.O, 4);
    gameBoard.updateBoard(Player.X, 1);
    gameBoard.updateBoard(Player.O, 2);
    gameBoard.updateBoard(Player.X, 6);
    gameBoard.updateBoard(Player.O, 3);
    gameBoard.updateBoard(Player.X, 5);
    gameBoard.updateBoard(Player.O, 7);
    BoardState updateStatus = gameBoard.updateBoard(Player.X, 8);

    assert updateStatus.isSuccess();
    assert updateStatus.getNextPlayer().equals(Player.NONE);
    assert updateStatus.getGameStatus().equals(GameStatus.DRAW);
    assert updateStatus.getWinner().equals(Player.NONE);
    for (String x : updateStatus.getBoard()) {
      assert x != null;
    }
  }

  @Test
  void board_update_fails_after_draw() {
    /* |---|---|---|  |---|---|---|
       | 0 | 1 | 2 |  | X | X | O |
       |-----------|  |-----------|
       | 3 | 4 | 5 |  | O | O | X |
       |-----------|  |-----------|
       | 6 | 7 | 8 |  | X | 7 |   |
       |---|---|---|  |---|---|---|*/
    gameBoard.updateBoard(Player.X, 0);
    gameBoard.updateBoard(Player.O, 4);
    gameBoard.updateBoard(Player.X, 1);
    gameBoard.updateBoard(Player.O, 2);
    gameBoard.updateBoard(Player.X, 6);
    gameBoard.updateBoard(Player.O, 3);
    gameBoard.updateBoard(Player.X, 5);
    gameBoard.updateBoard(Player.O, 7);
    gameBoard.updateBoard(Player.X, 8);

    BoardState updateStatus = gameBoard.updateBoard(Player.O, 1);
    assert !updateStatus.isSuccess();
    assert updateStatus.getGameStatus().equals(GameStatus.DRAW);
    assert updateStatus.getNextPlayer().equals(Player.NONE);
    assert updateStatus.getWinner().equals(Player.NONE);
    for (String x : updateStatus.getBoard()) {
      assert x != null;
    }

  }

}