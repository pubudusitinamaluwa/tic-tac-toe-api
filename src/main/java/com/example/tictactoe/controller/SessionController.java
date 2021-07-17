package com.example.tictactoe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.tictactoe.response.ResponseMessage;
import com.example.tictactoe.services.SessionManager;
import com.example.tictactoe.session.GameSession;

@Controller
@CrossOrigin
@RequestMapping(value = "/tictactoe/session")
public class SessionController {
  @Autowired
  SessionManager sessionManager;

  @GetMapping("/create")
  public ResponseEntity<Object> createSession() {
    try {
      return new ResponseEntity<>(sessionManager.createSession(), HttpStatus.OK);
    } catch (IllegalStateException ex) {
      return new ResponseEntity<>(
          new ResponseMessage(HttpStatus.CONFLICT, ex.getMessage()), HttpStatus.CONFLICT
      );
    }
  }

  @GetMapping("/get/{sessionId}")
  public ResponseEntity<Object> getSession(@PathVariable String sessionId) {
    GameSession session = sessionManager.getSession(sessionId);
    if (session != null) {
      return new ResponseEntity<>(session, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(
          new ResponseMessage(HttpStatus.NOT_FOUND, "Session id " + sessionId + " not found."),
          HttpStatus.NOT_FOUND
      );
    }
  }
}
