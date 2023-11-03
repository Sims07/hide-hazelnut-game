package com.ippon.kata.hide.hazelnut.infrastructure.primary.javafx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class AbstractRenderer<E> implements Renderer<E> {
  protected static int xOrigin() {
    return boardWidth() + boardWidth() / 2 - BLOCK_SIZE;
  }

  protected static int boardWidth() {
    return WIDTH * BLOCK_SIZE;
  }

  protected static int boardHeight() {
    return HEIGHT * BLOCK_SIZE;
  }

  @Override
  public void erase(GraphicsContext graphicsContext) {}

  static void setDefaultFillColor(GraphicsContext graphicsContext) {
    graphicsContext.setFill(Color.WHITE);
  }

  protected static void renderTextAt(
      GraphicsContext graphicsContext, double x, double y, String text, Color color) {
    setDefaultFillColor(graphicsContext);
    graphicsContext.fillRect(x, y - TEXT_HEIGHT, TEXT_WIDTH, TEXT_HEIGHT);
    graphicsContext.setFill(color);
    graphicsContext.fillText(text, x, y);
  }
}
