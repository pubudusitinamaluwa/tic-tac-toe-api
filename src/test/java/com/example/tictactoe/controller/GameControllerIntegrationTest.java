package com.example.tictactoe.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.example.tictactoe.game.BoardState;
import com.example.tictactoe.game.GameStatus;
import com.example.tictactoe.game.Player;
import com.example.tictactoe.services.GameManager;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration(classes = GameController.class)
@WebMvcTest()
class GameControllerIntegrationTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private GameManager gameManager;

  @Test
  void valid_strike_returns_updated_board_state() throws Exception {
    String[] board = new String[9];
    board[0] = Player.X.name();
    BoardState boardState = new BoardState(true, GameStatus.ACTIVE, Player.O, board,
        Player.NONE);
    given(gameManager.strike("1", Player.X, 0)).willReturn(boardState);

    mvc.perform(get("/tictoctoe/game/strike")
        .queryParam("sessionId", "1")
        .queryParam("striker", Player.X.name())
        .queryParam("index", "0")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();
  }

  @Test
  void invalid_strike_returns_conflict() throws Exception {
    given(gameManager.strike("1", Player.O, 0)).willThrow(IllegalStateException.class);

    mvc.perform(get("/tictoctoe/game/strike")
        .queryParam("sessionId", "1")
        .queryParam("striker", Player.O.name())
        .queryParam("index", "0")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isConflict())
        .andReturn();
  }

}