package com.example.tictactoe.services.impl;

import org.springframework.stereotype.Service;

import com.example.tictactoe.game.BoardState;
import com.example.tictactoe.game.Player;
import com.example.tictactoe.services.GameManager;
import com.example.tictactoe.services.SessionManager;

@Service
public class GameManagerImpl implements GameManager {
  private SessionManager sessionManager;

  public GameManagerImpl(SessionManager sessionManager) {
    this.sessionManager = sessionManager;
  }

  @Override
  public BoardState strike(String sessionId, Player striker, int index)
      throws IllegalStateException {
    return null;
  }
}
