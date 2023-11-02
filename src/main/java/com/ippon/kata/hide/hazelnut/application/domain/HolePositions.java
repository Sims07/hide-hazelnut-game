package com.ippon.kata.hide.hazelnut.application.domain;

public enum HolePositions {
  FIRST(new Position(0, 1)),
  SECOND(new Position(2, 0)),
  THIRD(new Position(1, 2)),
  FOURTH(new Position(3, 3));

  private final Position position;

  HolePositions(Position position) {
    this.position = position;
  }

  public Position position() {
    return position;
  }
}
