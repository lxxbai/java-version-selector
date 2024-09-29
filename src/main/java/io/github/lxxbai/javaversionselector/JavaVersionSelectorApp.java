package io.github.lxxbai.javaversionselector;

import cn.hutool.extra.spring.EnableSpringUtil;
import io.github.lxxbai.javaversionselector.common.util.ResourceUtil;
import io.github.lxxbai.javaversionselector.common.util.ScreenUtil;
import io.github.lxxbai.javaversionselector.common.util.TrayUtil;
import io.github.lxxbai.javaversionselector.view.JVSMainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lxxbai
 */
@EnableSpringUtil
@SpringBootApplication(scanBasePackages = "io.github.lxxbai.javaversionselector")
public class JavaVersionSelectorApp extends Application {

    private static String[] savedArgs = new String[0];

    @Override
    public void start(Stage stage) throws Exception {
        // 加载主页面
        JVSMainView jVSMainView = new JVSMainView();
        // 设置标题
        stage.setTitle("Java Version Selector");
        // 设置图标
        stage.getIcons().add(new Image(ResourceUtil.toExternalForm("pic/jv.png")));
        // 设置场景
        stage.setScene(new Scene(jVSMainView));
        // 设置 Stage 的位置，使其居中
        ScreenUtil.setScreenPosition(stage, 0.6, 0.6);
        // 创建系统托盘
        TrayUtil.createTrayIcon(stage);
        // 设置窗口关闭事件
        stage.setOnCloseRequest(event -> {
            // 取消关闭事件
            event.consume();
            // 隐藏窗口
            stage.hide();
        });
        stage.show();
    }

    @Override
    public void init() throws Exception {
        super.init();
        //spring加载
        SpringApplication.run(JavaVersionSelectorApp.class, savedArgs);
    }

    public static void main(String[] args) throws Exception {
        savedArgs = args;
        launch(args);
    }
}