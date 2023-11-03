package com.ippon.kata.hide.hazelnut;

import com.ippon.kata.hide.hazelnut.application.BoardChangedEvent;
import com.ippon.kata.hide.hazelnut.application.domain.EventPublisher;
import com.ippon.kata.hide.hazelnut.application.domain.MoveSquirrel;
import com.ippon.kata.hide.hazelnut.application.domain.StartLevel;
import com.ippon.kata.hide.hazelnut.application.usecase.MoveSquirrelUseCase;
import com.ippon.kata.hide.hazelnut.application.usecase.StartLevelUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

  @Bean
  StartLevelUseCase startLevelUseCase(EventPublisher<BoardChangedEvent> boardPublisher) {

    return new StartLevel(boardPublisher);
  }

  @Bean
  MoveSquirrelUseCase moveSquirrelUseCase(EventPublisher<BoardChangedEvent> boardPublisher) {
    return new MoveSquirrel(boardPublisher);
  }
}
