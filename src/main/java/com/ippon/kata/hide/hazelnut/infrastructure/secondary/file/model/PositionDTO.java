package com.ippon.kata.hide.hazelnut.infrastructure.secondary.file.model;

import com.ippon.kata.hide.hazelnut.application.domain.Position;

public record PositionDTO(int x, int y) {

  public Position toPosition() {
    return new Position(x, y);
  }
}
