package com.ippon.kata.hide.hazelnut.infrastructure.primary.javafx;

import com.ippon.kata.hide.hazelnut.JavaFxHideHazelnutApplication;
import com.ippon.kata.hide.hazelnut.application.domain.Board;
import com.ippon.kata.hide.hazelnut.application.domain.BoardLevel;
import com.ippon.kata.hide.hazelnut.application.domain.Color;
import com.ippon.kata.hide.hazelnut.application.domain.Orientation;
import com.ippon.kata.hide.hazelnut.infrastructure.primary.spring.GameAPI;
import com.ippon.kata.hide.hazelnut.infrastructure.secondary.spring.model.BoardChangedEventDTO;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

public class HideHazelnutGame extends Application {

  private static final int WIDTH = 4;
  private static final int HEIGHT = 4;
  private static final int BLOCK_SIZE = 180;
  public static final String TITLE = "Hide Hazelnut";
  public static final int FONT_SIZE = 25;
  public static final double SCREEN_SIZE_PROPORTION_TO_BOARD = 1.6;
  public static final double VOLUME = 0.1;
  private ConfigurableApplicationContext applicationContext;
  private GameAPI gameAPI;
  private MediaPlayer mediaPlayer;
  private final Canvas canvas;
  public static final URL TETRIS_MP3 =
      HideHazelnutGame.class.getResource("/sounds/hide-hazelnut-theme.mp3");
  private Color currentSquirrelColor;
  private BoardRenderer boardRenderer;
  private WinGameRenderer winGameRenderer;
  private LevelRenderer levelRenderer;
  private Board board;
  private int currentLevel = 1;
  private List<BoardLevel> boardLevels;

  public HideHazelnutGame() {
    canvas =
        new Canvas(
            SCREEN_SIZE_PROPORTION_TO_BOARD * WIDTH * BLOCK_SIZE, (double) HEIGHT * BLOCK_SIZE);
  }

  @Override
  public void init() {
    applicationContext = new SpringApplicationBuilder(JavaFxHideHazelnutApplication.class).run();
    gameAPI = applicationContext.getBean(GameAPI.class);
    boardRenderer = new BoardRenderer();
    winGameRenderer = new WinGameRenderer();
    levelRenderer = new LevelRenderer();
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    GraphicsContext graphicsContext = initCanvas(primaryStage);
    graphicsContext.setFont(new Font(FONT_SIZE));
    registerListeners(graphicsContext);
    initMediaPLayers();
    initLevels(graphicsContext);
    startLevel();
    playHideHazelnutThemeMusic();

    primaryStage.show();
  }

  private void initLevels(GraphicsContext graphicsContext) {
    this.boardLevels = gameAPI.listGameLevels();
    levelRenderer.render(graphicsContext, boardLevels);
  }

  private void startLevel() {
    gameAPI.startLevel(currentLevel);
    levelRenderer.render(canvas.getGraphicsContext2D(), boardLevels);
    levelRenderer.renderLevel(canvas.getGraphicsContext2D(), currentLevel);
  }

  private void registerListeners(GraphicsContext graphicsContext) {
    applicationContext.addApplicationListener(
        (ApplicationListener<BoardChangedEventDTO>)
            event -> Platform.runLater(() -> renderBoard(graphicsContext, event)));
  }

  private void initMediaPLayers() throws URISyntaxException {
    Media mediaMusicGame = new Media(TETRIS_MP3.toURI().toString());
    mediaPlayer = new MediaPlayer(mediaMusicGame);
    mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    mediaPlayer.setAutoPlay(true);
    mediaPlayer.setVolume(VOLUME);
  }

  private void playHideHazelnutThemeMusic() {
    mediaPlayer.play();
  }

  private GraphicsContext initCanvas(Stage primaryStage) {
    Group root = new Group();
    Scene scene = new Scene(root);

    scene.setOnKeyPressed(
        event -> {
          switch (event.getCode()) {
            case O -> selectSquirrelPiece(Color.ORANGE);
            case G -> selectSquirrelPiece(Color.GREY);
            case Y -> selectSquirrelPiece(Color.YELLOW);
            case B -> selectSquirrelPiece(Color.BLACK);
            case LEFT -> gameAPI.move(board, currentSquirrelColor, Orientation.LEFT);
            case UP -> gameAPI.move(board, currentSquirrelColor, Orientation.UP);
            case DOWN -> gameAPI.move(board, currentSquirrelColor, Orientation.DOWN);
            case RIGHT -> gameAPI.move(board, currentSquirrelColor, Orientation.RIGHT);
            case N -> startLevel();
          }
        });
    GraphicsContext gc = canvas.getGraphicsContext2D();
    primaryStage.setTitle(TITLE);
    primaryStage.setScene(scene);
    scene.setOnMouseClicked(this::onMouseClicked);
    root.getChildren().add(canvas);
    return gc;
  }

  private void onMouseClicked(MouseEvent mouseEvent) {
    int level = levelRenderer.onMouseClicked(mouseEvent);
    if (level > 0) {
      this.currentLevel = level;
      startLevel();
    }
  }

  private void selectSquirrelPiece(Color color) {
    this.gameAPI.selectSquirrel(board, color);
    this.currentSquirrelColor = color;
  }

  private void renderBoard(GraphicsContext graphicsContext, BoardChangedEventDTO event) {
    this.board = event.board();
    boardRenderer.render(graphicsContext, event.board(), currentSquirrelColor);
    if (this.board.allSquirrelHasReleasedTheirHazelnuts()) {
      winGameRenderer.render(graphicsContext, event);
      this.currentLevel++;
    }
  }

  @Override
  public void stop() {
    applicationContext.close();
    Platform.exit();
  }
}
