
package io.github.lxxbai.javaversionselector.component;

import cn.hutool.core.util.ReflectUtil;
import com.jfoenix.controls.JFXDecorator;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Window Decorator allow to resize/move its content Note: the default close button will call
 * stage.close() which will only close the current stage. it will not close the java application,
 * however it can be customized by calling {@link #setOnCloseButtonAction(Runnable)}
 *
 * @author lxxbai
 * @version 1.0
 * @since 2024-10-25
 */
public class LJFXDecorator extends JFXDecorator {


    private final HBox newButtonsContainer;


    public LJFXDecorator(Stage stage, Node node) {
        this(stage, node, true, true, true);
    }

    public LJFXDecorator(Stage stage, Node node, boolean fullScreen, boolean max, boolean min) {
        super(stage, node, fullScreen, max, min);
        this.newButtonsContainer = (HBox) ReflectUtil.getFieldValue(this, "buttonsContainer");
    }


    /**
     * 添加按钮
     *
     * @param node  节点
     * @param index 第几个 第0个是图标,第一个是全屏(开启时)
     */
    public void addButton(Node node, int index) {
        //判断fullScreen是否开启
        if (!isCustomMaximize()) {
            index += 1;
        }
        newButtonsContainer.getChildren().add(index, node);
    }
}
