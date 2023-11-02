package com.ippon.kata.hide.hazelnut.application.domain;

import static com.ippon.kata.hide.hazelnut.application.domain.Pieces.ORANGE_SQUIRREL;
import static org.assertj.core.api.BDDAssertions.then;

import java.util.List;
import java.util.Optional;
import org.assertj.core.api.BDDAssertions;
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
  void givenMoveHazelnutUnderHole_move_shouldUpdateBoardWithAHoleWithHazelnul() {
    final BoardLevel boardLevel = givenLevelOne();
    Board board = boardLevel.start();

    final Optional<Piece> piece = board.piece(ORANGE_SQUIRREL.piece());

    BDDAssertions.then(piece).isPresent();
  }

  private static BoardLevel givenLevelOne() {
    return new BoardLevel(
        1,
        Board.emptyBoard(),
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
