package com.ippon.kata.hide.hazelnut.infrastructure.primary.javafx;

import com.ippon.kata.hide.hazelnut.application.domain.Board;
import com.ippon.kata.hide.hazelnut.application.domain.Flower;
import com.ippon.kata.hide.hazelnut.application.domain.PieceParcel;
import com.ippon.kata.hide.hazelnut.application.domain.Position;
import com.ippon.kata.hide.hazelnut.application.domain.Slot;
import com.ippon.kata.hide.hazelnut.application.domain.Squirrel;
import java.util.Arrays;
import java.util.Optional;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BoardRenderer extends AbstractRenderer<Board> {

  private static final Logger LOGGER = LoggerFactory.getLogger(BoardRenderer.class);
  public static final int ARC_WIDTH = 50;
  public static final Color PICE_GREEN_COLOR = Color.rgb(184, 204, 52);
  public static final Color FLOWER_RED_COLOR = Color.rgb(250, 139, 118);
  public static final Color HAZELNUT_STROKE_COLOR = Color.rgb(150, 95, 28);
  public static final Color HAZELNUT_FILL_COLOR = Color.rgb(250, 179, 84);

  @Override
  public void render(GraphicsContext graphicsContext, Board board) {
    board
        .slotPositions()
        .forEach(
            (position, slot) -> {
              renderEmptySlot(
                  graphicsContext, Color.rgb(190, 144, 104), slot, position.x(), position.y());
            });
    Arrays.stream(com.ippon.kata.hide.hazelnut.application.domain.Color.values())
        .map(board::piece)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .forEach(
            piece -> {
              switch (piece) {
                case Squirrel squirrel:
                  {
                    renderSquirrel(graphicsContext, squirrel);
                  }
                  break;
                case Flower flower:
                  {
                    renderFlower(graphicsContext, flower);
                  }
                  break;
                default:
                  throw new IllegalStateException("Unexpected value: " + piece);
              }
            });
  }

  private void renderFlower(GraphicsContext graphicsContext, Flower flower) {
    double x = flower.pieceParcels().get(0).position().x();
    double y = flower.pieceParcels().get(0).position().y();
    graphicsContext.setFill(PICE_GREEN_COLOR);
    graphicsContext.fillRoundRect(
        x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE, ARC_WIDTH, ARC_WIDTH);
    graphicsContext.setFill(Color.BLACK);
    graphicsContext.strokeRect(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
    // flower

    drawPetals(
        graphicsContext, x * BLOCK_SIZE + BLOCK_SIZE / 2, y * BLOCK_SIZE + BLOCK_SIZE / 2, 60);
    drawCircle(graphicsContext, x, y, FLOWER_RED_COLOR, toColor(flower.color()));
  }

  private void drawCircle(
      GraphicsContext graphicsContext,
      double x,
      double y,
      Color strokeColor,
      Color circleFillColor) {
    double delta = PADDING * 2;
    final double upperLeftX = x * BLOCK_SIZE + delta / 2;
    final double upperLeftY = y * BLOCK_SIZE + delta / 2;
    double width = BLOCK_SIZE - delta;
    graphicsContext.setStroke(strokeColor);
    graphicsContext.setLineWidth(30.0);
    graphicsContext.strokeOval(upperLeftX, upperLeftY, width, width);
    graphicsContext.setStroke(Color.BLACK);
    graphicsContext.setFill(circleFillColor);
    graphicsContext.fillOval(upperLeftX, upperLeftY, width, width);
    graphicsContext.setLineWidth(1.0);
  }

  private void drawPetals(GraphicsContext gc, double centerX, double centerY, double petalRadius) {
    gc.setFill(Color.RED);
    double angleIncrement = 360.0 / 6; // 6 pétales

    for (int i = 0; i < 6; i++) {
      double angle = i * angleIncrement;
      double petalX = centerX + petalRadius * Math.cos(Math.toRadians(angle));
      double petalY = centerY + petalRadius * Math.sin(Math.toRadians(angle));
      gc.fillOval(petalX - petalRadius / 2, petalY - petalRadius / 2, petalRadius, petalRadius);
    }
  }

  private void renderSquirrel(GraphicsContext graphicsContext, Squirrel squirrel) {
    if (squirrelSizeTwo(squirrel)) {
      drawContourSquirrel(graphicsContext, squirrel);
      squirrel
          .pieceParcels()
          .forEach(
              parcel -> {
                drawParcelDetail(graphicsContext, parcel, squirrel.color());
              });
    } else {
      squirrel
          .pieceParcels()
          .forEach(
              pieceParcel -> {
                renderPieceParcel(
                    graphicsContext, pieceParcel.position().x(), pieceParcel.position().y());
              });
    }
  }

  private void drawParcelDetail(
      GraphicsContext graphicsContext,
      PieceParcel parcel,
      com.ippon.kata.hide.hazelnut.application.domain.Color color) {
    switch (parcel.type()) {
      case HAZELNUT_SLOT -> drawHazelnutParcel(graphicsContext, parcel.position(), color);
      case SQUIRREL -> drawSquirrel(graphicsContext, parcel.position(), color);
    }
  }

  private void drawSquirrel(
      GraphicsContext graphicsContext,
      Position position,
      com.ippon.kata.hide.hazelnut.application.domain.Color color) {
    SquirrelRenderer.drawSquirrel(
        graphicsContext,
        position.x() * BLOCK_SIZE + (double) BLOCK_SIZE / 6,
        position.y() * BLOCK_SIZE + (double) BLOCK_SIZE / 6,
        120,
        toColor(color));
  }

  private void drawHazelnutParcel(
      GraphicsContext graphicsContext,
      Position position,
      com.ippon.kata.hide.hazelnut.application.domain.Color color) {
    drawCircle(
        graphicsContext, position.x(), position.y(), HAZELNUT_STROKE_COLOR, HAZELNUT_FILL_COLOR);
  }

  private static void drawContourSquirrel(GraphicsContext graphicsContext, Squirrel squirrel) {
    int minX =
        squirrel.pieceParcels().stream()
            .map(PieceParcel::position)
            .map(Position::x)
            .min(Integer::compareTo)
            .orElseThrow();
    int maxX =
        squirrel.pieceParcels().stream()
            .map(PieceParcel::position)
            .map(Position::x)
            .max(Integer::compareTo)
            .orElseThrow();
    int minY =
        squirrel.pieceParcels().stream()
            .map(PieceParcel::position)
            .map(Position::y)
            .min(Integer::compareTo)
            .orElseThrow();
    graphicsContext.setFill(PICE_GREEN_COLOR);
    if (horizontal(maxX, minX)) {
      final int width = BLOCK_SIZE * 2;
      drawPiece(graphicsContext, minX, minY, width, BLOCK_SIZE);
    } else {
      final int height = BLOCK_SIZE * 2;
      drawPiece(graphicsContext, minX, minY, BLOCK_SIZE, height);
    }
  }

  private static boolean squirrelSizeTwo(Squirrel squirrel) {
    return squirrel.pieceParcels().size() == 2;
  }

  private static boolean horizontal(int maxX, int minX) {
    return maxX - minX == 1;
  }

  private static void drawPiece(
      GraphicsContext graphicsContext, int minX, int minY, int width, int blockSize) {
    graphicsContext.fillRoundRect(
        minX * BLOCK_SIZE, minY * BLOCK_SIZE, width, blockSize, ARC_WIDTH, ARC_WIDTH);
    graphicsContext.setFill(Color.BLACK);
    graphicsContext.strokeRect(minX * BLOCK_SIZE, minY * BLOCK_SIZE, width, blockSize);
  }

  private void renderPieceParcel(GraphicsContext graphicsContext, int x, int y) {
    graphicsContext.setFill(PICE_GREEN_COLOR);
    graphicsContext.fillRect(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
    graphicsContext.setFill(Color.BLACK);
    graphicsContext.strokeRect(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
  }

  private static Color toColor(com.ippon.kata.hide.hazelnut.application.domain.Color color) {
    return switch (color) {
      case BLACK -> Color.BLACK;
      case ORANGE -> Color.ORANGE;
      case RED -> Color.rgb(208, 15, 17);
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
