package com.ippon.kata.hide.hazelnut;

import com.ippon.kata.hide.hazelnut.infrastructure.primary.javafx.HideHazelnutGame;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class JavaFxHideHazelnutApplication implements AsyncConfigurer {

  @Override
  public Executor getAsyncExecutor() {
    return new TaskExecutorAdapter(Executors.newVirtualThreadPerTaskExecutor());
  }

  public static void main(String[] args) {
    Application.launch(HideHazelnutGame.class, args);
    System.exit(0);
  }
}
