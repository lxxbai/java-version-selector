package io.github.lxxbai.javaversionselector.common.factory;

import cn.hutool.core.util.StrUtil;
import com.jfoenix.controls.JFXButton;
import io.github.lxxbai.javaversionselector.common.enums.StatusEnum;
import io.github.lxxbai.javaversionselector.common.util.ResourceUtil;
import io.github.lxxbai.javaversionselector.model.UserJavaVersion;
import io.github.lxxbai.javaversionselector.view.JavaVersionViewModel;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.util.List;

public class VersionActionFactory implements Callback<TableColumn<UserJavaVersion, String>, TableCell<UserJavaVersion, String>> {

    /**
     * 数据管理器
     */
    private final JavaVersionViewModel javaVersionViewModel;

    public VersionActionFactory(JavaVersionViewModel javaVersionViewModel) {
        this.javaVersionViewModel = javaVersionViewModel;
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
                StatusEnum status = userJavaVersion.getStatus();
                switch (status) {
                    case NOT_INSTALLED, INSTALLED_FAILURE, INSTALLED_PAUSE -> {
                        JFXButton button = new JFXButton();
                        button.setText("安装");
                        button.setOnAction(event -> installDialog(getTableView().getItems(), getIndex()));
                        setGraphic(button);
                    }
                    case INSTALLED -> {
                        // 创建下拉菜单
                        MenuButton contextMenu = createContextMenu(List.of("应用", "卸载"), getIndex());
                        setGraphic(contextMenu);
                    }
                    case CURRENT -> {
                        JFXButton button = new JFXButton();
                        button.setText("卸载");
                        //todo 添加弹框
                        button.setOnAction(event -> javaVersionViewModel.unInstall(getIndex()));
                        setGraphic(button);
                    }
                    case INSTALLING -> {
                        JFXButton button = new JFXButton();
                        button.setText("暂停");
                        button.setOnAction(event -> javaVersionViewModel.downloadStatusChange(getIndex(),
                                StatusEnum.INSTALLED_PAUSE));
                        setGraphic(button);
                    }
                    default -> setText(null);
                }
            }
        };
    }


    /**
     * 安装对话框
     *
     * @param dataList 版本
     * @param index    下标
     */
    private void installDialog(ObservableList<UserJavaVersion> dataList, int index) {
        UserJavaVersion version = dataList.get(index);
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
                javaVersionViewModel.downloadStatusChange(index, StatusEnum.INSTALLING);
            }
        });
    }


    /**
     * 创建下拉菜单
     *
     * @param contents 菜单内容
     * @param index    版本信息
     * @return 菜单
     */
    private MenuButton createContextMenu(List<String> contents, int index) {
        MenuButton contextMenu = new MenuButton("更多");
        List<MenuItem> items = contents.stream().map(content -> {
            MenuItem menuItem = new MenuItem(content);
            menuItem.setOnAction(event -> {
                if (StrUtil.equals("应用", content)) {
                    javaVersionViewModel.apply(index);
                } else {
                    //卸载
                    javaVersionViewModel.unInstall(index);
                }
            });
            return menuItem;
        }).toList();
        // 创建选项菜单项
        contextMenu.getItems().addAll(items);
        return contextMenu;
    }
}
