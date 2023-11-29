package com.ippon.kata.hide.hazelnut.application.domain;

import com.ippon.kata.hide.hazelnut.application.usecase.SelectSquirrelUseCase;

public class SelectSquirrel implements SelectSquirrelUseCase {

  private final EventPublisher<BoardChangedEvent> boardEventPublisher;

  public SelectSquirrel(EventPublisher<BoardChangedEvent> boardEventPublisher) {
    this.boardEventPublisher = boardEventPublisher;
  }

  @Override
  public BoardChangedEvent selectSquirrel(Board board, Color squirrelColorSelected) {
    return boardEventPublisher.publish(
        new BoardChangedEvent(board.selectSquirrel(squirrelColorSelected)));
  }
}
