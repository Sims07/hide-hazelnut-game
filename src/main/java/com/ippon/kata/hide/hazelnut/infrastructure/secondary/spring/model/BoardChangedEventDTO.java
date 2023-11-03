package com.ippon.kata.hide.hazelnut.infrastructure.secondary.spring.model;

import com.ippon.kata.hide.hazelnut.application.domain.Board;
import org.springframework.context.ApplicationEvent;

public class BoardChangedEventDTO extends ApplicationEvent {
  private final Board board;

  public BoardChangedEventDTO(Object source, Board board) {
    super(source);
    this.board = board;
  }

  public Board board() {
    return board;
  }
}
