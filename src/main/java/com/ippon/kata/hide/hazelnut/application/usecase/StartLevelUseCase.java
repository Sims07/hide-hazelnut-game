package com.ippon.kata.hide.hazelnut.application.usecase;

import com.ippon.kata.hide.hazelnut.application.domain.BoardChangedEvent;

public interface StartLevelUseCase {
  BoardChangedEvent startLevel(int level);
}
