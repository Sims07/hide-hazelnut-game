package com.ippon.kata.hide.hazelnut.application.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
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
        values.put(position, new Slot(Optional.empty(), position, false));
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
    final Piece updatedPiece = piece.moveAt(initPosition);
    Map<Position, Slot> updatedSlotPositions = new HashMap<>(this.slotPositions);
    if (availableSlots(updatedPiece)) {
      updatedPiece
          .pieceParcels()
          .forEach(
              parcel -> {
                LOGGER.info("Use position {}", parcel.position());
                updatedSlotPositions.put(
                    parcel.position(),
                    new Slot(
                        Optional.of(updatedPiece),
                        parcel.position(),
                        updatedSlotPositions.get(parcel.position()).hazelnutInTheHole()));
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
        .filter(piece1 -> piece1.color().equals(piece.color()))
        .findAny();
  }

  public Board move(Piece piece, Orientation orientation) {
    Board updatedBoard = this;
    final Optional<Piece> squirrelPieceOpt = piece(piece);
    if (squirrelPieceOpt.isPresent()) {
      final Piece squirrelPiece = squirrelPieceOpt.get();
      if (squirrelPiece instanceof Squirrel squirrel) {
        updatedBoard = cleanPieceSlots(squirrel);
        Squirrel movedSquirrel = squirrel.move(orientation);
        if (updatedBoard.availableSlots(movedSquirrel)) {
          updatedBoard = updatedBoard.addPieceAtPosition(movedSquirrel);
        } else {
          updatedBoard = this;
        }
      }
    }
    return updatedBoard;
  }

  private Board cleanPieceSlots(Squirrel squirrel) {
    Map<Position, Slot> updatedSlotPositions = new HashMap<>(this.slotPositions);
    squirrel
        .pieceParcels()
        .forEach(
            parcel -> {
              LOGGER.info("Free position {}", parcel.position());
              updatedSlotPositions.put(
                  parcel.position(),
                  new Slot(
                      Optional.empty(),
                      parcel.position(),
                      updatedSlotPositions.get(parcel.position()).hazelnutInTheHole()));
            });
    return new Board(updatedSlotPositions);
  }

  private Board addPieceAtPosition(Squirrel squirrel) {
    Map<Position, Slot> updatedSlotPositions = new HashMap<>(this.slotPositions);
    if (isHazelnutReleasable(squirrel, updatedSlotPositions).isPresent()) {
      updatedSlotPositions = releaseHazelnut(squirrel, updatedSlotPositions);
    } else {
      updatedSlotPositions = moveSquirrel(squirrel, updatedSlotPositions);
    }
    return new Board(updatedSlotPositions);
  }

  private static Optional<PieceParcel> isHazelnutReleasable(
      Squirrel squirrel, Map<Position, Slot> updatedSlotPositions) {
    return squirrel.pieceParcels().stream()
        .filter(
            parcel -> {
              LOGGER.info(
                  "Use position {}, hazelNutInTheHole {}",
                  parcel.position(),
                  updatedSlotPositions.get(parcel.position()));
              return canReleaseHazelnut(
                  squirrel, parcel, updatedSlotPositions.get(parcel.position()));
            })
        .findAny();
  }

  private static Map<Position, Slot> moveSquirrel(
      Squirrel squirrel, Map<Position, Slot> updatedSlotPositions) {
    Map<Position, Slot> updatedSlotPositionsWithSquirrel = new HashMap<>(updatedSlotPositions);
    squirrel
        .pieceParcels()
        .forEach(
            parcel -> {
              LOGGER.info(
                  "Use position {}, hazelNutInTheHole {}",
                  parcel.position(),
                  updatedSlotPositionsWithSquirrel.get(parcel.position()));
              updatedSlotPositionsWithSquirrel.put(
                  parcel.position(),
                  new Slot(
                      Optional.of(squirrel),
                      parcel.position(),
                      updatedSlotPositionsWithSquirrel.get(parcel.position()).hazelnutInTheHole()));
            });
    return updatedSlotPositionsWithSquirrel;
  }

  private Map<Position, Slot> releaseHazelnut(
      Squirrel squirrelWithHazelnut, Map<Position, Slot> updatedSlotPositions) {
    Map<Position, Slot> updatedSlotPositionsWithSquirrel = new HashMap<>(updatedSlotPositions);
    Squirrel updatedSquirrel = squirrelWithHazelnut.releaseHazelnut();
    updatedSquirrel
        .pieceParcels()
        .forEach(
            squirrelWithHazelnutParcel -> {
              boolean hazelNutInTheHole =
                  squirrelWithHazelnutParcel.type() == ParcelType.HAZELNUT_SLOT;
              if (hazelNutInTheHole) {
                LOGGER.info(
                    "Squirrel {} put his hazelnut in {}",
                    squirrelWithHazelnut.color(),
                    squirrelWithHazelnutParcel.position());
              }
              updatedSlotPositionsWithSquirrel.put(
                  squirrelWithHazelnutParcel.position(),
                  new Slot(
                      Optional.of(updatedSquirrel),
                      squirrelWithHazelnutParcel.position(),
                      hazelNutInTheHole));
            });
    return updatedSlotPositionsWithSquirrel;
  }

  private static boolean canReleaseHazelnut(Squirrel squirrel, PieceParcel parcel, Slot slot) {
    return parcel.type() == ParcelType.HAZELNUT_SLOT
        && squirrel.hasHazelnut()
        && slot.hasHole()
        && !slot.hazelnutInTheHole();
  }

  public boolean allSquirrelHasReleasedTheirHazelnuts() {
    final Stream<Boolean> squirels =
        slotPositions.values().stream()
            .map(Slot::piece)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .filter(Squirrel.class::isInstance)
            .map(Squirrel.class::cast)
            .map(Squirrel::hasHazelnut);
    return squirels.reduce(true, (acc, hasHazelnut) -> acc && !hasHazelnut);
  }
}
