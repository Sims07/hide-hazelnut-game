package com.ippon.kata.hide.hazelnut;

import com.ippon.kata.hide.hazelnut.application.domain.BoardChangedEvent;
import com.ippon.kata.hide.hazelnut.application.domain.BoardLevels;
import com.ippon.kata.hide.hazelnut.application.domain.EventPublisher;
import com.ippon.kata.hide.hazelnut.application.domain.ListBoardLevels;
import com.ippon.kata.hide.hazelnut.application.domain.MoveSquirrel;
import com.ippon.kata.hide.hazelnut.application.domain.StartLevel;
import com.ippon.kata.hide.hazelnut.application.usecase.ListBoardLevelsUseCase;
import com.ippon.kata.hide.hazelnut.application.usecase.MoveSquirrelUseCase;
import com.ippon.kata.hide.hazelnut.application.usecase.StartLevelUseCase;
import com.ippon.kata.hide.hazelnut.infrastructure.secondary.file.FileBoardLevels;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

  @Bean
  ListBoardLevelsUseCase listBoardLevelsUseCase(BoardLevels boardLevels) {
    return new ListBoardLevels(boardLevels);
  }

  @Bean
  BoardLevels boardLevels(@Value("${levels.file.path}") String path) {
    return new FileBoardLevels(path);
  }

  @Bean
  StartLevelUseCase startLevelUseCase(
      EventPublisher<BoardChangedEvent> boardPublisher, BoardLevels boardLevels) {

    return new StartLevel(boardPublisher, boardLevels);
  }

  @Bean
  MoveSquirrelUseCase moveSquirrelUseCase(EventPublisher<BoardChangedEvent> boardPublisher) {
    return new MoveSquirrel(boardPublisher);
  }
}
