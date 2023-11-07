package com.ippon.kata.hide.hazelnut.infrastructure.secondary.file;

import com.google.gson.Gson;
import com.ippon.kata.hide.hazelnut.application.domain.BoardLevel;
import com.ippon.kata.hide.hazelnut.application.domain.BoardLevels;
import com.ippon.kata.hide.hazelnut.infrastructure.primary.javafx.HideHazelnutGame;
import com.ippon.kata.hide.hazelnut.infrastructure.secondary.file.model.BoardConfigurationDTO;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileBoardLevels implements BoardLevels {
  private static final Logger LOGGER = LoggerFactory.getLogger(FileBoardLevels.class);
  private final String levelsFilePath;
  private BoardConfigurationDTO[] boardConfigurations;

  public FileBoardLevels(@Value("${levels.file.path}") String levelsFilePath) {
    this.levelsFilePath = levelsFilePath;
  }

  @PostConstruct
  void init() {
    try {
      final String readString =
          Files.readString(Path.of(HideHazelnutGame.class.getResource(levelsFilePath).toURI()));
      Gson gson = new Gson();
      boardConfigurations = gson.fromJson(readString, BoardConfigurationDTO[].class);
    } catch (IOException | URISyntaxException e) {
      LOGGER.error("", e);
    }
  }

  @Override
  public List<BoardLevel> list() {
    return Arrays.stream(boardConfigurations).map(BoardConfigurationDTO::toDomain).toList();
  }
}
