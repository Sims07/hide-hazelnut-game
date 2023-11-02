package com.ippon.kata.hide.hazelnut.application.domain;

public record Position(int x, int y) {

  public Position translate(Position newOriginPosition) {
    return new Position(x + newOriginPosition.x(), y + newOriginPosition.y());
  }
}
