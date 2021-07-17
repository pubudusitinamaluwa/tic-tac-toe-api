package com.example.tictactoe.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.tictactoe.game.GameBoard;
import com.example.tictactoe.game.Player;
import com.example.tictactoe.services.impl.SessionManagerImpl;
import com.example.tictactoe.session.GameSession;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class SessionManagerTest {
  @Value("${game.sessions.max}")
  private int maxSessions;
  @Value("${game.sessions.timeoutSeconds}")
  private int timeoutSeconds;

  private SessionManager sessionManager;

  @BeforeEach
  void init() {
    sessionManager = new SessionManagerImpl(maxSessions, timeoutSeconds);
  }

  @Test
  void create_session_returns_new_game_session() {
    GameSession gameSession = sessionManager.createSession();
    assert gameSession.getSessionId() != null;
    assert gameSession.getLastActiveTs() <= System.currentTimeMillis();
    GameBoard gameBoard = gameSession.getGameBoard();
    assert gameBoard != null;
    assert gameBoard.getAllowedStriker().equals(Player.X);
    assert gameBoard.getWinner().equals(Player.NONE);
    String[] board = gameBoard.getBoard();
    for (String x : board) {
      assert x == null;
    }
  }

  @Test
  void invalid_session_id_returns_false() {
    assert !sessionManager.isActiveSession("non_existing_id");
  }

  @Test
  void valid_session_id_returns_true() {
    GameSession gameSession = sessionManager.createSession();
    assert sessionManager.isActiveSession(gameSession.getSessionId());
  }

  @Test
  void too_many_new_sessions_throws_exception() {
    assertThrows(IllegalStateException.class, () -> {
      int i = 0;
      while (i <= maxSessions) {
        sessionManager.createSession();
        i++;
      }
    });
  }

  @Test
  void invalid_sessions_cleared_to_create_new_sessions() throws InterruptedException {
    try {
      int i = 0;
      while (i <= maxSessions) {
        sessionManager.createSession();
        i++;
      }
      assert false;
    } catch (IllegalStateException ex) {
      Thread.sleep(2000);
      GameSession gameSession = sessionManager.createSession();
      assert gameSession.getSessionId() != null;
      assert gameSession.getLastActiveTs() <= System.currentTimeMillis();
      GameBoard gameBoard = gameSession.getGameBoard();
      assert gameBoard != null;
      assert gameBoard.getAllowedStriker().equals(Player.X);
      assert gameBoard.getWinner().equals(Player.NONE);
      String[] board = gameBoard.getBoard();
      for (String x : board) {
        assert x == null;
      }
    }
  }
}
