package io.github.lxxbai.javaversionselector.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import io.github.lxxbai.javaversionselector.common.annotations.base.FXView;
import io.github.lxxbai.javaversionselector.common.enums.InstallStatusEnum;
import io.github.lxxbai.javaversionselector.common.util.JFXButtonUtil;
import io.github.lxxbai.javaversionselector.component.cell.GraphicTableCellFactory;
import io.github.lxxbai.javaversionselector.model.JdkVersionVO;
import jakarta.annotation.Resource;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.springframework.stereotype.Component;


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
    private TableColumn<JdkVersionVO, String> action;
    @FXML
    private JFXButton resetButton;
    @FXML
    private JFXButton refreshButton;

    @FXML
    public void initialize() {
        //禁用焦点
        ReadOnlyDoubleProperty width = tableView.widthProperty();
//        tableView.
        //设置百分比宽度
        vmVendor.prefWidthProperty().bind(width.multiply(.1));
        mainVersion.prefWidthProperty().bind(width.multiply(.1));
        javaVersion.prefWidthProperty().bind(width.multiply(.1));
        releaseDate.prefWidthProperty().bind(width.multiply(.142));
        fileName.prefWidthProperty().bind(width.multiply(.3));
        fileSize.prefWidthProperty().bind(width.multiply(.15));
        action.prefWidthProperty().bind(width.multiply(.1));
        //绑定数据
        filterJavaVersion.textProperty().bindBidirectional(jdkVersionViewModel.getFilterJavaVersion());
        filterMainVersion.valueProperty().bindBidirectional(jdkVersionViewModel.getFilterMainVersion());
        filterVmVendor.valueProperty().bindBidirectional(jdkVersionViewModel.getFilterVmVendor());
        //变更事件
        filterJavaVersion.textProperty().addListener(str -> jdkVersionViewModel.filter());
        filterJavaVersion.setOnKeyPressed(keyEvent -> jdkVersionViewModel.filter());
        filterJavaVersion.textProperty().addListener(str -> jdkVersionViewModel.filter());
        filterVmVendor.valueProperty().addListener(str -> jdkVersionViewModel.filter());
        filterMainVersion.valueProperty().addListener(str -> jdkVersionViewModel.filter());
        vmVendor.setCellValueFactory(new PropertyValueFactory<>("vmVendor"));
        mainVersion.setCellValueFactory(new PropertyValueFactory<>("mainVersion"));
        javaVersion.setCellValueFactory(new PropertyValueFactory<>("javaVersion"));
        releaseDate.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        fileName.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        fileSize.setCellValueFactory(new PropertyValueFactory<>("fileSize"));
        action.setCellFactory(buildActionCellFactory());
        tableView.setItems(jdkVersionViewModel.getJavaVersionList());
        filterVmVendor.setItems(jdkVersionViewModel.getVmVendorList());
        filterMainVersion.setItems(jdkVersionViewModel.getMainVersionList());
        JFXButtonUtil.fullSvg(resetButton, "svg/rotate-solid.svg", "重置");
        JFXButtonUtil.fullSvg(refreshButton, "svg/rotate-solid.svg", "刷新");
    }


    /**
     * 获取下载状态列的工厂
     *
     * @return 表格列工厂
     */
    private GraphicTableCellFactory<JdkVersionVO, String> buildActionCellFactory() {
        //设置单元格工厂,只有不是安装中的时候才显示下载按钮
        return GraphicTableCellFactory.withGraphicFunc(cellData -> {
            JdkVersionVO vo = cellData.getData();
            InstallStatusEnum downloadStatus = vo.getInstallStatus();
            JFXButton downloadButton;
            if (downloadStatus == InstallStatusEnum.DOWNLOADING) {
                downloadButton = JFXButtonUtil.buildDynamicButton("pic/downloading.gif");
                downloadButton.setDisable(true);
            } else {
                downloadButton = JFXButtonUtil.buildSvgButton("svg/circle-down-regular.svg");
                //下载
                downloadButton.setOnAction(event -> installViewModel.download(vo));
            }
            return downloadButton;
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



    // 定义自定义的 ColumnResizePolicy
    public static class CustomColumnResizePolicy<T> implements Callback<TableView.ResizeFeatures<T>, Boolean> {

        private final TableView<T> tableView;

        public CustomColumnResizePolicy(TableView<T> tableView) {
            this.tableView = tableView;
        }

        @Override
        public Boolean call(TableView.ResizeFeatures<T> features) {
            if (features == null || features.getColumn() == null) {
                return false;
            }
            TableColumn<T, ?> column = features.getColumn();
            double delta = features.getDelta(); // 调整的距离
            double newWidth = column.getWidth() + delta;

            // 确保列宽不小于最小宽度（例如 50 像素）
            double minWidth = 50.0;
            if (newWidth < minWidth) {
                newWidth = minWidth;
            }

            // 确保列宽不大于最大宽度（例如 300 像素）
            double maxWidth = 300.0;
            if (newWidth > maxWidth) {
                newWidth = maxWidth;
            }
            // 更新列宽
            column.setPrefWidth(newWidth);
            // 如果是最后一列，确保它占据剩余的空间
            if (column == tableView.getColumns().get(tableView.getColumns().size() - 1)) {
                adjustLastColumnWidth(tableView);
            }
            return true;
        }

        private void adjustLastColumnWidth(TableView<T> tableView) {
            // 获取最后一列
            TableColumn<T, ?> lastColumn = tableView.getColumns().get(tableView.getColumns().size() - 1);

            // 计算剩余空间
            double totalWidth = tableView.getWidth();
            double usedWidth = tableView.getColumns().stream()
                    .filter(col -> col != lastColumn)
                    .mapToDouble(TableColumn::getWidth)
                    .sum();

            // 设置最后一列的宽度为剩余空间
            double remainingWidth = Math.max(totalWidth - usedWidth, 50.0); // 最小宽度为 50 像素
            lastColumn.setPrefWidth(remainingWidth);
        }
    }
}