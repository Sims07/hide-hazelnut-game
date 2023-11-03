package com.ippon.kata.hide.hazelnut.infrastructure.primary.javafx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class SquirrelRenderer {
  private static final double EAR_SIZE = 0.25; // 25% de la taille de la tête
  private static final double EYE_SIZE = 0.17; // 17% de la taille de la tête
  private static final double PUPIL_SIZE = 0.07; // 7% de la taille de la tête
  private static final double NOSE_SIZE = 0.08; // 8% de la taille de la tête
  private static final double MOUTH_WIDTH = 0.33; // 33% de la taille de la tête
  private static final double MOUTH_HEIGHT = 0.17; // 17% de la taille de la tête

  public static void drawSquirrel(
      GraphicsContext gc, double x, double y, double ecureuilSize, Color color) {
    // Dessiner la tête de l'écureuil roux
    gc.setFill(color); // Couleur rousse
    gc.fillOval(x, y, ecureuilSize, ecureuilSize); // Tête de l'écureuil

    // Oreille gauche (pointue, attachée à la tête)
    double earSize = ecureuilSize * EAR_SIZE;
    gc.setFill(color);
    gc.fillPolygon(new double[] {x, x - earSize, x + earSize}, new double[] {y, y - earSize, y}, 3);

    // Oreille droite (pointue, attachée à la tête)
    gc.setFill(color);
    gc.fillPolygon(
        new double[] {x + ecureuilSize, x + ecureuilSize + earSize, x + ecureuilSize - earSize},
        new double[] {y, y - earSize, y},
        3);

    // Yeux
    double eyeSize = ecureuilSize * EYE_SIZE;
    gc.setFill(Color.WHITE);
    gc.fillOval(x + ecureuilSize * 0.25, y + ecureuilSize * 0.2, eyeSize, eyeSize); // Oeil gauche
    gc.fillOval(
        x + ecureuilSize * 0.75 - eyeSize, y + ecureuilSize * 0.2, eyeSize, eyeSize); // Oeil droit

    // Pupilles
    double pupilSize = ecureuilSize * PUPIL_SIZE;
    gc.setFill(Color.BLACK);
    gc.fillOval(
        x + ecureuilSize * 0.3, y + ecureuilSize * 0.25, pupilSize, pupilSize); // Pupille gauche
    gc.fillOval(
        x + ecureuilSize * 0.7 - pupilSize,
        y + ecureuilSize * 0.25,
        pupilSize,
        pupilSize); // Pupille droite

    // Nez
    double noseSize = ecureuilSize * NOSE_SIZE;
    gc.setFill(Color.BLACK);
    gc.fillOval(
        x + ecureuilSize * 0.5 - noseSize * 0.5,
        y + ecureuilSize * 0.5 - noseSize * 0.5,
        noseSize,
        noseSize);

    // Bouche
    double mouthWidth = ecureuilSize * MOUTH_WIDTH;
    double mouthHeight = ecureuilSize * MOUTH_HEIGHT;
    gc.setStroke(Color.BLACK);
    gc.setLineWidth(2);
    gc.strokeArc(
        x + ecureuilSize * 0.5 - mouthWidth * 0.5,
        y + ecureuilSize * 0.6,
        mouthWidth,
        mouthHeight,
        0,
        -180,
        ArcType.OPEN);
  }
}
