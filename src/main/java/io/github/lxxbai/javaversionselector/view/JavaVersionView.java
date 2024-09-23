package io.github.lxxbai.javaversionselector.view;

import io.github.lxxbai.javaversionselector.common.factory.VersionActionFactory;
import io.github.lxxbai.javaversionselector.common.factory.VersionStatusCellFactory;
import io.github.lxxbai.javaversionselector.model.UserJavaVersion;
import jakarta.annotation.Resource;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.stereotype.Component;


@Component
public class JavaVersionView {

    @Resource
    private JavaVersionViewModel javaVersionViewModel;
    @FXML
    private TableView<UserJavaVersion> tableView;
    @FXML
    private TableColumn<UserJavaVersion, String> versionColumn;
    @FXML
    private TableColumn<UserJavaVersion, String> releaseDateColumn;
    @FXML
    private TableColumn<UserJavaVersion, String> statusColumn;
    @FXML
    private TableColumn<UserJavaVersion, String> actionColumn;
    @FXML
    private TextField filterTextField;

    @FXML
    public void initialize() throws Exception {
        ReadOnlyDoubleProperty width = tableView.widthProperty();
        //设置百分比宽度
        versionColumn.prefWidthProperty().bind(width.multiply(.2));
        releaseDateColumn.prefWidthProperty().bind(width.multiply(.3));
        statusColumn.prefWidthProperty().bind(width.multiply(.3));
        actionColumn.prefWidthProperty().bind(width.multiply(.2));
        filterTextField.textProperty().bindBidirectional(javaVersionViewModel.filterTextProperty());
        filterTextField.setOnKeyPressed(keyEvent -> javaVersionViewModel.filter());
        filterTextField.textProperty().addListener(str -> javaVersionViewModel.filter());
        versionColumn.setCellValueFactory(new PropertyValueFactory<>("version"));
        releaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        statusColumn.setCellFactory(new VersionStatusCellFactory(javaVersionViewModel));
        // 设置操作列的单元格工厂
        actionColumn.setCellFactory(new VersionActionFactory(javaVersionViewModel));
        //设置数据源
        tableView.setItems(javaVersionViewModel.getUserVersionList());
    }


    @FXML
    private void onUpdateDataButtonClick() {
        javaVersionViewModel.refresh();
    }
}