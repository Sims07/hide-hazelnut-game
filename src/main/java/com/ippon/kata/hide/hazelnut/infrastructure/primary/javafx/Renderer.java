package com.ippon.kata.hide.hazelnut.infrastructure.primary.javafx;

import javafx.scene.canvas.GraphicsContext;

public interface Renderer<E> {
  int TEXT_HEIGHT = 20;
  int TEXT_WIDTH = 300;
  double HALF = 2.0;
  int LEVEL_Y_OFFSET = 60;
  int WIDTH = 10;
  int HEIGHT = 22;
  int BLOCK_SIZE = 180;

  double PADDING = 20.0;

  void render(GraphicsContext graphicsContext, E event);

  void erase(GraphicsContext graphicsContext);
}
