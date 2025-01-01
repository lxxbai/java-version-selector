package io.github.lxxbai.javaversionselector.component;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import javafx.scene.control.ButtonBar;
import javafx.stage.Window;

/**
 * @author lxxbai
 */
public class XxbStaticAlert extends JFXAlert<Boolean> {


    public XxbStaticAlert(Window window, XxbDialogLayout dialogLayout) {
        super(window);
        setContent(dialogLayout);
        //设置关闭按钮事件
        dialogLayout.closeSuperProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                hide();
            }
        });
        setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
        setOverlayClose(false);
        //设置结果转换器,不是退出的就是true
        setResultConverter(button -> button.getButtonData() != ButtonBar.ButtonData.CANCEL_CLOSE);
    }
}
