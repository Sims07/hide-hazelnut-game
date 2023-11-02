package com.ippon.kata.hide.hazelnut.application.domain;

import com.ippon.kata.hide.hazelnut.application.domain.asserts.Asserts;
import java.util.List;

public record Flower(Color color, List<PieceParcel> pieceParcels) implements Piece {

  public Flower {
    Asserts.withContext(getClass())
        .notEmpty(pieceParcels, "Parcels should not be null")
        .isEqualTo(pieceParcels.size(), 1, "Flower should have a size of one");
  }

  public Flower moveAt(Position position) {
    return new Flower(color, List.of(pieceParcels.get(0).translate(position)));
  }
}
