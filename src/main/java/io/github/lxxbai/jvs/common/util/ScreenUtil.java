package io.github.lxxbai.jvs.common.util;

import javafx.stage.Screen;
import javafx.stage.Stage;

public class ScreenUtil {

    /**
     * 屏幕宽度
     */
    private static final double SCREEN_WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
    /**
     * 屏幕高度
     */
    private static final double SCREEN_HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();


    /**
     * 设置屏幕宽高 ，百分比的
     *
     * @param stage         舞台
     * @param widthPercent  宽度百分比
     * @param heightPercent 高度百分比
     */
    public static void setScreenPosition(Stage stage, double widthPercent, double heightPercent) {
        // 计算窗口占据屏幕的百分比
        double windowWidth = SCREEN_WIDTH * widthPercent;
        double windowHeight = SCREEN_HEIGHT * heightPercent;
        // 设置 Stage 的位置，使其居中
        stage.setX((SCREEN_WIDTH - windowWidth) / 2);
        stage.setY((SCREEN_HEIGHT - windowHeight) / 2);
        // 设置窗口宽度
        stage.setWidth(windowWidth);
        // 设置窗口高度
        stage.setHeight(windowHeight);
    }
}
