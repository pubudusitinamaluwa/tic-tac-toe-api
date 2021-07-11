package com.example.tictactoe.services;

import com.example.tictactoe.game.BoardState;
import com.example.tictactoe.session.GameSession;

public interface SessionManager {
  /**
   * Validate if a session is active by id
   *
   * @param sessionId Session id
   * @return Returns true if the session is valid and active, else returns false
   */
  boolean isActiveSession(String sessionId);

  /**
   * Create a new game session
   *
   * @return A new game session is returned
   * @throws IllegalStateException Thrown if max active sessions (defaults to game.session.max) has reached.
   */
  GameSession createSession() throws IllegalStateException;

  /**
   * Get a game session by id
   *
   * @param sessionId Session id
   * @return Returns game session if a valid active session exists. Else returns null.
   */
  GameSession getSession(String sessionId);
}
