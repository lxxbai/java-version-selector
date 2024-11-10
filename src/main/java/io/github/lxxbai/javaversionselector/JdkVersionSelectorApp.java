package io.github.lxxbai.javaversionselector;

import cn.hutool.extra.spring.EnableSpringUtil;
import io.github.lxxbai.javaversionselector.common.util.*;
import io.github.lxxbai.javaversionselector.component.LJFXDecorator;
import io.github.lxxbai.javaversionselector.config.GlobalExceptionHandler;
import io.github.lxxbai.javaversionselector.model.ViewResult;
import io.github.lxxbai.javaversionselector.view.JVSMainView;
import io.github.lxxbai.javaversionselector.view.SettingsView;
import javafx.application.Application;
import javafx.scene.Node;
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
        //保存stage
        StageUtil.setPrimaryStage(stage);
        // 加载主页面
        JVSMainView jvsMainView = new JVSMainView();
        // 设置标题
        stage.setTitle("Jdk Version Selector");
        // 设置图标
        stage.getIcons().add(ResourceUtil.toImage("pic/jv.png"));
        // 加载配置
        ViewResult<SettingsView, Node> settingsViewResult = FXMLLoaderUtil.loadFxView(SettingsView.class);
        // 使用 JFXDecorator 包装主内容和标题栏按钮
        LJFXDecorator decorator = new LJFXDecorator(stage, jvsMainView);
        // 创建自定义按钮
        // 创建自定义按钮
        decorator.addButton(JFXButtonUtil.buildScanSvgButton("svg/java-solid.svg", "扫描本地JDK"), 1);
        decorator.addButton(settingsViewResult.getController().buildConfigButton(), 2);
        Scene scene = new Scene(decorator);
        scene.getStylesheets().addAll(
                ResourceUtil.toExternalForm("css/jf-all.css")
//                BootstrapFX.bootstrapFXStylesheet()
        );
        // 设置场景
        stage.setScene(scene);
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