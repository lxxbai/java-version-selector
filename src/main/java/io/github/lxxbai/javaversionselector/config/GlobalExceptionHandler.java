package io.github.lxxbai.javaversionselector.config;

import io.github.lxxbai.javaversionselector.common.util.JFXAlertUtil;
import io.github.lxxbai.javaversionselector.common.util.StageUtil;
import javafx.application.Platform;
import lombok.extern.slf4j.Slf4j;

/**
 * 全局异常处理器
 *
 * @author lxxbai
 */
@Slf4j
public class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error("异常:", e);
        Platform.runLater(() -> JFXAlertUtil.showError(StageUtil.getPrimaryStage(), "程序出现异常", e.getMessage()));
    }
}
