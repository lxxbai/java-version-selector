package io.github.lxxbai.jvs.component;


import com.jfoenix.controls.JFXProgressBar;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
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
public class XxbDownloadBar extends VBox {


    private static final String DEFAULT_STYLE_CLASS = "xxb-download-bar";

    /**
     * 下载任务
     */
    @Getter
    private DownloadTask task;

    // 创建进度条
    private final ProgressBar progressBar;

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
        this.getStyleClass().add(DEFAULT_STYLE_CLASS);
        this.setAlignment(Pos.CENTER_LEFT);
        this.barWidth = new SimpleDoubleProperty(0);
        this.barHeight = new SimpleDoubleProperty(0);
        // 创建进度条
        this.progressBar = new JFXProgressBar();
        // 创建显示进度的标签
        this.progressLabel = new Label("0%");
        // 确保标签在进度条上方居中显示
        StackPane.setAlignment(progressLabel, Pos.CENTER_LEFT);
        // 将标签放在进度条下方居中显示
        this.getChildren().addAll(progressLabel, progressBar);
        //创建一个下载任务
        this.task = new DownloadTask(downloadUrl, fileName);
        progressBar.progressProperty().bind(task.progressProperty());
        progressLabel.textProperty().bind(task.messageProperty());
        setBarWidth(width);
        setBarHeight(height);
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
}
