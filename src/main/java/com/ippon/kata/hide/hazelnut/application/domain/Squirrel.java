package com.ippon.kata.hide.hazelnut.application.domain;

import java.util.List;

public record Squirrel(
    Color color, List<PieceParcel> pieceParcels, boolean hasHazelnut, Orientation orientation)
    implements Piece {

  public Squirrel moveAt(Position squirrelInitPosition) {
    return new Squirrel(
        color,
        pieceParcels.stream()
            .map(squirrelParcels -> squirrelParcels.translate(squirrelInitPosition))
            .toList(),
        hasHazelnut,
        this.orientation);
  }

  public Squirrel useOrientation(Orientation orientation) {
    List<PieceParcel> pieceParcels =
        switch (color) {
          case ORANGE, GREY -> iOrientation(orientation);
          case RED -> pieceParcels();
          case YELLOW, BLACK -> lOrientation(orientation);
        };
    return new Squirrel(color, pieceParcels, hasHazelnut, orientation);
  }

  private List<PieceParcel> lOrientation(Orientation orientation) {
    return switch (color) {
      case BLACK -> blackOrientation(orientation);
      case YELLOW -> yellowOrientation(orientation);
      default -> throw new IllegalStateException("Unexpected value: " + color);
    };
  }

  private List<PieceParcel> blackOrientation(Orientation orientation) {
    return switch (orientation) {
      case UP -> List.of(
          new PieceParcel(new Position(0, 0), ParcelType.HAZELNUT_SLOT),
          new PieceParcel(new Position(0, 1), ParcelType.SQUIRREL),
          new PieceParcel(new Position(1, 1), ParcelType.FLOWER));
      case LEFT -> List.of(
          new PieceParcel(new Position(1, 0), ParcelType.FLOWER),
          new PieceParcel(new Position(0, 1), ParcelType.HAZELNUT_SLOT),
          new PieceParcel(new Position(1, 1), ParcelType.SQUIRREL));
      case DOWN -> List.of(
          new PieceParcel(new Position(0, 0), ParcelType.FLOWER),
          new PieceParcel(new Position(1, 1), ParcelType.HAZELNUT_SLOT),
          new PieceParcel(new Position(1, 0), ParcelType.SQUIRREL));
      case RIGHT -> List.of(
          new PieceParcel(new Position(1, 0), ParcelType.HAZELNUT_SLOT),
          new PieceParcel(new Position(0, 0), ParcelType.SQUIRREL),
          new PieceParcel(new Position(0, 1), ParcelType.FLOWER));
      case NONE -> List.of();
    };
  }

  private List<PieceParcel> yellowOrientation(Orientation orientation) {
    return switch (orientation) {
      case UP -> List.of(
          new PieceParcel(new Position(0, 0), ParcelType.HAZELNUT_SLOT),
          new PieceParcel(new Position(0, 1), ParcelType.SQUIRREL),
          new PieceParcel(new Position(1, 0), ParcelType.FLOWER));
      case LEFT -> List.of(
          new PieceParcel(new Position(0, 0), ParcelType.FLOWER),
          new PieceParcel(new Position(0, 1), ParcelType.HAZELNUT_SLOT),
          new PieceParcel(new Position(1, 1), ParcelType.SQUIRREL));
      case DOWN -> List.of(
          new PieceParcel(new Position(0, 1), ParcelType.FLOWER),
          new PieceParcel(new Position(1, 1), ParcelType.HAZELNUT_SLOT),
          new PieceParcel(new Position(1, 0), ParcelType.SQUIRREL));
      case RIGHT -> List.of(
          new PieceParcel(new Position(1, 0), ParcelType.HAZELNUT_SLOT),
          new PieceParcel(new Position(0, 0), ParcelType.SQUIRREL),
          new PieceParcel(new Position(1, 1), ParcelType.FLOWER));
      case NONE -> List.of();
    };
  }

  private List<PieceParcel> iOrientation(Orientation orientation) {
    return switch (orientation) {
      case UP -> List.of(
          new PieceParcel(new Position(0, 0), ParcelType.HAZELNUT_SLOT),
          new PieceParcel(new Position(0, 1), ParcelType.SQUIRREL));
      case LEFT -> List.of(
          new PieceParcel(new Position(0, 0), ParcelType.HAZELNUT_SLOT),
          new PieceParcel(new Position(1, 0), ParcelType.SQUIRREL));
      case DOWN -> List.of(
          new PieceParcel(new Position(0, 0), ParcelType.SQUIRREL),
          new PieceParcel(new Position(0, 1), ParcelType.HAZELNUT_SLOT));
      case RIGHT -> List.of(
          new PieceParcel(new Position(0, 0), ParcelType.SQUIRREL),
          new PieceParcel(new Position(1, 0), ParcelType.HAZELNUT_SLOT));
      case NONE -> List.of();
    };
  }

  public Squirrel move(Orientation orientation) {
    return new Squirrel(
        color,
        pieceParcels.stream()
            .map(
                parcel ->
                    new PieceParcel(
                        parcel.position().translate(translatePosition(orientation)), parcel.type()))
            .toList(),
        hasHazelnut,
        this.orientation);
  }

  private static Position translatePosition(Orientation orientation) {

    return switch (orientation) {
      case LEFT -> new Position(-1, 0);
      case UP -> new Position(0, -1);
      case DOWN -> new Position(0, 1);
      case RIGHT -> new Position(1, 0);
      case NONE -> new Position(0, 0);
    };
  }

  public Squirrel releaseHazelnut() {
    return new Squirrel(color, pieceParcels, false, this.orientation);
  }
}
