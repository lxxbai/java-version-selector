package io.github.lxxbai.javaversionselector.common.util;

import io.github.lxxbai.javaversionselector.common.exception.ClientException;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TrayUtil {


    /**
     * 创建系统托盘图标
     *
     * @param primaryStage stage
     */
    public static void createTrayIcon(Stage primaryStage) {
        // 检查系统托盘支持
        System.setProperty("java.awt.headless", "false");
        if (!SystemTray.isSupported()) {
            return;
        }
        //执行stage.close()方法,窗口不直接退出
        Platform.setImplicitExit(false);
        SystemTray tray = SystemTray.getSystemTray();
        // 使用合适的图标路径
        Image image = Toolkit.getDefaultToolkit().getImage(TrayUtil.class.getClassLoader().getResource("pic/jv.png"));
        PopupMenu popupMenu = new PopupMenu();
        MenuItem showItem = new MenuItem("Main");
        showItem.addActionListener(e -> showStage(primaryStage));
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        popupMenu.add(showItem);
        popupMenu.addSeparator();
        popupMenu.add(exitItem);
        TrayIcon trayIcon = new TrayIcon(image, "jvs", popupMenu);
        trayIcon.setImageAutoSize(true);
        // 单击图标显示窗口
        trayIcon.addMouseListener(buildMouseListener(primaryStage));
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            throw new ClientException("System_error", "窗口显示失败");
        }
    }

    private static MouseAdapter buildMouseListener(Stage stage) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //鼠标左键
                if (e.getButton() == MouseEvent.BUTTON1) {
                    showStage(stage);
                }
            }
        };
    }

    /**
     * 点击系统托盘,显示界面(并且显示在最前面,将最小化的状态设为false)
     */
    private static void showStage(Stage stage) {
        //点击系统托盘,
        Platform.runLater(() -> {
            if (stage.isIconified()) {
                stage.setIconified(false);
            }
            if (!stage.isShowing()) {
                stage.show();
            }
            stage.toFront();
        });
    }
}
