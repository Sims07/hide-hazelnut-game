package com.ippon.kata.hide.hazelnut.application.domain;

public record PieceParcel(Position position, ParcelType type) {

  public PieceParcel translate(Position squirrelInitPosition) {
    return new PieceParcel(position.translate(squirrelInitPosition), type);
  }
}
