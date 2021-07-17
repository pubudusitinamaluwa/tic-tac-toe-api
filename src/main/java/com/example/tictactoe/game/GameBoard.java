package com.example.tictactoe.game;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import static com.example.tictactoe.game.GameStatus.ACTIVE;
import static com.example.tictactoe.game.GameStatus.DRAW;
import static com.example.tictactoe.game.GameStatus.WIN;
import static com.example.tictactoe.game.Player.NONE;
import static com.example.tictactoe.game.Player.O;
import static com.example.tictactoe.game.Player.X;

/**
 * @author Pubudu
 * <p>
 * GameBoar manages the state of the game for a single session.
 */
@Getter
public class GameBoard {
  private final String[] board = new String[9];
  private GameStatus gameStatus = ACTIVE;
  private Player nextStriker = X;
  private Player winner = NONE;

  /**
   * Update and get board status
   *
   * @param player Player taking the turn to strike
   * @param index  Index to insert
   * @return Board state
   */
  public BoardState updateBoard(Player player, int index) {
    if (isValidStriker(player) && gameStatus.equals(ACTIVE) && board[index] == null) {
      board[index] = player.name();
      evaluateBoard(player);
      updateStriker();
      return new BoardState(true, gameStatus, nextStriker, board, winner);
    } else {
      return new BoardState(false, gameStatus, nextStriker, board, winner);
    }
  }

  /**
   * Validate current striker
   *
   * @param player Player taking the turn
   * @return Returns true if player taking the turn is equal to allowed striker, else false
   */
  private boolean isValidStriker(Player player) {
    return this.nextStriker.equals(player);
  }

  /**
   * Updates/Switches turn. X->O or O->X depending on the current turn value
   */
  private void updateStriker() {
    if (nextStriker.equals(X)) {
      this.nextStriker = O;
    } else if (nextStriker.equals(O)) {
      this.nextStriker = X;
    }
  }

  /**
   * Evaluate the board and determine the status of the game.
   * If the striker has completed a line, game ends with striker as the winner.
   * If the board is full without any complete line, game end with draw status.
   *
   * @param striker Player taking the turn to strike
   */
  private void evaluateBoard(Player striker) {
    // TODO: Improvement - Evaluate if game ends with a draw before all values are filled
    List<String> lines = getLines();
    boolean isDraw = true;
    for (String line : lines) {
      if (line.matches(striker.name() + "{3}")) {
        gameStatus = WIN;
        winner = striker;
        nextStriker = NONE;
        return;
      } else if (line.contains("null")) {
        isDraw = false;
      }
    }
    if (isDraw) {
      gameStatus = DRAW;
      nextStriker = NONE;
    }
  }

  /**
   * Get all possible completion lines in the game board
   *
   * @return List of lines extracted from current game board
   */
  private List<String> getLines() {
    List<String> lines = new ArrayList<>();
    for (int i = 0; i < board.length; i += 3) {
      // Horizontal lines
      String horizontalLine = board[i] + board[i + 1] + board[i + 2];
      lines.add(horizontalLine);
      // Vertical Lines
      int k = i / 3;
      String verticalLine = board[k] + board[k + 3] + board[k + 6];
      lines.add(verticalLine);
    }
    // Diagonal Lines
    lines.add(board[0] + board[4] + board[8]);
    lines.add(board[2] + board[4] + board[6]);

    return lines;
  }
}
