package io.github.lxxbai.javaversionselector.component.fx;


import cn.hutool.core.util.StrUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import io.github.lxxbai.javaversionselector.common.util.ThreadPoolUtil;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import lombok.Getter;

/**
 * 下载进度条
 *
 * @author lxxbai
 */
public class DownloadProgress implements BaseNode {

    private final HBox node;

    /**
     * 下载任务
     */
    @Getter
    private final DownloadTask task;


    /**
     * 构造方法
     *
     * @param width       进度条宽度
     * @param height      进度条高度
     * @param downloadUrl 下载地址
     * @param saveDir     保存目录
     */
    public DownloadProgress(int width, int height, String downloadUrl, String saveDir, String detailDesc) {
        String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/") + 1);
        fileName = StrUtil.isBlank(fileName) ? "unknown.zip" : fileName;
        this.node = new HBox();
        node.setAlignment(Pos.CENTER);
        StackPane stackPane = new StackPane();
        // 创建进度条
        ProgressBar progressBar = new JFXProgressBar();
        progressBar.setPrefWidth(width);
        progressBar.setPrefHeight(height);
        // 创建显示进度的标签
        Label progressLabel = new Label("0%");
        progressLabel.setStyle("-fx-text-fill: #0b0a0a; -fx-font-size: " + (height - 2) + "px;");
        // 将标签放在进度条上
        stackPane.getChildren().addAll(progressBar, progressLabel);
        // 确保标签在进度条上方居中显示
        StackPane.setAlignment(progressLabel, Pos.CENTER);
        // 下方的描述
        JFXButton jfxButton = new JFXButton(detailDesc);
        jfxButton.setButtonType(JFXButton.ButtonType.FLAT);
        jfxButton.setDisable(true);
        // 将标签放在进度条下方居中显示
        node.getChildren().addAll(jfxButton, stackPane);
        //创建一个下载任务
        this.task = new DownloadTask(downloadUrl, saveDir, fileName);
        progressBar.progressProperty().bind(task.progressProperty());
        progressLabel.textProperty().bind(task.messageProperty());
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
        ThreadPoolUtil.execute(task);
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
