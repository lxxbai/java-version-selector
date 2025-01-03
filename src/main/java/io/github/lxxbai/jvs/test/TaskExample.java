package io.github.lxxbai.jvs.test;

import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TaskExample extends Application {
    private MyTask task;

    @Override
    public void start(Stage primaryStage) {
        JFXButton jfoenixButton = new JFXButton("JFoenix Button");
        jfoenixButton.setButtonType(JFXButton.ButtonType.FLAT);
        JFXButton button = new JFXButton("RAISED BUTTON");
        button.getStyleClass().add("button-raised");

        JFXButton mixedButton = new JFXButton("Mixed Style");
        mixedButton.setButtonType(JFXButton.ButtonType.RAISED);
        mixedButton.getStyleClass().add("custom-button");

        JFXButton button1 = new JFXButton("DISABLED");
        button1.setDisable(true);

        VBox root = new VBox(10, jfoenixButton, button, button1,mixedButton);
        primaryStage.setScene(new Scene(root, 300, 200));
        primaryStage.setTitle("Task Example");
        primaryStage.show();
    }

    private void startTask(ProgressBar progressBar) {
        task = new MyTask();
        progressBar.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }

    private void cancelTask() {
        if (task != null) {
            task.cancel();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    class MyTask extends Task<Void> {
        @Override
        protected Void call() {
            for (int i = 0; i < 100; i++) {
                if (isCancelled()) {
                    break; // 退出循环
                }
                updateProgress(i + 1, 100);
                try {
                    Thread.sleep(100); // 模拟耗时操作
                } catch (InterruptedException e) {
                    // 处理被中断的异常
                }
            }
            return null;
        }
    }
}
