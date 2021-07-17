package com.example.tictactoe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tictactoe.game.Player;
import com.example.tictactoe.response.ResponseMessage;
import com.example.tictactoe.services.GameManager;

@Controller
@CrossOrigin
@RequestMapping(value = "/tictoctoe/game")
public class GameController {
  @Autowired
  GameManager gameManager;

  @GetMapping("/strike")
  public ResponseEntity<Object> strike(@RequestParam String sessionId, @RequestParam Player striker,
      @RequestParam int index) {
    try {
      return new ResponseEntity<>(gameManager.strike(sessionId, striker, index), HttpStatus.OK);
    } catch (IllegalStateException ex) {
      return new ResponseEntity<>(
          new ResponseMessage(HttpStatus.CONFLICT, ex.getMessage()), HttpStatus.CONFLICT
      );
    }
  }
}
