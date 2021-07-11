package com.example.tictactoe.game;

import static com.example.tictactoe.game.BoardStatus.ACTIVE;
import static com.example.tictactoe.game.Turn.O;
import static com.example.tictactoe.game.Turn.X;

/**
 * @author Pubudu
 * <p>
 * GameBoar manages the state of the game for a single session.
 */
public class GameBoard {
  private final String sessionId;
  private String[] board = new String[9];
  private BoardStatus boardStatus = ACTIVE;
  private Turn turn = X;

  public GameBoard(String sessionId) {
    this.sessionId = sessionId;
  }

  /**
   * Update and get board status
   *
   * @param turn  Player turn
   * @param index Index to insert
   * @return Board status
   */
  public BoardUpdateStatus updateBoard(Turn turn, int index) {
    if (isValidTurn(turn)) {
      board[index] = turn.name();
      updateTurn();
      return new BoardUpdateStatus(true, this.boardStatus, this.turn, this.board);
    } else {
      return new BoardUpdateStatus(false, this.boardStatus, this.turn, this.board);
    }
  }

  /**
   * Validate current turn
   *
   * @param turn Player taking the turn
   * @return Returns true if player taking the turn is equal to expected current turn, else false
   */
  private boolean isValidTurn(Turn turn) {
    return this.turn.equals(turn);
  }

  /**
   * Updates/Switches turn. X->O or O->X depending on the current turn value
   */
  private void updateTurn() {
    if (turn.equals(X)) {
      this.turn = O;
    } else {
      this.turn = X;
    }
  }
}
