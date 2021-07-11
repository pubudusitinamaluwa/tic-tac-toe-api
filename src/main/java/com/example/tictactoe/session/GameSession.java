package com.example.tictactoe.session;

import com.example.tictactoe.game.GameBoard;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Reference for game session state
 */
@Getter
@AllArgsConstructor
public class GameSession {
  private final String sessionId;
  private long lastActiveTs;
  private GameBoard gameBoard;

  public boolean isValid(long timeoutMillis) {
    return timeoutMillis > (System.currentTimeMillis() - lastActiveTs);
  }

  public void updateLastActive() {
    lastActiveTs = System.currentTimeMillis();
  }
}
