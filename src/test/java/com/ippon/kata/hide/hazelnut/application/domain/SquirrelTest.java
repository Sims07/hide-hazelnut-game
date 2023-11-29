package com.ippon.kata.hide.hazelnut.application.domain;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class SquirrelTest {

  @ParameterizedTest
  @EnumSource(Pieces.class)
  void givenSquirrelWithTwoParcels_grey_shouldHaveTwoParcels(Pieces pieces) {
    switch (pieces.piece()) {
      case Squirrel squirrel:
        {
          if (squirrel.pieceParcels().size() == 2) {
            then(squirrel.pieceParcels().get(0).position()).isEqualTo(new Position(0, 0));
            then(squirrel.pieceParcels().get(0).type()).isEqualTo(ParcelType.HAZELNUT_SLOT);
            then(squirrel.pieceParcels().get(1).position()).isEqualTo(new Position(1, 0));
            then(squirrel.pieceParcels().get(1).type()).isEqualTo(ParcelType.SQUIRREL);
          } else {
            then(squirrel.pieceParcels().get(0).position()).isEqualTo(new Position(0, 0));
            then(squirrel.pieceParcels().get(0).type()).isEqualTo(ParcelType.FLOWER);
            then(squirrel.pieceParcels().get(1).position()).isEqualTo(new Position(1, 1));
            then(squirrel.pieceParcels().get(1).type()).isEqualTo(ParcelType.SQUIRREL);
            then(squirrel.pieceParcels().get(2).position()).isEqualTo(new Position(0, 1));
            then(squirrel.pieceParcels().get(2).type()).isEqualTo(ParcelType.HAZELNUT_SLOT);
          }
        }
        break;
      case Flower flower:
        {
          then(flower.pieceParcels().size()).isEqualTo(1);
          then(flower.pieceParcels().get(0).position()).isEqualTo(new Position(0, 0));
        }
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + pieces.piece());
    }
  }
}
