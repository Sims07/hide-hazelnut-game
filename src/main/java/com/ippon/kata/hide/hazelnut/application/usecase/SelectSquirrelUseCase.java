package com.ippon.kata.hide.hazelnut.application.usecase;

import com.ippon.kata.hide.hazelnut.application.domain.Board;
import com.ippon.kata.hide.hazelnut.application.domain.BoardChangedEvent;
import com.ippon.kata.hide.hazelnut.application.domain.Color;

public interface SelectSquirrelUseCase {

  BoardChangedEvent selectSquirrel(Board board, Color squirrelColorSelected);
}
