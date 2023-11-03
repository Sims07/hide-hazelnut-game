package com.ippon.kata.hide.hazelnut.application.domain;

import com.ippon.kata.hide.hazelnut.application.BoardChangedEvent;
import com.ippon.kata.hide.hazelnut.application.usecase.MoveSquirrelUseCase;

public class MoveSquirrel implements MoveSquirrelUseCase {
  private final EventPublisher<BoardChangedEvent> boardEventPublisher;

  public MoveSquirrel(EventPublisher<BoardChangedEvent> boardEventPublisher) {
    this.boardEventPublisher = boardEventPublisher;
  }

  @Override
  public BoardChangedEvent move(Board board, Orientation orientation, Piece piece) {
    return boardEventPublisher.publish(new BoardChangedEvent(board.move(piece, orientation)));
  }
}
