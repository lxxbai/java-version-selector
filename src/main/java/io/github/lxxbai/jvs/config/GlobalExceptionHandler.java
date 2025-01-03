package io.github.lxxbai.jvs.config;

import io.github.lxxbai.jvs.common.util.JFXMsgAlertUtil;
import io.github.lxxbai.jvs.spring.GUIState;
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
        Platform.runLater(() -> JFXMsgAlertUtil.showError(GUIState.getStage(), "程序出现异常", e.getMessage()));
    }
}
