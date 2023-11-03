package com.ippon.kata.hide.hazelnut.application.usecase;

import com.ippon.kata.hide.hazelnut.application.BoardChangedEvent;

public interface StartLevelUseCase {
  BoardChangedEvent startLevel(int level);
}
