package com.ippon.kata.hide.hazelnut.application.domain;

import java.util.List;

public enum Pieces {
  FLOWER(new Flower(Color.RED, List.of(new PieceParcel(new Position(0, 0), ParcelType.FLOWER)))),
  ORANGE_SQUIRREL(
      new Squirrel(
          Color.ORANGE,
          List.of(
              new PieceParcel(new Position(0, 0), ParcelType.HAZELNUT_SLOT),
              new PieceParcel(new Position(1, 0), ParcelType.SQUIRREL)),
          true)),
  YELLOW_SQUIRREL(
      new Squirrel(
          Color.YELLOW,
          List.of(
              new PieceParcel(new Position(0, 0), ParcelType.FLOWER),
              new PieceParcel(new Position(1, 1), ParcelType.SQUIRREL),
              new PieceParcel(new Position(0, 1), ParcelType.HAZELNUT_SLOT)),
          true)),
  BLACK_SQUIRREL(
      new Squirrel(
          Color.BLACK,
          List.of(
              new PieceParcel(new Position(0, 0), ParcelType.FLOWER),
              new PieceParcel(new Position(1, 1), ParcelType.SQUIRREL),
              new PieceParcel(new Position(0, 1), ParcelType.HAZELNUT_SLOT)),
          true)),
  GREY_SQUIRREL(
      new Squirrel(
          Color.GREY,
          List.of(
              new PieceParcel(new Position(0, 0), ParcelType.HAZELNUT_SLOT),
              new PieceParcel(new Position(1, 0), ParcelType.SQUIRREL)),
          true));

  private final Piece piece;

  Pieces(Piece piece) {
    this.piece = piece;
  }

  public Piece piece() {
    return piece;
  }
}
