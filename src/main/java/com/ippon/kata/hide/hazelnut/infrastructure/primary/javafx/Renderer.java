package com.ippon.kata.hide.hazelnut.infrastructure.primary.javafx;

import javafx.scene.canvas.GraphicsContext;

public interface Renderer<E> {
  int TEXT_HEIGHT = 20;
  int TEXT_WIDTH = 300;
  int WIDTH = 4;
  int HEIGHT = 4;
  int BLOCK_SIZE = 180;

  double PADDING = 40.0;

  void render(GraphicsContext graphicsContext, E event);

  void erase(GraphicsContext graphicsContext);
}
