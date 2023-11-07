package com.ippon.kata.hide.hazelnut.infrastructure.secondary.file;

import static org.assertj.core.api.BDDAssertions.*;

import com.ippon.kata.hide.hazelnut.application.domain.BoardLevel;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileBoardLevelsTest {
  private final FileBoardLevels fileBoardLevels = new FileBoardLevels("/levels.json");

  @Test
  void readBoardLevel() {
    fileBoardLevels.init();
    final List<BoardLevel> boardLevels = fileBoardLevels.list();

    then(boardLevels).isNotEmpty();
    then(boardLevels.size()).isEqualTo(1);
  }
}
