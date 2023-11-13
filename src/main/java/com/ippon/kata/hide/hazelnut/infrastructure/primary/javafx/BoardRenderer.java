package com.ippon.kata.hide.hazelnut.infrastructure.primary.javafx;

import com.ippon.kata.hide.hazelnut.application.domain.Board;
import com.ippon.kata.hide.hazelnut.application.domain.Flower;
import com.ippon.kata.hide.hazelnut.application.domain.PieceParcel;
import com.ippon.kata.hide.hazelnut.application.domain.Position;
import com.ippon.kata.hide.hazelnut.application.domain.Slot;
import com.ippon.kata.hide.hazelnut.application.domain.Squirrel;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BoardRenderer extends AbstractRenderer<Board> {

  public static final int ARC_WIDTH = 50;
  public static final Color PICE_GREEN_COLOR = Color.rgb(184, 204, 52);
  public static final Color FLOWER_RED_COLOR = Color.rgb(250, 139, 118);
  public static final Color HAZELNUT_STROKE_COLOR = Color.rgb(150, 95, 28);
  public static final Color HAZELNUT_FILL_COLOR = Color.rgb(250, 179, 84);
  public static final double HAZELNUT_LINE_WIDTH = 30.0;
  public static final int PETAL_RADIUS = 60;
  public static final int LINE_WIDTH = 1;
  public static final Color EMPTY_BOARD_COLOR = Color.rgb(190, 144, 104);
  public static final int HAZELNUT_INTERNAL_LINE_WIDTH = 10;

  @Override
  public void render(GraphicsContext graphicsContext, Board board) {
    renderEmptyBoard(graphicsContext, board);
    renderPieces(graphicsContext, board);
    renderHazelnut(graphicsContext, board);
  }

  private static void renderHazelnut(GraphicsContext graphicsContext, Board board) {
    board
        .slotPositions()
        .forEach(
            (position, slot) -> {
              if (slot.hazelnutInTheHole() && slot.piece().isEmpty()) {
                drawCircle(
                    graphicsContext,
                    position.x(),
                    position.y(),
                    HAZELNUT_STROKE_COLOR,
                    HAZELNUT_FILL_COLOR,
                    LINE_WIDTH);
              }
            });
  }

  private void renderPieces(GraphicsContext graphicsContext, Board board) {
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

  private static void renderEmptyBoard(GraphicsContext graphicsContext, Board board) {
    board
        .slotPositions()
        .forEach(
            (position, slot) ->
                renderEmptySlot(
                    graphicsContext, EMPTY_BOARD_COLOR, slot, position.x(), position.y()));
  }

  public static void renderFlower(GraphicsContext graphicsContext, Flower flower) {
    double x = flower.pieceParcels().get(0).position().x();
    double y = flower.pieceParcels().get(0).position().y();
    graphicsContext.setFill(PICE_GREEN_COLOR);
    graphicsContext.fillRoundRect(
        x * BLOCK_SIZE + 2,
        y * BLOCK_SIZE + 2,
        BLOCK_SIZE - 2,
        BLOCK_SIZE - 2,
        ARC_WIDTH,
        ARC_WIDTH);
    graphicsContext.setFill(Color.BLACK);
    // flower

    drawPetals(
        graphicsContext,
        upperLeft(x, (double) BLOCK_SIZE),
        upperLeft(y, (double) BLOCK_SIZE),
        toColor(flower.color()));
    drawCircle(
        graphicsContext,
        x,
        y,
        innerFlowerColor(flower.color()),
        toColor(flower.color()),
        HAZELNUT_LINE_WIDTH);
  }

  private static Color innerFlowerColor(
      com.ippon.kata.hide.hazelnut.application.domain.Color color) {
    return switch (color) {
      case BLACK -> Color.LIGHTBLUE;
      case RED -> FLOWER_RED_COLOR;
      case YELLOW -> Color.WHITE;
      default -> throw new IllegalStateException("Unexpected value: " + color);
    };
  }

  private static void drawCircle(
      GraphicsContext graphicsContext,
      double x,
      double y,
      Color strokeColor,
      Color circleFillColor,
      double lineWitdth) {
    double delta = PADDING * 2;
    final double upperLeftX = upperLeft(x, delta);
    final double upperLeftY = upperLeft(y, delta);
    double width = BLOCK_SIZE - delta;
    graphicsContext.save();

    graphicsContext.setStroke(strokeColor);
    final double lineWidthOriginal = graphicsContext.getLineWidth();
    graphicsContext.setLineWidth(lineWitdth);
    graphicsContext.strokeOval(upperLeftX, upperLeftY, width, width);
    graphicsContext.setStroke(Color.BLACK);
    graphicsContext.setFill(circleFillColor);
    graphicsContext.fillOval(upperLeftX, upperLeftY, width, width);
    graphicsContext.setLineWidth(lineWidthOriginal);

    graphicsContext.restore();
  }

  private static double upperLeft(double x, double delta) {
    return x * BLOCK_SIZE + delta / 2;
  }

  private static void drawPetals(GraphicsContext gc, double centerX, double centerY, Color color) {
    gc.setFill(color);
    double angleIncrement = 360.0 / 6; // 6 pétales

    for (int i = 0; i < 6; i++) {
      double angle = i * angleIncrement;
      double petalX =
          centerX + (double) BoardRenderer.PETAL_RADIUS * Math.cos(Math.toRadians(angle));
      double petalY =
          centerY + (double) BoardRenderer.PETAL_RADIUS * Math.sin(Math.toRadians(angle));
      gc.fillOval(
          petalX - (double) BoardRenderer.PETAL_RADIUS / 2,
          petalY - (double) BoardRenderer.PETAL_RADIUS / 2,
          BoardRenderer.PETAL_RADIUS,
          BoardRenderer.PETAL_RADIUS);
    }
  }

  private void renderSquirrel(GraphicsContext graphicsContext, Squirrel squirrel) {
    if (squirrelSizeTwo(squirrel)) {
      drawContourSquirrel(graphicsContext, squirrel);
      squirrel
          .pieceParcels()
          .forEach(parcel -> drawParcelDetail(graphicsContext, parcel, squirrel));
    } else {
      drawContourLSquirrel(graphicsContext, squirrel);
      squirrel
          .pieceParcels()
          .forEach(parcel -> drawParcelDetail(graphicsContext, parcel, squirrel));
    }
  }

  private void drawContourLSquirrel(GraphicsContext graphicsContext, Squirrel squirrel) {
    graphicsContext.setFill(PICE_GREEN_COLOR);
    switch (squirrel.color()) {
      case BLACK -> drawContourBlackL(graphicsContext, squirrel);
      case YELLOW -> drawContourYellowL(graphicsContext, squirrel);
    }
  }

  private static Position minY(Squirrel squirrel) {
    return squirrel.pieceParcels().stream()
        .map(PieceParcel::position)
        .min(Comparator.comparing(Position::y))
        .orElseThrow();
  }

  private static Position minX(Squirrel squirrel) {
    return squirrel.pieceParcels().stream()
        .map(PieceParcel::position)
        .min(Comparator.comparing(Position::x))
        .orElseThrow();
  }

  private void drawContourYellowL(GraphicsContext graphicsContext, Squirrel squirrel) {
    final Position minX = minX(squirrel);
    final Position minY = minY(squirrel);
    switch (squirrel.orientation()) {
      case UP, LEFT -> {
        drawBottomHorizontalSquirrel(graphicsContext, minX.x(), minX.y() + 1);
        drawVerticalSquirrel(graphicsContext, minX.x(), minX.y());
      }
      case DOWN -> {
        drawBottomHorizontalSquirrel(graphicsContext, minX.x(), minX.y());
        drawVerticalSquirrel(graphicsContext, minX.x() + 1, minX.y() - 1);
      }
      case RIGHT -> {
        drawBottomHorizontalSquirrel(graphicsContext, minX.x(), minY.y());
        drawVerticalSquirrel(graphicsContext, minX.x() + 1, minY.y());
      }
      case NONE -> {}
    }
  }

  private void drawContourBlackL(GraphicsContext graphicsContext, Squirrel squirrel) {
    final Position minX = minX(squirrel);
    final Position minY = minY(squirrel);
    switch (squirrel.orientation()) {
      case UP, LEFT -> {
        drawBottomHorizontalSquirrel(graphicsContext, minX.x(), minX.y());
        drawVerticalSquirrel(graphicsContext, minX.x() + 1, minX.y() - 1);
      }
      case DOWN -> {
        drawBottomHorizontalSquirrel(graphicsContext, minX.x(), minX.y());
        drawVerticalSquirrel(graphicsContext, minX.x() + 1, minX.y());
      }
      case RIGHT -> {
        drawBottomHorizontalSquirrel(graphicsContext, minX.x(), minY.y());
        drawVerticalSquirrel(graphicsContext, minX.x(), minY.y());
      }
      case NONE -> {}
    }
  }

  private void drawVerticalSquirrel(GraphicsContext graphicsContext, int x, int y) {
    drawPiece(graphicsContext, x, y, BLOCK_SIZE, BLOCK_SIZE * 2);
  }

  private void drawBottomHorizontalSquirrel(GraphicsContext graphicsContext, int x, int y) {
    drawPiece(graphicsContext, x, y, BLOCK_SIZE * 2, BLOCK_SIZE);
  }

  private void drawParcelDetail(
      GraphicsContext graphicsContext, PieceParcel parcel, Squirrel squirrel) {
    switch (parcel.type()) {
      case HAZELNUT_SLOT -> drawHazelnutParcel(graphicsContext, parcel.position(), squirrel);
      case SQUIRREL -> drawSquirrel(graphicsContext, parcel.position(), squirrel);
      case FLOWER -> drawFlower(graphicsContext, parcel, squirrel);
    }
  }

  private void drawFlower(GraphicsContext graphicsContext, PieceParcel parcel, Squirrel squirrel) {
    renderFlower(graphicsContext, new Flower(squirrel.color(), List.of(parcel)));
  }

  private void drawSquirrel(GraphicsContext graphicsContext, Position position, Squirrel squirrel) {
    SquirrelRenderer.drawSquirrel(
        graphicsContext,
        position.x() * BLOCK_SIZE + (double) BLOCK_SIZE / 6,
        position.y() * BLOCK_SIZE + (double) BLOCK_SIZE / 6,
        120,
        toColor(squirrel.color()));
  }

  private void drawHazelnutParcel(
      GraphicsContext graphicsContext, Position position, Squirrel squirrel) {
    if (squirrel.hasHazelnut()) {
      drawCircle(
          graphicsContext,
          position.x(),
          position.y(),
          HAZELNUT_STROKE_COLOR,
          HAZELNUT_FILL_COLOR,
          HAZELNUT_LINE_WIDTH);
    } else {
      drawCircle(
          graphicsContext,
          position.x(),
          position.y(),
          HAZELNUT_STROKE_COLOR,
          Color.TRANSPARENT,
          BoardRenderer.HAZELNUT_INTERNAL_LINE_WIDTH);
    }
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
    return maxX - minX == LINE_WIDTH;
  }

  private static void drawPiece(
      GraphicsContext graphicsContext, int x, int y, int width, int height) {
    graphicsContext.save();
    graphicsContext.fillRoundRect(
        x * BLOCK_SIZE + 2, y * BLOCK_SIZE + 2, width - 2, height - 2, ARC_WIDTH, ARC_WIDTH);
    graphicsContext.setFill(Color.BLACK);
    graphicsContext.restore();
  }

  private static Color toColor(com.ippon.kata.hide.hazelnut.application.domain.Color color) {
    return switch (color) {
      case BLACK -> Color.rgb(100, 98, 97);
      case ORANGE -> Color.rgb(227, 91, 35);
      case RED -> Color.rgb(208, 15, 17);
      case GREY -> Color.LIGHTGRAY;
      case YELLOW -> Color.YELLOW;
    };
  }

  private static void renderEmptySlot(
      GraphicsContext graphicsContext, Color color, Slot slot, int x, int y) {
    renderParcel(graphicsContext, color, x, y);
    if (slot.hasHole()) {
      final double upperLeftX = upperLeft((double) x, PADDING);
      final double upperLeftY = upperLeft((double) y, PADDING);
      double width = BLOCK_SIZE - PADDING;
      graphicsContext.setFill(Color.BLACK);
      graphicsContext.strokeOval(upperLeftX, upperLeftY, width, width);
      if (slot.hazelnutInTheHole()) {
        graphicsContext.setFill(HAZELNUT_FILL_COLOR);
      } else {
        graphicsContext.setFill(Color.GRAY);
      }

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
