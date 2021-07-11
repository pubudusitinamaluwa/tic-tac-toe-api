package com.example.tictactoe.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.tictactoe.game.BoardState;
import com.example.tictactoe.services.SessionManager;
import com.example.tictactoe.session.GameSession;

@Service
public class SessionManagerImpl implements SessionManager {
  @Value("${game.sessions.max}")
  private int maxSessions;

  @Override
  public boolean isValidSession(String sessionId) {
    return false;
  }

  @Override
  public BoardState createSession() throws IllegalStateException {
    return null;
  }

  @Override
  public GameSession getSession(String sessionId) {
    return null;
  }
}
