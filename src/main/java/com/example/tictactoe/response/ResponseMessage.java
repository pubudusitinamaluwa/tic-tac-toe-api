package com.example.tictactoe.response;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseMessage {
  private final HttpStatus status;
  private final String message;
}
