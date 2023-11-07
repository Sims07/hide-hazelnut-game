package com.ippon.kata.hide.hazelnut.infrastructure.primary.spring;

import com.ippon.kata.hide.hazelnut.application.domain.Board;
import com.ippon.kata.hide.hazelnut.application.domain.BoardLevel;
import com.ippon.kata.hide.hazelnut.application.domain.Color;
import com.ippon.kata.hide.hazelnut.application.domain.Orientation;
import com.ippon.kata.hide.hazelnut.application.usecase.ListBoardLevelsUseCase;
import com.ippon.kata.hide.hazelnut.application.usecase.MoveSquirrelUseCase;
import com.ippon.kata.hide.hazelnut.application.usecase.StartLevelUseCase;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GameAPI {
  private final StartLevelUseCase startLevelUseCase;
  private final MoveSquirrelUseCase moveSquirrelUseCase;
  private final ListBoardLevelsUseCase listBoardLevelsUseCase;

  public GameAPI(
      StartLevelUseCase startLevelUseCase,
      MoveSquirrelUseCase moveSquirrelUseCase,
      ListBoardLevelsUseCase listBoardLevelsUseCase) {
    this.startLevelUseCase = startLevelUseCase;
    this.moveSquirrelUseCase = moveSquirrelUseCase;
    this.listBoardLevelsUseCase = listBoardLevelsUseCase;
  }

  public void move(Board board, Color squirrelColorToMove, Orientation orientation) {
    moveSquirrelUseCase.move(board, orientation, board.piece(squirrelColorToMove).orElseThrow());
  }

  public void startLevel(int level) {
    startLevelUseCase.startLevel(level);
  }

  public List<BoardLevel> listGameLevels() {
    return listBoardLevelsUseCase.list();
  }
}
