package io.github.lxxbai.javaversionselector.view;

import io.github.lxxbai.javaversionselector.common.factory.JavaVersionStatusFactory;
import io.github.lxxbai.javaversionselector.model.JavaVersionVO;
import jakarta.annotation.Resource;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.stereotype.Component;


/**
 * @author lxxbai
 */
@Component
public class DownloadView {

    @Resource
    private DownloadViewModel downloadViewModel;

    @FXML
    private TableView<JavaVersionVO> tableView;
    @FXML
    private TableColumn<JavaVersionVO, String> vmVendor;
    @FXML
    private TableColumn<JavaVersionVO, String> mainVersion;
    @FXML
    private TableColumn<JavaVersionVO, String> javaVersion;
    @FXML
    private TableColumn<JavaVersionVO, String> fileName;
    @FXML
    private TableColumn<JavaVersionVO, String> fileSize;
    @FXML
    private TableColumn<JavaVersionVO, String> action;
    @FXML
    private TableColumn<JavaVersionVO, String> status;

    @FXML
    public void initialize() throws Exception {
        ReadOnlyDoubleProperty width = tableView.widthProperty();
        //设置百分比宽度
        vmVendor.prefWidthProperty().bind(width.multiply(.1));
        mainVersion.prefWidthProperty().bind(width.multiply(.1));
        javaVersion.prefWidthProperty().bind(width.multiply(.1));
        fileName.prefWidthProperty().bind(width.multiply(.3));
        fileSize.prefWidthProperty().bind(width.multiply(.15));
        action.prefWidthProperty().bind(width.multiply(.1));
        status.prefWidthProperty().bind(width.multiply(.15));
        vmVendor.setCellValueFactory(new PropertyValueFactory<>("vmVendor"));
        mainVersion.setCellValueFactory(new PropertyValueFactory<>("mainVersion"));
        javaVersion.setCellValueFactory(new PropertyValueFactory<>("javaVersion"));
        fileName.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        fileSize.setCellValueFactory(new PropertyValueFactory<>("fileSize"));
        action.setCellFactory(new JavaVersionStatusFactory());
        //tableView.setItems(newJavaVersionViewModel.getJavaVersionList());
    }
}