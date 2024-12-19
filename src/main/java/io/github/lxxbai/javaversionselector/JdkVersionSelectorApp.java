package io.github.lxxbai.javaversionselector;

import cn.hutool.extra.spring.EnableSpringUtil;
import io.github.lxxbai.javaversionselector.common.util.*;
import io.github.lxxbai.javaversionselector.config.GlobalExceptionHandler;
import io.github.lxxbai.javaversionselector.view.JVSMainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lxxbai
 */
@MapperScan(basePackages = "io.github.lxxbai.javaversionselector.datasource.mapper")
@EnableSpringUtil
@SpringBootApplication(scanBasePackages = "io.github.lxxbai.javaversionselector")
public class JdkVersionSelectorApp extends Application {

    private static String[] savedArgs = new String[0];

    @Override
    public void start(Stage stage) throws Exception {
        getHostServices().showDocument("https://github.com/lxxbai/java-version-selector");
        //保存stage
        StageUtil.setPrimaryStage(stage);
        StageUtil.setHostServices(getHostServices());
        // 加载主页面
        JVSMainView jvsMainView = new JVSMainView();
        // 设置标题
        stage.setTitle("Jdk版本选择器");
        // 设置图标
        stage.getIcons().add(ResourceUtil.toImage("pic/jv.png"));
        Scene scene = new Scene(jvsMainView);
        scene.getStylesheets().addAll(ResourceUtil.toExternalForm("css/pink-theme.css"));
        // 设置场景
        stage.setScene(scene);
        // 设置 Stage 的位置，使其居中
        ScreenUtil.setScreenPosition(stage, 0.53, 0.56);
        // 创建系统托盘
        TrayUtil.createTrayIcon(stage);
        stage.show();
    }

    @Override
    public void init() throws Exception {
        super.init();
        // 初始化
        AppInitUtil.initDb();
        //springboot加载
        SpringApplication.run(JdkVersionSelectorApp.class, savedArgs);
        //初始化数据库 ,后面改成异步或者是springboot后置初始化
        AppInitUtil.initUserData();
    }

    public static void main(String[] args) throws Exception {
        //全局异常处理
        Thread.setDefaultUncaughtExceptionHandler(new GlobalExceptionHandler());
        savedArgs = args;
        launch(args);
    }
}