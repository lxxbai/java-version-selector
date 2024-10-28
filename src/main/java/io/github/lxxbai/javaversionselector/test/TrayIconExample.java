package io.github.lxxbai.javaversionselector.test;

import com.dustinredmond.fxtrayicon.FXTrayIcon;
import io.github.lxxbai.javaversionselector.common.util.ResourceUtil;
import javafx.application.Application;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.awt.*;


public class TrayIconExample extends Application {

    private FXTrayIcon trayIcon;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tray Icon Example");
        FXTrayIcon icon = new FXTrayIcon(primaryStage, ResourceUtil.toImage("pic/jv.png"));
        icon.addExitItem("exit");
        icon.addSeparator();
        icon.show();
        primaryStage.show();
    }


}
