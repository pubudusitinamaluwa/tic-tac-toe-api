package com.example.tictactoe.services.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.tictactoe.game.GameBoard;
import com.example.tictactoe.game.GameStatus;
import com.example.tictactoe.services.SessionManager;
import com.example.tictactoe.session.GameSession;

@Service
public class SessionManagerImpl implements SessionManager {
  private int maxSessions;
  private int timeoutSeconds;
  private final Map<String, GameSession> sessionMap = new HashMap<>();

  public SessionManagerImpl(@Value("${game.sessions.max}") int maxSessions,
      @Value("${game.sessions.timeoutSeconds}") int timeoutSeconds) {
    this.maxSessions = maxSessions;
    this.timeoutSeconds = timeoutSeconds;
  }

  @Override
  public boolean isActiveSession(String sessionId) {
    if (sessionMap.containsKey(sessionId)) {
      GameSession session = sessionMap.get(sessionId);
      // Check if session timed out
      long timeoutMillis = timeoutSeconds * 1000L;
      if (session.isValid(timeoutMillis)) {
        GameStatus gameStatus = session.getGameBoard().getGameStatus();
        // Check if game is still active
        return gameStatus.equals(GameStatus.ACTIVE);
      }
    }
    return false;
  }

  @Override
  public GameSession createSession() throws IllegalStateException {
    int activeSessionCount = sessionMap.size();
    if (activeSessionCount <= maxSessions) {
      String sessionId = UUID.randomUUID().toString();
      GameBoard gameBoard = new GameBoard(sessionId);
      GameSession gameSession = new GameSession(sessionId, System.currentTimeMillis(), gameBoard);
      sessionMap.put(sessionId, gameSession);
      return gameSession;
    } else {
      // If max sessions is reached, check for timed out or concluded sessions and remove
      for (String sessionId : sessionMap.keySet()) {
        // If inactive session found, remove it and re-attempt session creation
        if (!isActiveSession(sessionId)) {
          sessionMap.remove(sessionId);
          return createSession();
        }
      }
    }
    throw new IllegalStateException("Max active sessions (" + maxSessions
        + ") reached. Unable to create new sessions. Increase game.sessions.max to increase max sessions.");
  }

  @Override
  public GameSession getSession(String sessionId) {
    return sessionMap.get(sessionId);
  }
}
