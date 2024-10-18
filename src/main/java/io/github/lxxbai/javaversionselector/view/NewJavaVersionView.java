package io.github.lxxbai.javaversionselector.view;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
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
public class NewJavaVersionView {
    @Resource
    private NewJavaVersionViewModel newJavaVersionViewModel;
    @FXML
    private JFXTextField filterJavaVersion;
    @FXML
    public JFXComboBox<String> filterMainVersion;
    @FXML
    public JFXComboBox<String> filterVmVendor;
    @FXML
    private TableView<JavaVersionVO> tableView;
    @FXML
    public TableColumn<JavaVersionVO, String> vmVendor;
    @FXML
    public TableColumn<JavaVersionVO, String> mainVersion;
    public TableColumn<JavaVersionVO, String> javaVersion;
    @FXML
    public TableColumn<JavaVersionVO, String> releaseDate;
    @FXML
    public TableColumn<JavaVersionVO, String> fileName;
    @FXML
    public TableColumn<JavaVersionVO, String> fileSize;
    @FXML
    public TableColumn<JavaVersionVO, String> action;

    @FXML
    public void initialize() throws Exception {
        ReadOnlyDoubleProperty width = tableView.widthProperty();
        //设置百分比宽度
        vmVendor.prefWidthProperty().bind(width.multiply(.1));
        mainVersion.prefWidthProperty().bind(width.multiply(.1));
        javaVersion.prefWidthProperty().bind(width.multiply(.1));
        releaseDate.prefWidthProperty().bind(width.multiply(.15));
        fileName.prefWidthProperty().bind(width.multiply(.3));
        fileSize.prefWidthProperty().bind(width.multiply(.15));
        action.prefWidthProperty().bind(width.multiply(.1));
        //绑定数据
        filterJavaVersion.textProperty().bindBidirectional(newJavaVersionViewModel.getFilterJavaVersion());
        filterMainVersion.valueProperty().bindBidirectional(newJavaVersionViewModel.getFilterMainVersion());
        filterVmVendor.valueProperty().bindBidirectional(newJavaVersionViewModel.getFilterVmVendor());
        //变更事件
        filterJavaVersion.textProperty().addListener(str -> newJavaVersionViewModel.filter());
        filterJavaVersion.setOnKeyPressed(keyEvent -> newJavaVersionViewModel.filter());
        filterJavaVersion.textProperty().addListener(str -> newJavaVersionViewModel.filter());
        filterVmVendor.valueProperty().addListener(str -> newJavaVersionViewModel.filter());
        filterMainVersion.valueProperty().addListener(str -> newJavaVersionViewModel.filter());
        vmVendor.setCellValueFactory(new PropertyValueFactory<>("vmVendor"));
        mainVersion.setCellValueFactory(new PropertyValueFactory<>("mainVersion"));
        javaVersion.setCellValueFactory(new PropertyValueFactory<>("javaVersion"));
        releaseDate.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        fileName.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        fileSize.setCellValueFactory(new PropertyValueFactory<>("fileSize"));
        action.setCellFactory(new JavaVersionStatusFactory());
        tableView.setItems(newJavaVersionViewModel.getJavaVersionList());
        filterVmVendor.setItems(newJavaVersionViewModel.getVmVendorList());
        filterMainVersion.setItems(newJavaVersionViewModel.getMainVersionList());
    }

    @FXML
    private void onUpdateDataButtonClick() {
        newJavaVersionViewModel.refresh();
    }

    @FXML
    private void resetFilter() {
        newJavaVersionViewModel.resetFilter();
    }
}