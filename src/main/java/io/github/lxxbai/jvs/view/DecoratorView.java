
package io.github.lxxbai.jvs.view;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import io.github.lxxbai.jvs.common.util.FXMLLoaderUtil;
import io.github.lxxbai.jvs.common.util.JFXMsgAlertUtil;
import io.github.lxxbai.jvs.common.util.JFXButtonUtil;
import io.github.lxxbai.jvs.component.jdk.JdkScanner;
import io.github.lxxbai.jvs.manager.UserJdkVersionManager;
import io.github.lxxbai.jvs.model.ViewResult;
import io.github.lxxbai.jvs.spring.GUIState;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;
import java.util.Objects;


/**
 * 标题栏
 *
 * @author lxxbai
 */
public class DecoratorView extends JFXDecorator {

    private final HBox newButtonsContainer;

    public DecoratorView(Stage stage, Node node) {
        this(stage, node, true, true, true);
    }

    public DecoratorView(Stage stage, Node node, boolean fullScreen, boolean max, boolean min) {
        super(stage, node, fullScreen, max, min);
        this.newButtonsContainer = (HBox) ReflectUtil.getFieldValue(this, "buttonsContainer");
        // 加载配置
        ViewResult<SettingsView, Node> settingsViewResult = FXMLLoaderUtil.loadFxView(SettingsView.class);
        SettingsView settingsView = settingsViewResult.getController();
        // 设置按钮
        JFXButton settingButton = settingsView.buildConfigButton();
        // 扫描按钮
        JFXButton scanButton = JFXButtonUtil.buildScanSvgButton("svg/java-solid.svg", "扫描本地JDK");
        // 添加按钮
        addButton(settingButton, 1);
        addButton(scanButton, 2);
        // 扫描本地JDK
        scanButton.setOnAction(even -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("请选择要扫描的文件夹");
            File selectedDirectory = directoryChooser.showDialog(GUIState.getStage().getScene().getWindow());
            if (Objects.isNull(selectedDirectory)) {
                return;
            }
            JdkScanner jdkScanner = new JdkScanner();
            // 扫描
            List<File> files = jdkScanner.scanForJdks(selectedDirectory);
            if (CollUtil.isEmpty(files)) {
                JFXMsgAlertUtil.showWarning(GUIState.getStage(), "告警", "未找到任何Jdk");
                return;
            }
            //将数据插入到数据库中
            UserJdkVersionManager userJdkVersionManager = SpringUtil.getBean(UserJdkVersionManager.class);
            // 插入到数据库中
            files.forEach(file -> userJdkVersionManager.buildUserJdk(file.getAbsolutePath()));
            //跳转到我的JDK页面
            SpringUtil.getBean(UserJdkViewModel.class).refresh();
        });
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