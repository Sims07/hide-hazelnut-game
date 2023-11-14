package com.ippon.kata.hide.hazelnut.infrastructure.secondary.spring;

import com.ippon.kata.hide.hazelnut.application.domain.BoardChangedEvent;
import com.ippon.kata.hide.hazelnut.infrastructure.secondary.spring.model.BoardChangedEventDTO;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class BoardPublisher extends AbstractPublisher<BoardChangedEventDTO, BoardChangedEvent> {

  protected BoardPublisher(ApplicationEventPublisher applicationEventPublisher) {
    super(applicationEventPublisher);
  }

  @Override
  protected BoardChangedEventDTO from(Object source, BoardChangedEvent domainEvent) {
    return new BoardChangedEventDTO(source, domainEvent.board());
  }
}
