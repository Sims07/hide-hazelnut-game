package com.ippon.kata.hide.hazelnut.infrastructure.primary.spring;

import com.ippon.kata.hide.hazelnut.application.domain.Board;
import com.ippon.kata.hide.hazelnut.application.domain.Color;
import com.ippon.kata.hide.hazelnut.application.domain.Orientation;
import com.ippon.kata.hide.hazelnut.application.usecase.MoveSquirrelUseCase;
import com.ippon.kata.hide.hazelnut.application.usecase.StartLevelUseCase;
import org.springframework.stereotype.Component;

@Component
public class GameAPI {
  private final StartLevelUseCase startLevelUseCase;
  private final MoveSquirrelUseCase moveSquirrelUseCase;

  public GameAPI(StartLevelUseCase startLevelUseCase, MoveSquirrelUseCase moveSquirrelUseCase) {
    this.startLevelUseCase = startLevelUseCase;
    this.moveSquirrelUseCase = moveSquirrelUseCase;
  }

  public void move(Board board, Color squirrelColorToMove, Orientation orientation) {
    moveSquirrelUseCase.move(board, orientation, board.piece(squirrelColorToMove).orElseThrow());
  }

  public void startLevel(int level) {
    startLevelUseCase.startLevel(level);
  }
}
