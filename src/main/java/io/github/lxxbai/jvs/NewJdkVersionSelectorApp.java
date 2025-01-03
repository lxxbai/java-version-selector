package io.github.lxxbai.jvs;

import cn.hutool.extra.spring.EnableSpringUtil;
import io.github.lxxbai.jvs.common.util.*;
import io.github.lxxbai.jvs.config.GlobalExceptionHandler;
import io.github.lxxbai.jvs.spring.AbstractJavaFxApplicationSupport;
import io.github.lxxbai.jvs.spring.GUIState;
import io.github.lxxbai.jvs.view.NewJvsMainView;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author lxxbai
 */
@Slf4j
@MapperScan(basePackages = "io.github.lxxbai.jvs.datasource.mapper")
@EnableSpringUtil
@SpringBootApplication(scanBasePackages = "io.github.lxxbai.jvs")
public class NewJdkVersionSelectorApp extends AbstractJavaFxApplicationSupport {

    public static void main(String[] args) throws Exception {
        //全局异常处理
        Thread.setDefaultUncaughtExceptionHandler(new GlobalExceptionHandler());
        setErrorAction(t -> {
            log.error("异常:", t);
            JFXMsgAlertUtil.showError(GUIState.getStage(), "程序出现异常", t.getMessage());
        });
        launch(NewJdkVersionSelectorApp.class, NewJvsMainView.class, args);

    }

    @Override
    public void init() throws Exception {
        // 初始化
        AppInitUtil.initDb();
        super.init();
    }

    @Override
    public void beforeInitialView(final Stage stage, final ConfigurableApplicationContext ctx) {
        //初始化数据库 ,后面改成异步或者是springboot后置初始化
        AppInitUtil.initUserData();
        // 设置 Stage 的位置，使其居中
        ScreenUtil.setScreenPosition(stage, 0.6, 0.6);
        // 创建系统托盘
        TrayUtil.createTrayIcon(stage);
        //设置全局样式
        setGlobalStyle(ResourceUtil.toExternalForm("css/pink-theme.css"));
        GUIState.getStage().setTitle("Jdk版本选择器");
    }
}