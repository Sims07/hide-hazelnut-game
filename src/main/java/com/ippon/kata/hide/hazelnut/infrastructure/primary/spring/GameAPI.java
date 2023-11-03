package com.ippon.kata.hide.hazelnut.infrastructure.primary.spring;

import com.ippon.kata.hide.hazelnut.application.domain.Color;
import com.ippon.kata.hide.hazelnut.application.domain.Orientation;
import com.ippon.kata.hide.hazelnut.application.usecase.StartLevelUseCase;
import org.springframework.stereotype.Component;

@Component
public class GameAPI {
  private final StartLevelUseCase startLevelUseCase;

  public GameAPI(StartLevelUseCase startLevelUseCase) {
    this.startLevelUseCase = startLevelUseCase;
  }

  public void move(Color squirrelColorToMove, Orientation orientation) {}

  public void startLevel(int level) {
    startLevelUseCase.startLevel(level);
  }
}
