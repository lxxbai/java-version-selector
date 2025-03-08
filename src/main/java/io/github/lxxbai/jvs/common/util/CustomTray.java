package io.github.lxxbai.jvs.common.util;

import io.github.lxxbai.jvs.common.exception.ClientException;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Objects;

/**
 * 自定义系统托盘实现，不依赖java.awt
 * 使用JavaFX创建一个小型悬浮窗口来模拟系统托盘功能
 */
public class CustomTray {
    private static final double ICON_SIZE = 16.0;
    private static final double TRAY_SIZE = 24.0;
    
    private Stage trayStage;
    private double xOffset;
    private double yOffset;
    private final Stage primaryStage;
    
    /**
     * 创建自定义系统托盘图标
     *
     * @param primaryStage 主舞台
     */
    public static void createTrayIcon(Stage primaryStage) {
        new CustomTray(primaryStage);
    }
    
    private CustomTray(Stage primaryStage) {
        this.primaryStage = primaryStage;
        initTrayStage();
        setupTrayIcon();
        setupDraggable();
        setupCloseHandler();
    }
    
    private void initTrayStage() {
        // 创建一个小型舞台作为托盘图标
        trayStage = new Stage();
        trayStage.initStyle(StageStyle.TRANSPARENT);
        trayStage.setAlwaysOnTop(true);
        trayStage.setResizable(false);
        
        // 设置托盘图标位置在屏幕右下角
        double screenWidth = javafx.stage.Screen.getPrimary().getBounds().getWidth();
        double screenHeight = javafx.stage.Screen.getPrimary().getBounds().getHeight();
        trayStage.setX(screenWidth - TRAY_SIZE - 10);
        trayStage.setY(screenHeight - TRAY_SIZE - 10);
        
        // 执行stage.close()方法,窗口不直接退出
        Platform.setImplicitExit(false);
    }
    
    private void setupTrayIcon() {
        try {
            // 创建托盘图标
            StackPane trayIconPane = new StackPane();
            trayIconPane.setAlignment(Pos.CENTER);
            trayIconPane.setStyle("-fx-background-color: transparent;");
            
            // 尝试加载图标
            ImageView iconView;
            try {
                Image image = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("pic/jvs.png")));
                iconView = new ImageView(image);
            } catch (Exception e) {
                // 如果图标加载失败，使用FontAwesome图标作为备用
                FontIcon icon = new FontIcon(FontAwesomeSolid.COFFEE);
                icon.setIconSize((int) ICON_SIZE);
                iconView = new ImageView();
                trayIconPane.getChildren().add(icon);
            }
            
            if (iconView.getImage() != null) {
                iconView.setFitWidth(ICON_SIZE);
                iconView.setFitHeight(ICON_SIZE);
                trayIconPane.getChildren().add(iconView);
            }
            
            // 创建托盘菜单
            ContextMenu trayMenu = createTrayMenu();
            
            // 设置鼠标点击事件
            trayIconPane.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    // 左键点击显示主窗口
                    showStage(primaryStage);
                } else if (event.getButton() == MouseButton.SECONDARY) {
                    // 右键点击显示菜单
                    trayMenu.show(trayStage, event.getScreenX(), event.getScreenY());
                }
            });
            
            // 设置场景
            Scene trayScene = new Scene(trayIconPane, TRAY_SIZE, TRAY_SIZE);
            trayScene.setFill(javafx.scene.paint.Color.TRANSPARENT);
            trayStage.setScene(trayScene);
            trayStage.show();
        } catch (Exception e) {
            throw new ClientException("System_error", "托盘图标创建失败");
        }
    }
    
    private ContextMenu createTrayMenu() {
        ContextMenu menu = new ContextMenu();
        
        // 主窗口菜单项
        MenuItem showItem = new MenuItem("Main");
        showItem.setOnAction(e -> showStage(primaryStage));
        
        // 退出菜单项
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> System.exit(0));
        
        // 添加菜单项到菜单
        menu.getItems().add(showItem);
        menu.getItems().add(new SeparatorMenuItem());
        menu.getItems().add(exitItem);
        
        return menu;
    }
    
    private void setupDraggable() {
        // 使托盘图标可拖动
        trayStage.getScene().setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        
        trayStage.getScene().setOnMouseDragged(event -> {
            trayStage.setX(event.getScreenX() - xOffset);
            trayStage.setY(event.getScreenY() - yOffset);
        });
    }
    
    private void setupCloseHandler() {
        // 设置主窗口关闭事件
        primaryStage.setOnCloseRequest(event -> {
            // 取消关闭事件
            event.consume();
            // 隐藏窗口
            primaryStage.hide();
        });
    }
    
    /**
     * 点击系统托盘,显示界面(并且显示在最前面,将最小化的状态设为false)
     */
    private void showStage(Stage stage) {
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