package com.ippon.kata.hide.hazelnut.application.domain;

import java.util.List;

public record Squirrel(Color color, List<PieceParcel> pieceParcels, boolean hasHazelnut)
    implements Piece {

  public Squirrel moveAt(Position squirrelInitPosition) {
    return new Squirrel(
        color,
        pieceParcels.stream()
            .map(squirrelParcels -> squirrelParcels.translate(squirrelInitPosition))
            .toList(),
        hasHazelnut);
  }

  public Squirrel useOrientation(Orientation orientation) {
    List<PieceParcel> pieceParcels =
        switch (color) {
          case ORANGE, GREY -> iOrientation(orientation);
          case RED -> pieceParcels();
          default -> throw new IllegalStateException("Unexpected value: " + color);
        };
    return new Squirrel(color, pieceParcels, hasHazelnut);
  }

  private List<PieceParcel> iOrientation(Orientation orientation) {
    return switch (orientation) {
      case UP -> List.of(
          new PieceParcel(new Position(0, 0), ParcelType.HAZELNUT_SLOT),
          new PieceParcel(new Position(0, 1), ParcelType.SQUIRREL));
      case LEFT -> List.of(
          new PieceParcel(new Position(0, 0), ParcelType.HAZELNUT_SLOT),
          new PieceParcel(new Position(1, 0), ParcelType.SQUIRREL));
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
        hasHazelnut);
  }

  private static Position translatePosition(Orientation orientation) {

    return switch (orientation) {
      case LEFT -> new Position(-1, 0);
      case UP -> new Position(0, -1);
      case NONE -> new Position(0, 0);
    };
  }

  public Squirrel releaseHazelnut() {
    return new Squirrel(color, pieceParcels, false);
  }
}
