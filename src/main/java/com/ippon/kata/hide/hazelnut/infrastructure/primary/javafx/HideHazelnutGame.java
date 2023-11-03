package com.ippon.kata.hide.hazelnut.infrastructure.primary.javafx;

import com.ippon.kata.hide.hazelnut.JavaFxHideHazelnutApplication;
import com.ippon.kata.hide.hazelnut.application.domain.Color;
import com.ippon.kata.hide.hazelnut.application.domain.Orientation;
import com.ippon.kata.hide.hazelnut.infrastructure.primary.spring.GameAPI;
import com.ippon.kata.hide.hazelnut.infrastructure.secondary.spring.model.BoardChangedEventDTO;
import java.net.URISyntaxException;
import java.net.URL;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

public class HideHazelnutGame extends Application {

  private static final int WIDTH = 10;
  private static final int HEIGHT = 22;
  private static final int BLOCK_SIZE = 40;
  public static final String TITLE = "Hide Hazelnut";
  public static final int FONT_SIZE = 25;
  public static final double HALF = 2.0;
  public static final double VOLUME = 0.1;
  private ConfigurableApplicationContext applicationContext;
  private GameAPI gameAPI;
  private MediaPlayer mediaPlayer;
  private final Canvas canvas;
  public static final URL TETRIS_MP3 =
      HideHazelnutGame.class.getResource("/sounds/tetris-theme.mp3");
  private Color currentSquirrelColor;
  private BoardRenderer boardRenderer;

  public HideHazelnutGame() {
    canvas = new Canvas(HALF * WIDTH * BLOCK_SIZE, (double) HEIGHT * BLOCK_SIZE);
  }

  @Override
  public void init() {
    applicationContext = new SpringApplicationBuilder(JavaFxHideHazelnutApplication.class).run();
    gameAPI = applicationContext.getBean(GameAPI.class);
    boardRenderer = new BoardRenderer();
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    GraphicsContext graphicsContext = initCanvas(primaryStage);
    graphicsContext.setFont(new Font(FONT_SIZE));
    registerListeners(graphicsContext);
    initMediaPLayers();
    startLevelOne();
    playHideHazelnutThemeMusic();

    primaryStage.show();
  }

  private void startLevelOne() {
    gameAPI.startLevel(1);
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
            case LEFT -> gameAPI.move(currentSquirrelColor, Orientation.LEFT);
            case UP -> gameAPI.move(currentSquirrelColor, Orientation.UP);
          }
        });
    GraphicsContext gc = canvas.getGraphicsContext2D();
    primaryStage.setTitle(TITLE);
    primaryStage.setScene(scene);
    root.getChildren().add(canvas);
    return gc;
  }

  private void selectSquirrelPiece(Color color) {
    this.currentSquirrelColor = color;
  }

  private void renderBoard(GraphicsContext graphicsContext, BoardChangedEventDTO event) {
    boardRenderer.render(graphicsContext, event.board());
  }

  @Override
  public void stop() {
    applicationContext.close();
    Platform.exit();
  }
}
