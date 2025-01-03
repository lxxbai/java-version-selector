package io.github.lxxbai.jvs.view;

import cn.hutool.extra.spring.SpringUtil;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import io.github.lxxbai.jvs.common.util.AppContextUtil;
import io.github.lxxbai.jvs.common.util.StageUtil;
import io.github.lxxbai.jvs.component.menu.MenuCellFactory;
import io.github.lxxbai.jvs.component.menu.MenuPage;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.util.Comparator;
import java.util.List;
import java.util.Map;


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
        //获取菜单
        Map<String, MenuContentView> beans = SpringUtil.getBeansOfType(MenuContentView.class);
        //排序
        List<MenuPage> viewList = beans.values().stream().map(MenuContentView::toMenuPage)
                .sorted(Comparator.comparingInt(MenuPage::getOrder))
                .toList();
        //左侧菜单和内容
        JFXListView<MenuPage> listView = new JFXListView<>();
        listView.setMinWidth(55);
        listView.setMaxWidth(55);
        //设置菜单的工厂
        listView.setCellFactory(new MenuCellFactory());
        listView.getStyleClass().add("main-left");
        listView.setShowTooltip(true);
        //禁止焦点,不然好像有些问题
        listView.setFocusTraversable(false);
        listView.getItems().addAll(viewList);
        //配置
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(0, 10, 0, 10));
        JFXSnackbar snackbar = new JFXSnackbar(borderPane);
        //设置snackbar
        AppContextUtil.setSnackbar(snackbar);
        DecoratorView pane = new DecoratorView(StageUtil.getPrimaryStage(), borderPane);
        pane.getStyleClass().add("main-center");
        //菜单切换
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            snackbar.fireEvent(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout("版本:17.0.4下载完成"), Duration.seconds(10)));
            if (newValue == null) {
                return;
            }
            borderPane.setCenter(newValue.getContent());
        });
        //默认选中
        listView.getSelectionModel().select(viewList.get(0));
        setCenter(pane);
        setLeft(listView);
    }
}