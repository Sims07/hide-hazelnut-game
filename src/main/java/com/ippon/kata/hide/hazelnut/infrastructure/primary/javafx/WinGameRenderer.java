package com.ippon.kata.hide.hazelnut.infrastructure.primary.javafx;

import com.ippon.kata.hide.hazelnut.infrastructure.secondary.spring.model.BoardChangedEventDTO;
import java.net.URISyntaxException;
import java.net.URL;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class WinGameRenderer extends AbstractRenderer<BoardChangedEventDTO> {
  private static final URL LOST_SOUND = HideHazelnutGame.class.getResource("/sounds/win.wav");

  @Override
  public void render(GraphicsContext graphicsContext, BoardChangedEventDTO event) {
    wonSound();
    renderWonGame(graphicsContext);
  }

  private void renderWonGame(GraphicsContext graphicsContext) {
    final Paint initialFill = graphicsContext.getFill();
    graphicsContext.setFill(Color.BLACK);
    graphicsContext.fillRect(0, 0, boardWidth(), boardHeight());
    graphicsContext.strokeRect(0, 0, boardWidth(), boardHeight());
    graphicsContext.setFill(Color.WHITE);
    graphicsContext.fillText("Gagné", boardWidth() / 2.0, boardHeight() / 2.0);
    graphicsContext.setFill(initialFill);
  }

  private void wonSound() {
    final AudioClip audioClip;
    try {
      audioClip = new AudioClip(LOST_SOUND.toURI().toString());
      audioClip.setVolume(5);
      audioClip.play();
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }
}
