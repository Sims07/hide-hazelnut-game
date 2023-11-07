package com.ippon.kata.hide.hazelnut.application.domain;

import com.ippon.kata.hide.hazelnut.application.usecase.ListBoardLevelsUseCase;
import java.util.List;

public class ListBoardLevels implements ListBoardLevelsUseCase {
  private final BoardLevels boardLevels;

  public ListBoardLevels(BoardLevels boardLevels) {
    this.boardLevels = boardLevels;
  }

  @Override
  public List<BoardLevel> list() {
    return boardLevels.list();
  }
}
