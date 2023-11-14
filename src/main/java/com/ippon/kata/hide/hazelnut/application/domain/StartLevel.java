package com.ippon.kata.hide.hazelnut.application.domain;

import com.ippon.kata.hide.hazelnut.application.usecase.StartLevelUseCase;

public class StartLevel implements StartLevelUseCase {
  private final EventPublisher<BoardChangedEvent> boardEventPublisher;
  private final BoardLevels boardLevels;

  public StartLevel(
      EventPublisher<BoardChangedEvent> boardEventPublisher, BoardLevels boardLevels) {
    this.boardEventPublisher = boardEventPublisher;
    this.boardLevels = boardLevels;
  }

  @Override
  public BoardChangedEvent startLevel(int level) {
    final BoardLevel boardLevel = boardLevels.list().get(level - 1);
    return boardEventPublisher.publish(new BoardChangedEvent(boardLevel.start()));
  }
}
