package com.ippon.kata.hide.hazelnut.application.domain;

import static com.ippon.kata.hide.hazelnut.application.domain.Pieces.GREY_SQUIRREL;
import static com.ippon.kata.hide.hazelnut.application.domain.Pieces.ORANGE_SQUIRREL;
import static org.assertj.core.api.BDDAssertions.then;

import java.util.List;
import org.junit.jupiter.api.Test;

class BoardLevelTest {

  @Test
  void startLevelOne() {
    final BoardLevel boardLevel = givenLevelOne();

    Board board = boardLevel.start();

    List<Position> orangeSquirrelPosition = List.of(new Position(1, 1), new Position(2, 1));
    orangeSquirrelPosition.forEach(position -> thenPositionOccupied(position, board));
    List<Position> greySquirrelPosition = List.of(new Position(2, 2), new Position(2, 3));
    greySquirrelPosition.forEach(position -> thenPositionOccupied(position, board));
    Position flowerPosition = new Position(1, 2);
    thenPositionOccupied(flowerPosition, board);
  }

  @Test
  void playLevelOne() {
    final BoardLevel boardLevel = givenLevelOne();
    Board board = boardLevel.start();

    board = board.move(ORANGE_SQUIRREL.piece(), Orientation.LEFT);
    board = board.move(GREY_SQUIRREL.piece(), Orientation.UP);
    board = board.move(GREY_SQUIRREL.piece(), Orientation.UP);

    then(board.slot(new Position(0, 1)).hazelnutInTheHole()).isTrue();
    then(board.slot(new Position(2, 0)).hazelnutInTheHole()).isTrue();
    then(board.allSquirrelHasReleasedTheirHazelnuts()).isTrue();
  }

  private static BoardLevel givenLevelOne() {
    return new BoardLevel(
        1,
        new GameConfiguration(
            List.of(
                new PieceConfiguration(Pieces.FLOWER.piece(), new Position(1, 2), Orientation.NONE),
                new PieceConfiguration(
                    ORANGE_SQUIRREL.piece(), new Position(1, 1), Orientation.LEFT),
                new PieceConfiguration(
                    Pieces.GREY_SQUIRREL.piece(), new Position(2, 2), Orientation.UP))));
  }

  private static void thenPositionOccupied(Position position, Board board) {
    final Slot orangeSquirrelSlot = board.slot(position);
    then(orangeSquirrelSlot.isOccupied()).isTrue();
  }
}
