package com.ippon.kata.hide.hazelnut.application.domain;

import java.util.List;

public interface Piece {

  List<PieceParcel> pieceParcels();

  <T extends Piece> T moveAt(Position position);

  Color color();
}
