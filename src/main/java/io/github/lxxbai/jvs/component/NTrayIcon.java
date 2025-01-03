package io.github.lxxbai.jvs.component;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author fearless
 * @version v1.0
 * @description:
 * @date 2021/12/30 下午6:23
 */
public class NTrayIcon extends TrayIcon {

    //设置面板和布局为静态变量
    private Stage stage;
    private StackPane pane;

    private void init() {
        stage = new Stage();
        pane = new StackPane();
        stage.setScene(new Scene(pane));
        //去掉面板的标题栏
        stage.initStyle(StageStyle.TRANSPARENT);
        //监听面板焦点
        stage.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                //如果失去焦点就隐藏面板
                stage.hide();
            }
        });
    }

    private static void showPopupMenu(MouseEvent e) {

    }

    public NTrayIcon(Image image, String tooltip, Region menu) {
        super(image, tooltip);
        init();
        //设置系统托盘图标为自适应
        this.setImageAutoSize(true);
        //添加组件到面板中
        pane.getChildren().add(menu);
        //设置面板的宽高
        stage.setWidth(menu.getPrefWidth());
        stage.setHeight(menu.getPrefHeight());
        //添加鼠标事件
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("坐标：" + e.getX() + "," + e.getY());
                //getButton() 1左键 2中键 3右键
                if (e.getButton() == 3) {
                    //在JavaFx的主线程中调用javaFx组件的方法
                    Platform.runLater(() -> {
                        // 获取鼠标位置
                        Point point = e.getLocationOnScreen();
                        // 在右上方显示
                        int x = (int) point.getX(); // X坐标
                        int y = (int) point.getY() - 100; // 调整Y坐标，向上移动
                        // 确保菜单不会超出屏幕
                        Rectangle screenBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
                        if (y < 0) {
                            y = 0; // 防止超出顶部
                        }
                        if (x + 100 > screenBounds.getWidth()) {
                            x = (int) (screenBounds.getWidth() - 100); // 防止超出右边
                        }
                        //设置dialog的显示位置
                        stage.setX(x);
                        stage.setY(y);
                        System.out.println("新坐标：" + stage.getX() + "," + stage.getY());
                        //设置为顶层，否则在windows系统中会被底部任务栏遮挡
                        stage.setAlwaysOnTop(true);
                        //展示/隐藏
                        if (!stage.isShowing()) {
                            stage.show();
                        } else {
                            stage.hide();
                        }
                    });
                }
            }
        });
    }
}
