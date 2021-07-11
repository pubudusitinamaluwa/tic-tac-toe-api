package com.example.tictactoe.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.tictactoe.game.BoardState;
import com.example.tictactoe.game.Player;
import com.example.tictactoe.services.impl.SessionManagerImpl;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class SessionManagerTest {
  @Value("${game.sessions.max}")
  private int maxSessions;

  SessionManager sessionManager;

  @BeforeEach
  void init() {
    sessionManager = new SessionManagerImpl();
  }

  @Test
  void create_session_returns_new_board() {
    BoardState boardState = sessionManager.createSession();
    assert boardState != null;
    assert boardState.getSessionId() != null;
    assert boardState.getNextPlayer().equals(Player.X);
    assert boardState.getWinner().equals(Player.NONE);
    String[] board = boardState.getBoard();
    for (String x : board) {
      assert x == null;
    }
  }

  @Test
  void invalid_session_id_returns_false() {
    assert !sessionManager.isValidSession("non_existing_id");
  }

  @Test
  void valid_session_id_returns_true() {
    BoardState boardState = sessionManager.createSession();
    assert sessionManager.isValidSession(boardState.getSessionId());
  }

  @Test
  void too_many_new_sessions_throws_exception() {
    assertThrows(IllegalStateException.class, () -> {
      int i = 0;
      while (i < maxSessions + 1) {
        sessionManager.createSession();
        i++;
      }
    });
  }
}