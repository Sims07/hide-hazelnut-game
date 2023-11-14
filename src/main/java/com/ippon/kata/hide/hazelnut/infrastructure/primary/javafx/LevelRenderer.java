package com.ippon.kata.hide.hazelnut.infrastructure.primary.javafx;

import com.ippon.kata.hide.hazelnut.application.domain.BoardLevel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LevelRenderer extends AbstractRenderer<List<BoardLevel>> {

  public static final Color STARTER_BG_COLOR = Color.rgb(184, 204, 52);

  private record PositionOnScreen(double x, double y) {}

  private static final Logger LOGGER = LoggerFactory.getLogger(LevelRenderer.class);
  public static final int LEVEL_BY_LINES = 6;
  public static final Map<PositionOnScreen, Integer> positionLevelMap = new HashMap<>();
  public static final int LEVEL_PERCENTAGE_WIDTH = 3;
  public static final int LEVEL_WIDTH = BLOCK_SIZE / LEVEL_PERCENTAGE_WIDTH;
  public static final double TEXT_WIDTH_DIVISION = 2.2;
  public static final double TEXT_WIDTH_Y_DIVISION = 1.3;

  @Override
  public void render(GraphicsContext graphicsContext, List<BoardLevel> boardLevels) {
    boardLevels.forEach(boardLevel -> renderBoardLevel(graphicsContext, boardLevel.level()));
  }

  private void renderBoardLevel(GraphicsContext graphicsContext, int level) {
    renderLevelColor(graphicsContext, level, false);
  }

  private void renderLevelColor(GraphicsContext graphicsContext, int level, boolean highLight) {
    int levelToUse = (level - 1);
    final LevelColor levelColor = levelColor(level, highLight);
    graphicsContext.save();
    graphicsContext.setFill(levelColor.backgroundColor());

    final double xPosition = levelToUse % LEVEL_BY_LINES;
    final double x = boardWidth() + xPosition * LEVEL_WIDTH;
    final double y = (levelToUse / LEVEL_BY_LINES) * (double) LEVEL_WIDTH;
    final double finalX = x + PADDING;
    final double finalY = y + PADDING;
    positionLevelMap.put(new PositionOnScreen(finalX, finalY), level);
    LOGGER.info("level={},x={},y={}", levelToUse, xPosition, levelToUse / LEVEL_BY_LINES);
    graphicsContext.fillRect(finalX, finalY, LEVEL_WIDTH, LEVEL_WIDTH);
    graphicsContext.strokeRect(finalX, finalY, LEVEL_WIDTH, LEVEL_WIDTH);
    graphicsContext.setFill(levelColor.fontColor());
    graphicsContext.fillText(
        "" + level,
        x + LEVEL_WIDTH / TEXT_WIDTH_DIVISION + PADDING - 5,
        y + LEVEL_WIDTH / LevelRenderer.TEXT_WIDTH_Y_DIVISION + PADDING - 5);
    graphicsContext.restore();
  }

  record LevelColor(Paint backgroundColor, Paint fontColor) {}

  private LevelColor levelColor(int levelToUse, boolean highLight) {
    if (starterLevel(levelToUse)) {
      return new LevelColor(highLight ? Color.GREEN : STARTER_BG_COLOR, Color.WHITE);
    }
    if (juniorLevel(levelToUse)) {
      return new LevelColor(highLight ? Color.CORAL : Color.ORANGE, Color.WHITE);
    }
    if (expertLevel(levelToUse)) {
      return new LevelColor(highLight ? Color.CORAL : Color.RED, Color.WHITE);
    }
    return new LevelColor(Color.GREENYELLOW, highLight ? Color.BLACK : Color.WHITE);
  }

  private static boolean expertLevel(int levelToUse) {
    return levelToUse > 24 && levelToUse < 37;
  }

  private static boolean juniorLevel(int levelToUse) {
    return levelToUse > 12 && levelToUse <= 24;
  }

  private static boolean starterLevel(int levelToUse) {
    return levelToUse > 0 && levelToUse <= 12;
  }

  public int onMouseClicked(MouseEvent mouseEvent) {
    AtomicInteger levelClicked = new AtomicInteger(-1);
    positionLevelMap.forEach(
        (position, level) -> {
          if (mouseEvent.getX() > position.x
              && mouseEvent.getX() < position.x + LEVEL_WIDTH
              && (mouseEvent.getY() > position.y && mouseEvent.getY() < position.y + LEVEL_WIDTH)) {
            levelClicked.set(level);
          }
        });
    return levelClicked.get();
  }

  public void renderLevel(GraphicsContext graphicsContext, int level) {
    renderHighLightLevel(graphicsContext, level);
  }

  private void renderHighLightLevel(GraphicsContext graphicsContext, int level) {
    renderLevelColor(graphicsContext, level, true);
  }
}
