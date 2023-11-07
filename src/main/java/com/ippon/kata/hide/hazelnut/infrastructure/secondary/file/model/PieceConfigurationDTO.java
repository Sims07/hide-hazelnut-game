package com.ippon.kata.hide.hazelnut.infrastructure.secondary.file.model;

import com.ippon.kata.hide.hazelnut.application.domain.Orientation;
import com.ippon.kata.hide.hazelnut.application.domain.Piece;
import com.ippon.kata.hide.hazelnut.application.domain.PieceConfiguration;
import com.ippon.kata.hide.hazelnut.application.domain.Pieces;

public record PieceConfigurationDTO(String type, String orientation, PositionDTO position) {

  public PieceConfiguration toPieceConfiguration() {
    return new PieceConfiguration(
        piece(type), position.toPosition(), Orientation.valueOf(orientation));
  }

  private Piece piece(String type) {
    return Pieces.valueOf(type).piece();
  }
}
