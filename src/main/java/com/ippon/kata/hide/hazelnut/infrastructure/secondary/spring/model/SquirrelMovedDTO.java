package com.ippon.kata.hide.hazelnut.infrastructure.secondary.spring.model;

import org.springframework.context.ApplicationEvent;

public class SquirrelMovedDTO extends ApplicationEvent {

  public SquirrelMovedDTO(Object source) {
    super(source);
  }
}
