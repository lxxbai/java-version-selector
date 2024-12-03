package io.github.lxxbai.javaversionselector.view;

import com.jfoenix.controls.JFXListView;
import io.github.lxxbai.javaversionselector.component.menu.MenuCellFactory;
import io.github.lxxbai.javaversionselector.component.menu.MenuPageFactory;
import io.github.lxxbai.javaversionselector.model.MenuPage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;


/**
 * 主页视图 根据BorderPane 实现
 *
 * @author wdc
 */
public class JVSMainView extends BorderPane {

    public JVSMainView() {
        init();
    }

    /**
     * 初始化内容
     */
    private void init() {
        getStyleClass().add("jvs-main");
        //左侧菜单
        JFXListView<MenuPage> listView = new JFXListView<>();
        listView.setMinWidth(50);
        listView.setMaxWidth(50);
        //设置菜单的工厂
        listView.setCellFactory(new MenuCellFactory());
        listView.getStyleClass().add("main-left");
        listView.setShowTooltip(true);
        //首页
        MenuPage homePage = MenuPageFactory.build("版本", "svg/home.svg", JdkVersionView.class);
        //下载
        MenuPage downloadPage = MenuPageFactory.build("进度", "svg/download-solid.svg", InstallView.class);
        //我的
        MenuPage myPage = MenuPageFactory.build("我的", "svg/user-large-solid.svg", UserJdkView.class);
        //配置
        listView.getItems().addAll(homePage, downloadPage, myPage);
        //左边菜单
        Pane pane = new StackPane();
        pane.getStyleClass().add("main-center");
        //菜单切换
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                return;
            }
            pane.getChildren().clear();
            pane.getChildren().setAll(newValue.getContent());
        });
        //默认选中
        listView.getSelectionModel().select(homePage);
        setCenter(pane);
        setLeft(listView);
    }
}