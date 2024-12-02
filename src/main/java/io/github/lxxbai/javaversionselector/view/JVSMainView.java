package io.github.lxxbai.javaversionselector.view;

import com.jfoenix.controls.JFXListView;
import io.github.lxxbai.javaversionselector.component.menu.MenuCellFactory;
import io.github.lxxbai.javaversionselector.component.menu.MenuPageFactory;
import io.github.lxxbai.javaversionselector.model.MenuPage;
import javafx.scene.layout.*;


/**
 * 主页视图 根据BorderPane 实现
 *
 * @author wdc
 */
public class JVSMainView extends BorderPane {

    /**
     * 右侧内容
     */
    private Pane pane;

    public JVSMainView() {
        init();
    }

    /**
     * 初始化内容
     */
    private void init() {
        //左侧菜单
        JFXListView<MenuPage> listView = new JFXListView<>();
        listView.setMinWidth(60);
        listView.setMaxWidth(60);
        //设置菜单的工厂
        listView.setCellFactory(new MenuCellFactory());
        //首页
        MenuPage homePage = MenuPageFactory.build("版本", "svg/home.svg", JdkVersionView.class);
        //下载
        MenuPage downloadPage = MenuPageFactory.build("进度", "svg/download-solid.svg", InstallView.class);
        //我的
        MenuPage myPage = MenuPageFactory.build("我的", "svg/user-large-solid.svg", UserJdkView.class);
        //配置
        listView.getItems().addAll(homePage, downloadPage, myPage);
        //左边菜单
        pane = new StackPane();
        GridPane.setHgrow(pane, Priority.ALWAYS);
        GridPane.setVgrow(pane, Priority.ALWAYS);
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