package com.example.tictactoe.game;

import static com.example.tictactoe.game.BoardStatus.ACTIVE;
import static com.example.tictactoe.game.Player.NONE;
import static com.example.tictactoe.game.Player.O;
import static com.example.tictactoe.game.Player.X;

/**
 * @author Pubudu
 * <p>
 * GameBoar manages the state of the game for a single session.
 */
public class GameBoard {
  private final String sessionId;
  private String[] board = new String[9];
  private BoardStatus boardStatus = ACTIVE;
  private Player currentPlayer = X;

  public GameBoard(String sessionId) {
    this.sessionId = sessionId;
  }

  /**
   * Update and get board status
   *
   * @param player Player turn
   * @param index  Index to insert
   * @return Board status
   */
  public BoardUpdateStatus updateBoard(Player player, int index) {
    if (isValidTurn(player)) {
      board[index] = player.name();
      updateTurn();
      return new BoardUpdateStatus(true, boardStatus, currentPlayer, board, NONE);
    } else {
      return new BoardUpdateStatus(false, boardStatus, currentPlayer, board, NONE);
    }
  }

  /**
   * Validate current turn
   *
   * @param player Player taking the turn
   * @return Returns true if player taking the turn is equal to expected current turn, else false
   */
  private boolean isValidTurn(Player player) {
    return this.currentPlayer.equals(player);
  }

  /**
   * Updates/Switches turn. X->O or O->X depending on the current turn value
   */
  private void updateTurn() {
    if (currentPlayer.equals(X)) {
      this.currentPlayer = O;
    } else if(currentPlayer.equals(O)) {
      this.currentPlayer = X;
    }
  }
}
