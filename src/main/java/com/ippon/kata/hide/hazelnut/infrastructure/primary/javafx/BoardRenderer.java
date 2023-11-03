package com.ippon.kata.hide.hazelnut.infrastructure.primary.javafx;

import com.ippon.kata.hide.hazelnut.application.domain.Board;
import com.ippon.kata.hide.hazelnut.application.domain.Slot;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BoardRenderer extends AbstractRenderer<Board> {

  @Override
  public void render(GraphicsContext graphicsContext, Board board) {
    board
        .slotPositions()
        .forEach(
            (position, slot) -> {
              if (!slot.isOccupied()) {
                renderEmptySlot(graphicsContext, Color.GRAY, position.x(), position.y());
              } else {
                renderPieceParcel(
                    graphicsContext, slot, position.x() * BLOCK_SIZE, position.y() * BLOCK_SIZE);
              }
            });
  }

  private void renderPieceParcel(GraphicsContext graphicsContext, Slot slot, int y, int x) {}

  private static void renderEmptySlot(GraphicsContext graphicsContext, Color color, int x, int y) {
    graphicsContext.setFill(color);
    graphicsContext.fillRect(
        (double) x * BLOCK_SIZE, (double) y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
    graphicsContext.strokeRect(
        (double) x * BLOCK_SIZE, (double) y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
  }
}
