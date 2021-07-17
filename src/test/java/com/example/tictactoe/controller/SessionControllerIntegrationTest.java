package com.example.tictactoe.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.example.tictactoe.game.GameBoard;
import com.example.tictactoe.services.SessionManager;
import com.example.tictactoe.session.GameSession;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration(classes = SessionController.class)
@WebMvcTest()
class SessionControllerIntegrationTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private SessionManager sessionManager;

  @Test
  void create_session_return_session() throws Exception {
    GameSession gameSession = new GameSession("1", System.currentTimeMillis(), new GameBoard());
    given(sessionManager.createSession()).willReturn(gameSession);

    mvc.perform(get("/tictactoe/session/create")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();
  }

  @Test
  void create_session_over_max_limit_throws_error() throws Exception {
    given(sessionManager.createSession()).willThrow(IllegalStateException.class);

    mvc.perform(get("/tictactoe/session/create")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isConflict())
        .andReturn();
  }

  @Test
  void get_session_return_active_session() throws Exception {
    GameSession gameSession = new GameSession("1", System.currentTimeMillis(), new GameBoard());
    given(sessionManager.getSession("1")).willReturn(gameSession);

    mvc.perform(get("/tictactoe/session/get/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();
  }

  @Test
  void get_invalid_session_returns_not_found() throws Exception {
    GameSession gameSession = new GameSession("1", System.currentTimeMillis(), new GameBoard());
    given(sessionManager.getSession("1")).willReturn(gameSession);

    mvc.perform(get("/session/get/2")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andReturn();
  }
}