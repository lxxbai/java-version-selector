package io.github.lxxbai.javaversionselector.view;

import com.jfoenix.controls.JFXListView;
import io.github.lxxbai.javaversionselector.component.menu.MenuCellFactory;
import io.github.lxxbai.javaversionselector.component.menu.MenuPageFactory;
import io.github.lxxbai.javaversionselector.model.MenuPage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;


/**
 * 主页视图 根据GridPane 实现
 *
 * @author wdc
 */
public class JVSMainView extends GridPane {

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
        listView.setMinWidth(150);
        listView.setMaxWidth(150);
        //设置菜单的工厂
        listView.setCellFactory(new MenuCellFactory());
        //首页
        MenuPage homePage = MenuPageFactory.build("Jdk版本", "view/java_version.fxml");
        //下载
        MenuPage downloadPage = MenuPageFactory.build("进度", "view/install_record.fxml");
        //配置
        listView.getItems().addAll(homePage,downloadPage);
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
        GridPane.setVgrow(listView, Priority.ALWAYS);
        //左侧菜单
        this.add(listView, 0, 0);
        //右边内容
        this.add(pane, 1, 0);
    }
}