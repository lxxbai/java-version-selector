package io.github.lxxbai.javaversionselector.common.factory;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.jfoenix.controls.JFXButton;
import io.github.lxxbai.javaversionselector.common.enums.VersionActionEnum;
import io.github.lxxbai.javaversionselector.common.enums.VersionStatusEnum;
import io.github.lxxbai.javaversionselector.common.util.ResourceUtil;
import io.github.lxxbai.javaversionselector.event.StatusChangeEvent;
import io.github.lxxbai.javaversionselector.model.UserJavaVersion;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * 操作按钮
 *
 * @author lxxbai
 */
public class VersionActionFactory implements Callback<TableColumn<UserJavaVersion, String>, TableCell<UserJavaVersion, String>> {

    /**
     * 数据管理器
     */
    private final ApplicationContext applicationContext;

    public VersionActionFactory() {
        this.applicationContext = SpringUtil.getApplicationContext();
    }

    @Override
    public TableCell<UserJavaVersion, String> call(TableColumn<UserJavaVersion, String> tableColumn) {
        return new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                    return;
                }
                UserJavaVersion userJavaVersion = getTableView().getItems().get(getIndex());
                String version = userJavaVersion.getVersion();
                VersionStatusEnum status = userJavaVersion.getStatus();
                switch (status) {
                    case NOT_INSTALLED -> {
                        JFXButton button = new JFXButton();
                        button.setText("安装");
                        button.setOnAction(event -> installDialog(userJavaVersion));
                        setGraphic(button);
                    }
                    case DOWNLOAD_FAILURE, UNZIPPING_FAILURE, CONFIGURING_FAILURE -> {
                        JFXButton button = new JFXButton();
                        button.setText("重新安装");
                        button.setOnAction(event -> publishEvent(version, VersionActionEnum.REINSTALL));
                        setGraphic(button);
                    }
                    case INSTALLED -> {
                        // 创建下拉菜单
                        MenuButton contextMenu = createContextMenu(List.of("应用", "卸载"), version);
                        setGraphic(contextMenu);
                    }
                    case CURRENT -> {
                        JFXButton button = new JFXButton();
                        button.setText("卸载");
                        //todo 添加弹框
                        button.setOnAction(event -> publishEvent(version, VersionActionEnum.UNINSTALL));
                        setGraphic(button);
                    }
                    case DOWNLOADING -> {
                        JFXButton button = new JFXButton();
                        button.setText("取消");
                        button.setOnAction(event -> publishEvent(version, VersionActionEnum.CANCEL));
                        setGraphic(button);
                    }
                    case UNZIPPING, CONFIGURING, UNINSTALLING, APPLYING -> {
                        JFXButton button = new JFXButton();
                        button.setText("处理中");
                        button.setDisable(true);
                        setGraphic(button);
                    }
                    default -> {
                        JFXButton button = new JFXButton();
                        button.setText("继续安装");
                        button.setOnAction(event -> publishEvent(version, VersionActionEnum.CONTINUE_INSTALL));
                        setGraphic(button);
                    }
                }
            }
        };
    }


    private void publishEvent(String version, VersionActionEnum event) {
        applicationContext.publishEvent(new StatusChangeEvent(version, event));
    }

    /**
     * 安装对话框
     *
     * @param version 版本
     */
    private void installDialog(UserJavaVersion version) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("确认操作");
        alert.setHeaderText("版本 " + version.getVersion() + " 是否安装？");
        alert.setContentText("点击 开始安装 按钮开始下载安装。");
        // 自定义样式
        alert.getDialogPane().getStylesheets().add(ResourceUtil.getUrl("css/dialog.css").toExternalForm());
        // 添加自定义按钮
        ButtonType yesButton = new ButtonType("开始安装");
        ButtonType noButton = new ButtonType("取消");
        alert.getButtonTypes().setAll(yesButton, noButton);
        // 处理用户的选择
        alert.showAndWait().ifPresent(response -> {
            if (response == yesButton) {
                //显示进度条
                publishEvent(version.getVersion(), VersionActionEnum.INSTALL);
            }
        });
    }


    /**
     * 创建下拉菜单
     *
     * @param contents 菜单内容
     * @param version  版本信息
     * @return 菜单
     */
    private MenuButton createContextMenu(List<String> contents, String version) {
        MenuButton contextMenu = new MenuButton("更多");
        List<MenuItem> items = contents.stream().map(content -> {
            MenuItem menuItem = new MenuItem(content);
            menuItem.setOnAction(event -> {
                if (StrUtil.equals("应用", content)) {
                    publishEvent(version, VersionActionEnum.APPLY);
                } else {
                    //卸载
                    publishEvent(version, VersionActionEnum.UNINSTALL);
                }
            });
            return menuItem;
        }).toList();
        // 创建选项菜单项
        contextMenu.getItems().addAll(items);
        return contextMenu;
    }
}
