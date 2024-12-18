package io.github.lxxbai.javaversionselector.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import io.github.lxxbai.javaversionselector.common.annotations.base.FXView;
import io.github.lxxbai.javaversionselector.component.SvgButton;
import io.github.lxxbai.javaversionselector.component.cell.XxbTableCellFactory;
import io.github.lxxbai.javaversionselector.model.JdkVersionVO;
import jakarta.annotation.Resource;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.springframework.stereotype.Component;

import java.awt.event.MouseEvent;


/**
 * @author lxxbai
 */
@FXView(url = "view/java_version.fxml")
@Component
public class JdkVersionView {

    @Resource
    private JdkVersionViewModel jdkVersionViewModel;
    @Resource
    private InstallViewModel installViewModel;
    @FXML
    private JFXTextField filterJavaVersion;
    @FXML
    private JFXComboBox<String> filterMainVersion;
    @FXML
    private JFXComboBox<String> filterVmVendor;
    @FXML
    private TableView<JdkVersionVO> tableView;
    @FXML
    private TableColumn<JdkVersionVO, String> vmVendor;
    @FXML
    private TableColumn<JdkVersionVO, String> mainVersion;
    @FXML
    private TableColumn<JdkVersionVO, String> javaVersion;
    @FXML
    private TableColumn<JdkVersionVO, String> releaseDate;
    @FXML
    private TableColumn<JdkVersionVO, String> fileName;
    @FXML
    private TableColumn<JdkVersionVO, String> fileSize;

    @FXML
    public void initialize() {
        //绑定数据
        filterJavaVersion.textProperty().bindBidirectional(jdkVersionViewModel.getFilterJavaVersion());
        filterMainVersion.valueProperty().bindBidirectional(jdkVersionViewModel.getFilterMainVersion());
        filterVmVendor.valueProperty().bindBidirectional(jdkVersionViewModel.getFilterVmVendor());
        //变更事件
        filterJavaVersion.textProperty().addListener(str -> jdkVersionViewModel.filter());
        filterVmVendor.valueProperty().addListener(str -> jdkVersionViewModel.filter());
        filterMainVersion.valueProperty().addListener(str -> jdkVersionViewModel.filter());
        vmVendor.setCellValueFactory(new PropertyValueFactory<>("vmVendor"));
        mainVersion.setCellValueFactory(new PropertyValueFactory<>("mainVersion"));
        javaVersion.setCellValueFactory(new PropertyValueFactory<>("javaVersion"));
        fileName.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        fileSize.setCellValueFactory(new PropertyValueFactory<>("fileSize"));
        tableView.setItems(jdkVersionViewModel.getJavaVersionList());
        filterVmVendor.setItems(jdkVersionViewModel.getVmVendorList());
        filterMainVersion.setItems(jdkVersionViewModel.getMainVersionList());
//        releaseDate.setCellFactory(buildCellFactory());

//        releaseDate.setCellFactory(new Callback<>() {
//            @Override
//            public TableCell<JdkVersionVO, String> call(TableColumn<JdkVersionVO, String> jdkVersionVOStringTableColumn) {
//                return new TableCell<>() {
//
//                    @Override
//                    protected void updateItem(String item, boolean empty) {
//                        super.updateItem(item, empty);
//                        if (empty) {
//                            setText(null);
//                            setGraphic(null);
//                            return;
//                        }
//                        JdkVersionVO jdkVersionVO = getTableView().getItems().get(getIndex());
//                        TableRow<JdkVersionVO> tableRow = getTableRow();
//                        tableRow.setOnMouseEntered(e -> {
//                            JFXButton downloadButton = new SvgButton("svg/circle-down-regular.svg", 18, 18, "下载");
//                            setText(null);
//                            setGraphic(downloadButton);
//                        });
//                        tableRow.setOnMouseExited(e -> {
//                            setGraphic(null);
//                            setText(jdkVersionVO.getReleaseDate());
//                        });
//                    }
//                };
//            }
//        });

        tableView.setRowFactory(new Callback<TableView<JdkVersionVO>, TableRow<JdkVersionVO>>() {
            @Override
            public TableRow<JdkVersionVO> call(TableView<JdkVersionVO> jdkVersionVOTableView) {
                TableRow<JdkVersionVO> objectTableRow = new TableRow<>();
                objectTableRow.setOnMouseEntered(e->{
                    JFXButton downloadButton = new SvgButton("svg/circle-down-regular.svg", 18, 18, "下载");
                    downloadButton.setOnAction(event -> installViewModel.download(jdkVersionVOTableView.getSelectionModel().getSelectedItem()));
                    objectTableRow.setGraphic(downloadButton);
//                    System.out.println();
                });

                return objectTableRow;
            }
        });
    }

    /**
     * 下载替换发布日期的cell
     *
     * @return 表格列工厂
     */
    private XxbTableCellFactory<JdkVersionVO, String> buildCellFactory() {
        return XxbTableCellFactory.cellFactory(cell -> {
            JdkVersionVO jdkVersion = cell.getData();
            TableRow<JdkVersionVO> tableRow = cell.getTableRow();
            tableRow.setOnMouseEntered(e -> {
                        JFXButton downloadButton = new SvgButton("svg/circle-down-regular.svg", 18, 18, "下载");
                        //下载
                        downloadButton.setOnAction(event -> installViewModel.download(jdkVersion));
                        cell.setText(null);
                        cell.setGraphic(downloadButton);
                    }
            );
            tableRow.setOnMouseExited(e -> {
                cell.setGraphic(null);
                cell.setText(jdkVersion.getReleaseDate());
            });
            cell.setText(jdkVersion.getReleaseDate());
        });
    }


    @FXML
    private void onUpdateDataButtonClick() {
        jdkVersionViewModel.refresh();
    }

    @FXML
    private void resetFilter() {
        jdkVersionViewModel.resetFilter();
    }
}