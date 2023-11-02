package com.ippon.kata.hide.hazelnut.application.domain;

import java.util.Arrays;
import java.util.Optional;

public record Slot(Optional<Piece> piece, Position position, boolean hazelnutInTheHole) {

  public boolean hasHole() {
    return Arrays.stream(HolePositions.values())
        .map(HolePositions::position)
        .anyMatch(holePosition -> holePosition.equals(position));
  }

  public boolean isOccupied() {
    return piece.isPresent();
  }
}
