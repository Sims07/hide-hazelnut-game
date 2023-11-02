package com.ippon.kata.hide.hazelnut.application.domain;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

class BoardTest {

  @ParameterizedTest
  @EnumSource(HolePositions.class)
  void givenEmptyBoard_emptyPosition_shouldContainAHole(HolePositions holePositions) {
    Board emptyBoard = Board.emptyBoard();

    Slot slot = emptyBoard.slot(holePositions.position());

    then(slot.hasHole()).isTrue();
  }

  @ParameterizedTest
  @CsvSource({"0,0", "1,0", "3,0", "1,1", "2,1", "3,1", "0,2", "2,2", "3,2", "0,3", "1,3", "2,3"})
  void givenEmptyBoard_fullPosition_shouldNotContainAHole(int x, int y) {
    Board emptyBoard = Board.emptyBoard();

    Slot slot = emptyBoard.slot(new Position(x, y));

    then(slot.hasHole()).isFalse();
  }

  @Test
  void givenSquirrelAtSomePositions_addSquirrel_shouldOccupiedSquirrelPositions() {
    Board emptyBoard = Board.emptyBoard();
    final Squirrel squirrel = (Squirrel) Pieces.ORANGE_SQUIRREL.piece();
    final Position squirrelInitPosition = new Position(2, 0);

    Board nonEmptyBoard = emptyBoard.addSquirrelWithHazelnut(squirrel, squirrelInitPosition);

    then(nonEmptyBoard.isSlotOccupiedAt(new Position(2, 0))).isTrue();
    then(nonEmptyBoard.isSlotOccupiedAt(new Position(3, 0))).isTrue();
    then(nonEmptyBoard.isSlotOccupiedAt(new Position(1, 0))).isFalse();
  }

  @Test
  void givenAlreadyOccupiedSlot_addSquirrel_shouldThrowAnException() {
    Board emptyBoard = Board.emptyBoard();
    final Squirrel squirrel = (Squirrel) Pieces.ORANGE_SQUIRREL.piece();
    final Position squirrelInitPosition = new Position(2, 0);
    Board nonEmptyBoard = emptyBoard.addSquirrelWithHazelnut(squirrel, squirrelInitPosition);

    thenThrownBy(
            () ->
                nonEmptyBoard.addSquirrelWithHazelnut(
                    (Squirrel) Pieces.GREY_SQUIRREL.piece(), squirrelInitPosition))
        .isInstanceOf(IllegalArgumentException.class);
  }
}
