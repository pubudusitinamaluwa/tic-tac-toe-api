package com.example.tictactoe.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.tictactoe.game.BoardState;
import com.example.tictactoe.game.GameStatus;
import com.example.tictactoe.game.Player;
import com.example.tictactoe.services.impl.GameManagerImpl;
import com.example.tictactoe.services.impl.SessionManagerImpl;
import com.example.tictactoe.session.GameSession;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class GameManagerTest {
  @Value("${game.sessions.max}")
  private int maxSessions;
  @Value("${game.sessions.timeoutSeconds}")
  private int timeoutSeconds;
  private GameManager gameManager;
  private String initSessionId;

  @BeforeEach
  void init() {
    SessionManager sessionManager = new SessionManagerImpl(maxSessions, timeoutSeconds);
    GameSession gameSession = sessionManager.createSession();
    initSessionId = gameSession.getSessionId();
    gameManager = new GameManagerImpl(sessionManager);
  }

  @Test
  void strike_on_invalid_session_throws_exception() {
    assertThrows(IllegalStateException.class, () -> {
      gameManager.strike("invalid_session_id", Player.X, 0);
    });
  }

  @Test
  void strike_on_valid_session_returns_updated_board_state() {
    BoardState boardState = gameManager.strike(initSessionId, Player.X, 0);
    assert boardState.isSuccess();
    assert boardState.getNextPlayer().equals(Player.O);
    assert boardState.getWinner().equals(Player.NONE);
    assert boardState.getGameStatus().equals(GameStatus.ACTIVE);
    assert "X".equals(boardState.getBoard()[0]);
  }
}