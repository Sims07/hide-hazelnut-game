package com.ippon.kata.hide.hazelnut.infrastructure.primary.javafx;

import static java.lang.StringTemplate.STR;

import com.ippon.kata.hide.hazelnut.application.domain.BoardLevel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LevelRenderer extends AbstractRenderer<List<BoardLevel>> {

  private record PositionOnScreen(double x, double y) {}

  private static final Logger LOGGER = LoggerFactory.getLogger(LevelRenderer.class);
  public static final int LEVEL_BY_LINES = 5;
  public static final Map<PositionOnScreen, Integer> positionLevelMap = new HashMap<>();
  public static final int LEVEL_PERCENTAGE_WIDTH = 6;
  public static final int LEVEL_WIDTH = BLOCK_SIZE / LEVEL_PERCENTAGE_WIDTH;
  public static final double TEXT_WIDTH_DIVISION = 2.2;
  public static final double TEXT_WIDTH_Y_DIVISION = 1.3;

  @Override
  public void render(GraphicsContext graphicsContext, List<BoardLevel> boardLevels) {
    boardLevels.forEach(boardLevel -> renderBoardLevel(graphicsContext, boardLevel.level()));
  }

  private void renderBoardLevel(GraphicsContext graphicsContext, double level) {
    int levelToUse = (int) (level - 1);
    graphicsContext.setFill(Color.YELLOW);

    final double xPosition = levelToUse % LEVEL_BY_LINES;
    final double x = boardWidth() + xPosition * LEVEL_WIDTH;
    final double y = ((int) levelToUse / LEVEL_BY_LINES) * (double) LEVEL_WIDTH;
    final double finalX = x + PADDING;
    final double finalY = y + PADDING;
    positionLevelMap.put(new PositionOnScreen(finalX, finalY), (int) level);
    LOGGER.info("level={},x={},y={}", levelToUse, xPosition, levelToUse / LEVEL_BY_LINES);
    graphicsContext.fillRect(finalX, finalY, LEVEL_WIDTH, LEVEL_WIDTH);
    graphicsContext.strokeRect(finalX, finalY, LEVEL_WIDTH, LEVEL_WIDTH);
    graphicsContext.setFill(Color.BLACK);
    graphicsContext.fillText(
        "" + (int) level,
        x + LEVEL_WIDTH / TEXT_WIDTH_DIVISION + PADDING,
        y + LEVEL_WIDTH / LevelRenderer.TEXT_WIDTH_Y_DIVISION + PADDING);
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
    renderTextAt(graphicsContext,  boardWidth() + BLOCK_SIZE, boardHeight()/2.0, STR."Niveau \{ level }", Color.BLACK);

  }
}
