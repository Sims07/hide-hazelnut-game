package com.ippon.kata.hide.hazelnut.infrastructure.primary.javafx;

import com.ippon.kata.hide.hazelnut.application.domain.Board;
import com.ippon.kata.hide.hazelnut.application.domain.Slot;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BoardRenderer extends AbstractRenderer<Board> {
  private static final Logger LOGGER = LoggerFactory.getLogger(BoardRenderer.class);

  @Override
  public void render(GraphicsContext graphicsContext, Board board) {
    board
        .slotPositions()
        .forEach(
            (position, slot) -> {
              if (!slot.isOccupied()) {
                renderEmptySlot(graphicsContext, Color.MAROON, slot, position.x(), position.y());
              } else {
                LOGGER.info("Slot {} is occupied", slot);
                renderPieceParcel(graphicsContext, slot, position.x(), position.y());
              }
            });
  }

  private void renderPieceParcel(GraphicsContext graphicsContext, Slot slot, int x, int y) {
    renderParcel(graphicsContext, toColor(slot), x, y);
  }

  private static Color toColor(Slot slot) {
    return switch (slot.piece().orElseThrow().color()) {
      case BLACK -> Color.BLACK;
      case ORANGE -> Color.ORANGE;
      case RED -> Color.RED;
      case GREY -> Color.GRAY;
      case YELLOW -> Color.YELLOW;
    };
  }

  private static void renderEmptySlot(
      GraphicsContext graphicsContext, Color color, Slot slot, int x, int y) {
    renderParcel(graphicsContext, color, x, y);
    if (slot.hasHole()) {
      final double upperLeftX = (double) x * BLOCK_SIZE + PADDING / 2;
      final double upperLeftY = (double) y * BLOCK_SIZE + PADDING / 2;
      double width = BLOCK_SIZE - PADDING;
      graphicsContext.setFill(Color.BLACK);
      graphicsContext.strokeOval(upperLeftX, upperLeftY, width, width);
      graphicsContext.setFill(Color.GRAY);
      graphicsContext.fillOval(upperLeftX, upperLeftY, width, width);
    }
  }

  private static void renderParcel(
      GraphicsContext graphicsContext, Color color, double x, double y) {
    graphicsContext.setFill(color);
    graphicsContext.fillRect(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
    graphicsContext.strokeRect(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
  }
}
