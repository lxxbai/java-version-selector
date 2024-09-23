package io.github.lxxbai.javaversionselector;

import cn.hutool.extra.spring.EnableSpringUtil;
import io.github.lxxbai.javaversionselector.common.util.FXMLLoaderUtil;
import io.github.lxxbai.javaversionselector.common.util.ScreenUtil;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSpringUtil
@SpringBootApplication(scanBasePackages = "io.github.lxxbai.javaversionselector")
public class JavaVersionSelectorApp1 extends Application {

    private static String[] savedArgs = new String[0];

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent load = FXMLLoaderUtil.load("view/home.fxml");
        primaryStage.setTitle("Java Version Selector");
        primaryStage.getIcons().add(new Image(JavaVersionSelectorApp1.class.getResource("/pic/jv.png").toExternalForm()));
        ScreenUtil.setScreenPosition(primaryStage, 0.6, 0.6);

//        primaryStage.setScene(new Scene(load, windowWidth, windowHeight));
//        // 设置 Stage 的位置，使其居中
//        primaryStage.setX((screenWidth - windowWidth) / 2);
//        primaryStage.setY((screenHeight - windowHeight) / 2);
        primaryStage.setOnCloseRequest(t -> Platform.exit());
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        super.init();
        //spring加载
        SpringApplication.run(JavaVersionSelectorApp1.class, savedArgs);
    }

    public static void main(String[] args) throws Exception {
        savedArgs = args;
        launch(args);
    }
}