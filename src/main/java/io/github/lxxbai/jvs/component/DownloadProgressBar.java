package io.github.lxxbai.jvs.component;


import com.jfoenix.controls.JFXProgressBar;
import io.github.lxxbai.jvs.common.util.ThreadPoolUtil;
import io.github.lxxbai.jvs.component.task.DownloadTask;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import lombok.Getter;

/**
 * 下载进度条
 *
 * @author lxxbai
 */
public class DownloadProgressBar implements BaseNode {

    private final VBox node;

    /**
     * 下载任务
     */
    @Getter
    private DownloadTask task;


    // 创建进度条
    private final ProgressBar progressBar;


    private final Label progressLabel;


    /**
     * 构造方法
     *
     * @param width       进度条宽度
     * @param height      进度条高度
     * @param downloadUrl 下载地址
     * @param fileName    文件名称
     */
    public DownloadProgressBar(int width, int height, String downloadUrl, String fileName, String detailDesc) {
        this.node = new VBox();
        node.setAlignment(Pos.CENTER_LEFT);
        // 创建进度条
        progressBar = new JFXProgressBar();
        progressBar.setPrefWidth(width);
        progressBar.setPrefHeight(height);
        // 创建显示进度的标签
        progressLabel = new Label("0%");
        // 确保标签在进度条上方居中显示
        StackPane.setAlignment(progressLabel, Pos.CENTER_LEFT);
        // 将标签放在进度条下方居中显示
        node.getChildren().addAll(progressLabel, progressBar);
        //创建一个下载任务
        this.task = new DownloadTask(downloadUrl, fileName);
        progressBar.progressProperty().bind(task.progressProperty());
        progressLabel.textProperty().bind(task.messageProperty());
    }


    /**
     * 设置进度
     *
     * @param downloadedBytes 已下载
     * @param totalBytes      总字节数
     */
    public void updateProgress(double downloadedBytes, long totalBytes) {
        task.updateProgress(downloadedBytes, totalBytes);
    }

    /**
     * 启动任务执行
     * <p>
     * 本方法通过线程池调度给定的任务，实现异步执行
     */
    public void start() {
        if (task.isRunning()) {
            return;
        }
        if (task.isCancelled()) {
            unbind();
            DownloadTask newTask = new DownloadTask(task.getDownloadUrl(), task.getFileName());
            bind(this.task, newTask);
            this.task = newTask;
        }
        ThreadPoolUtil.execute(task);
    }

    private void unbind() {
        progressBar.progressProperty().unbind();
        progressLabel.textProperty().unbind();
    }

    private void bind(DownloadTask oldTask, DownloadTask newTask) {
        progressBar.progressProperty().bind(newTask.progressProperty());
        progressLabel.textProperty().bind(newTask.messageProperty());
        newTask.setOnFailed(oldTask.getOnFailed());
        newTask.setOnSucceeded(oldTask.getOnSucceeded());
        newTask.setOnCancelled(oldTask.getOnCancelled());
    }

    public void setOnSucceeded(EventHandler<WorkerStateEvent> eventHandler) {
        task.setOnSucceeded(eventHandler);
    }

    public void setOnFailed(EventHandler<WorkerStateEvent> eventHandler) {
        task.setOnFailed(eventHandler);
    }

    public void setOnCancelled(EventHandler<WorkerStateEvent> eventHandler) {
        task.setOnCancelled(eventHandler);
    }

    /**
     * 启动任务执行
     * <p>
     * 本方法通过线程池调度给定的任务，实现异步执行
     */
    public void cancel() {
        task.pauseOrCancel();
    }


    @Override
    public Node getContent() {
        return node;
    }
}
