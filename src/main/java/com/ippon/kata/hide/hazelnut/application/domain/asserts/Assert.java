package com.ippon.kata.hide.hazelnut.application.domain.asserts;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

public class Assert {

  public static final String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
  private final String context;
  private final Class<?> assertedClass;

  Assert(Class<?> assertedClass, String context) {
    this.context = context;
    this.assertedClass = assertedClass;
  }

  public Assert notNull(Object object, String message) {
    if (object == null) {
      throw new IllegalArgumentException(buildMessage(message));
    }
    return this;
  }

  public Assert hasLength(String str, int expectedLength, String message) {
    if (str.length() != expectedLength) {
      throw new IllegalArgumentException(buildMessage(message));
    }
    return this;
  }

  public Assert notEmpty(Object object, String message) {

    if (object == null
        || (object instanceof Collection<?> col && col.isEmpty())
        || (object instanceof String str && str.isEmpty())) {
      throw new IllegalArgumentException(buildMessage(message));
    }
    return this;
  }

  public Assert strictlyGreaterThan(
      double expectedGreaterValue, double bottomValue, String message) {
    if (expectedGreaterValue <= bottomValue) {
      throw new IllegalArgumentException(buildMessage(message));
    }
    return this;
  }

  public Assert isEqualTo(long toTest, long equalToValue, String message) {
    if (toTest != equalToValue) {
      throw new IllegalArgumentException(buildMessage(message));
    }
    return this;
  }

  private String buildMessage(String message) {
    return String.format(
        "%s-%s:%s", assertedClass.getName(), context().orElse("Constructor"), message);
  }

  public Assert isValidEmail(String emailToTest) {
    if (!emailToTest.matches(EMAIL_REGEX)) {
      throw new IllegalArgumentException(
          buildMessage(String.format("Email %s is invalid", emailToTest)));
    }
    return this;
  }

  private Optional<String> context() {
    return Optional.ofNullable(context);
  }

  public void strictlyLessThan(int expectedLessValue, int maxValue, String message) {
    if (expectedLessValue >= maxValue) {
      throw new IllegalArgumentException(buildMessage(message));
    }
  }

  public Assert notInThePast(LocalDate dateToTest, String message) {
    return notBeforeDate(dateToTest, LocalDate.now(), message);
  }

  public Assert notBeforeDate(LocalDate dateToTest, LocalDate beforeDate, String message) {
    if (dateToTest.isBefore(beforeDate)) {
      throw new IllegalArgumentException(buildMessage(message));
    }
    return this;
  }
}
