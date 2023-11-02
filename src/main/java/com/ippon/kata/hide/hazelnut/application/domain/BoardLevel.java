package com.ippon.kata.hide.hazelnut.application.domain;

import java.util.concurrent.atomic.AtomicReference;

public record BoardLevel(int level, Board board, GameConfiguration gameConfiguration) {

  public Board start() {
    AtomicReference<Board> boardLevel = new AtomicReference<>(Board.emptyBoard());
    gameConfiguration
        .pieceConfigurations()
        .forEach(
            pieceConfiguration ->
                boardLevel.set(applyPieceConfiguration(pieceConfiguration, boardLevel.get())));
    return boardLevel.get();
  }

  private static Board applyPieceConfiguration(
      PieceConfiguration pieceConfiguration, Board boardLevel) {
    Board newBoard;
    switch (pieceConfiguration.piece()) {
      case Squirrel squirrel:
        {
          newBoard =
              boardLevel.addSquirrelWithHazelnut(
                  squirrel.useOrientation(pieceConfiguration.orientation()),
                  pieceConfiguration.position());
        }
        break;
      case Flower flower:
        {
          newBoard = boardLevel.addFlower(flower, pieceConfiguration.position());
        }
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + pieceConfiguration.piece());
    }
    return newBoard;
  }
}
