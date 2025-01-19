package io.github.lxxbai.jvs.component;


import com.jfoenix.controls.JFXProgressBar;
import io.github.lxxbai.jvs.common.util.ThreadPoolUtil;
import io.github.lxxbai.jvs.component.task.DownloadTask;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Worker;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.Objects;

/**
 * 下载进度条
 *
 * @author lxxbai
 */
public class XxbDownloadBar {

    private static final String DEFAULT_STYLE_CLASS = "xxb-download-bar";

    /**
     * 下载任务
     */
    private DownloadTask task;

    // 创建进度条
    private final JFXProgressBar progressBar;

    //描述加进度
    private final Label progressLabel;

    private final SimpleDoubleProperty barWidth;

    private final SimpleDoubleProperty barHeight;


    /**
     * 构造方法
     *
     * @param width       进度条宽度
     * @param height      进度条高度
     * @param downloadUrl 下载地址
     * @param fileName    文件名称
     */
    public XxbDownloadBar(int width, int height, String downloadUrl, String fileName) {
        this.barWidth = new SimpleDoubleProperty(0);
        this.barHeight = new SimpleDoubleProperty(0);
        // 创建进度条
        this.progressBar = new JFXProgressBar();
        // 创建显示进度的标签
        this.progressLabel = new Label("0%");
        //创建一个下载任务
        this.task = new DownloadTask(downloadUrl, fileName);
        progressBar.progressProperty().bind(task.progressProperty());
        progressLabel.textProperty().bind(task.messageProperty());
        setBarWidth(width);
        setBarHeight(height);
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


    public boolean isRunning() {
        return task.isRunning();
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
        if (task.isCancelled() || Objects.equals(task.getState(), Worker.State.FAILED)
                || Objects.equals(task.getState(), Worker.State.CANCELLED)) {
            //解绑任务
            unbind();
            String oldMessage = task.getMessage();
            EventHandler<WorkerStateEvent> onSucceeded = task.getOnSucceeded();
            EventHandler<WorkerStateEvent> onFailed = task.getOnFailed();
            EventHandler<WorkerStateEvent> onCancelled = task.getOnCancelled();
            //重新创建下载任务
            this.task = new DownloadTask(task.getDownloadUrl(), task.getFileName());
            //绑定任务
            bind();
            //重新绑定事件
            task.setMessage(oldMessage);
            task.setOnSucceeded(onSucceeded);
            task.setOnFailed(onFailed);
            task.setOnCancelled(onCancelled);
        }
        ThreadPoolUtil.execute(task);
    }


    private void unbind() {
        progressBar.progressProperty().unbind();
        progressLabel.textProperty().unbind();
    }


    private void bind() {
        progressBar.progressProperty().bind(task.progressProperty());
        progressLabel.textProperty().bind(task.messageProperty());
    }

    /**
     * 暂停或退出
     */
    public void cancel() {
        task.pauseOrCancel();
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

    public double getBarWidth() {
        return barWidth.get();
    }

    public double getBarHeight() {
        return barHeight.get();
    }

    public void setBarWidth(double barWidth) {
        this.barWidth.setValue(barWidth);
    }

    public void setBarHeight(double barHeight) {
        this.barHeight.setValue(barHeight);
    }

    public SimpleDoubleProperty barHeightProperty() {
        return barHeight;
    }

    public SimpleDoubleProperty barWidthProperty() {
        return barWidth;
    }

    public void setMessage(String message) {
        task.setMessage(message);
    }

    /**
     * 获取下载进度条
     *
     * @return 下载进度条
     */
    public VBox getDownloadBar() {
        VBox vBox = new VBox(progressLabel, progressBar);
        vBox.getStyleClass().add(DEFAULT_STYLE_CLASS);
        return vBox;
    }
}