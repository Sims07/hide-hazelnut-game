package com.ippon.kata.hide.hazelnut.application.domain;

public interface EventPublisher<T> {

  T publish(T event);
}
