package com.ippon.kata.hide.hazelnut.infrastructure.primary.javafx;

import com.ippon.kata.hide.hazelnut.application.domain.BoardLevel;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LevelRenderer extends AbstractRenderer<List<BoardLevel>> {

  private static final Logger LOGGER = LoggerFactory.getLogger(LevelRenderer.class);
  public static final int LEVEL_BY_LINES = 5;
  public static final int LEVEL_PERCENTAGE_WIDTH = 6;
  public static final double TEXT_WIDTH_DIVISION = 2.2;
  public static final double TEXT_WIDTH_Y_DIVISION = 1.3;

  @Override
  public void render(GraphicsContext graphicsContext, List<BoardLevel> boardLevels) {
    boardLevels.forEach(
        (boardLevel) -> {
          renderBoardLevel(graphicsContext, boardLevel.level());
        });
  }

  private void renderBoardLevel(GraphicsContext graphicsContext, double level) {
    int levelToUse = (int) (level - 1);
    graphicsContext.setFill(Color.YELLOW);
    final int levelWidth = BLOCK_SIZE / LEVEL_PERCENTAGE_WIDTH;

    final double xPosition = levelToUse % LEVEL_BY_LINES;
    final double x = boardWidth() + xPosition * levelWidth;
    final double y = ((int) levelToUse / LEVEL_BY_LINES) * (double) levelWidth;
    LOGGER.info("level={},x={},y={}", levelToUse, xPosition, levelToUse / LEVEL_BY_LINES);
    graphicsContext.fillRect(x + PADDING, y + PADDING, levelWidth, levelWidth);
    graphicsContext.strokeRect(x + PADDING, y + PADDING, levelWidth, levelWidth);
    graphicsContext.setFill(Color.BLACK);
    graphicsContext.fillText(
        "" + levelToUse,
        x + levelWidth / TEXT_WIDTH_DIVISION + PADDING,
        y + levelWidth / LevelRenderer.TEXT_WIDTH_Y_DIVISION + PADDING);
  }
}
