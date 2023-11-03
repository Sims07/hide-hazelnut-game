package com.ippon.kata.hide.hazelnut.application.domain;

import static com.ippon.kata.hide.hazelnut.application.domain.Pieces.ORANGE_SQUIRREL;

import com.ippon.kata.hide.hazelnut.application.BoardChangedEvent;
import com.ippon.kata.hide.hazelnut.application.usecase.StartLevelUseCase;
import java.util.List;

public class StartLevel implements StartLevelUseCase {
  private final EventPublisher<BoardChangedEvent> boardEventPublisher;

  public StartLevel(EventPublisher<BoardChangedEvent> boardEventPublisher) {
    this.boardEventPublisher = boardEventPublisher;
  }

  @Override
  public BoardChangedEvent startLevel(int level) {
    final BoardLevel boardLevel =
        new BoardLevel(
            1,
            Board.emptyBoard(),
            new GameConfiguration(
                List.of(
                    new PieceConfiguration(
                        Pieces.FLOWER.piece(), new Position(1, 2), Orientation.NONE),
                    new PieceConfiguration(
                        ORANGE_SQUIRREL.piece(), new Position(1, 1), Orientation.LEFT),
                    new PieceConfiguration(
                        Pieces.GREY_SQUIRREL.piece(), new Position(2, 2), Orientation.UP))));
    return boardEventPublisher.publish(new BoardChangedEvent(boardLevel.start()));
  }
}
