package com.example.tictactoe.services.impl;

import org.springframework.stereotype.Service;

import com.example.tictactoe.game.BoardState;
import com.example.tictactoe.game.GameBoard;
import com.example.tictactoe.game.Player;
import com.example.tictactoe.services.GameManager;
import com.example.tictactoe.services.SessionManager;
import com.example.tictactoe.session.GameSession;

@Service
public class GameManagerImpl implements GameManager {
  private final SessionManager sessionManager;

  public GameManagerImpl(SessionManager sessionManager) {
    this.sessionManager = sessionManager;
  }

  @Override
  public BoardState strike(String sessionId, Player striker, int index)
      throws IllegalStateException {
    if (sessionManager.isActiveSession(sessionId)) {
      GameSession session = sessionManager.getSession(sessionId);
      GameBoard gameBoard = session.getGameBoard();
      return gameBoard.updateBoard(striker, index);
    }
    throw new IllegalStateException("Session does not exists. id: " + sessionId + ".");
  }
}
