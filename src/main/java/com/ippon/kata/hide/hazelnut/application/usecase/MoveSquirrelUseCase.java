package com.ippon.kata.hide.hazelnut.application.usecase;

import com.ippon.kata.hide.hazelnut.application.domain.Board;
import com.ippon.kata.hide.hazelnut.application.domain.BoardChangedEvent;
import com.ippon.kata.hide.hazelnut.application.domain.Orientation;
import com.ippon.kata.hide.hazelnut.application.domain.Piece;

public interface MoveSquirrelUseCase {

  BoardChangedEvent move(Board board, Orientation orientation, Piece piece);
}
