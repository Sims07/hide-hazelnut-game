package com.ippon.kata.hide.hazelnut.infrastructure.secondary.file.model;

import com.ippon.kata.hide.hazelnut.application.domain.BoardLevel;
import com.ippon.kata.hide.hazelnut.application.domain.GameConfiguration;
import java.util.List;

public record BoardConfigurationDTO(int level, List<PieceConfigurationDTO> configuration) {

  public BoardLevel toDomain() {
    return new BoardLevel(
        level,
        new GameConfiguration(
            configuration.stream().map(PieceConfigurationDTO::toPieceConfiguration).toList()));
  }
}
