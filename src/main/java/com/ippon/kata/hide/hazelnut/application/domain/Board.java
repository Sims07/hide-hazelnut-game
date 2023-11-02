package com.ippon.kata.hide.hazelnut.application.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record Board(Map<Position, Slot> slotPositions) {
  private static final Logger LOGGER = LoggerFactory.getLogger(Board.class);
  public static final int BOARD_SIZE = 4;

  public static Board emptyBoard() {
    Map<Position, Slot> values = new HashMap<>();
    for (int x = 0; x < BOARD_SIZE; x++) {
      for (int y = 0; y < BOARD_SIZE; y++) {
        final Position position = new Position(x, y);
        values.put(position, new Slot(Optional.empty(), position));
      }
    }
    return new Board(values);
  }

  public Slot slot(Position position) {
    return slotPositions.get(position);
  }

  public Board addSquirrelWithHazelnut(Squirrel squirrel, Position squirrelInitPosition) {
    return addPiece(squirrel, squirrelInitPosition);
  }

  public Board addFlower(Flower flower, Position position) {
    return addPiece(flower, position);
  }

  private Board addPiece(Piece piece, Position initPosition) {
    final Piece piecePosition = piece.moveAt(initPosition);
    Map<Position, Slot> updatedSlotPositions = new HashMap<>(this.slotPositions);
    if (availableSlots(piecePosition)) {
      piecePosition
          .pieceParcels()
          .forEach(
              parcel -> {
                LOGGER.info("Use position {}", parcel.position());
                updatedSlotPositions.put(
                    parcel.position(), new Slot(Optional.of(piece), parcel.position()));
              });
    } else {
      throw new IllegalArgumentException("Squirrel is already at this slot");
    }

    return new Board(updatedSlotPositions);
  }

  private boolean availableSlots(Piece piece) {
    return piece.pieceParcels().stream()
        .map(PieceParcel::position)
        .map(this::isSlotOccupiedAt)
        .reduce(true, (acc, slotOccupied) -> acc && !slotOccupied);
  }

  public boolean isSlotOccupiedAt(Position position) {
    return slotPositions.get(position).isOccupied();
  }

  public Optional<Piece> piece(Piece piece) {
    return slotPositions.values().stream()
        .map(Slot::piece)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .filter(piece1 -> piece1.equals(piece))
        .findAny();
  }
}
