package io.github.lxxbai.javaversionselector.config;

import io.github.lxxbai.javaversionselector.common.exception.ClientException;
import io.github.lxxbai.javaversionselector.common.util.DialogUtils;
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
        if (e instanceof ClientException) {
            Platform.runLater(() -> DialogUtils.showErrorDialog("错误", "程序出现异常", e.getMessage()));
        } else {
            Platform.runLater(() -> DialogUtils.showErrorDialog("程序异常", "程序出现异常", e.getMessage()));
            System.exit(1);
        }
    }
}
