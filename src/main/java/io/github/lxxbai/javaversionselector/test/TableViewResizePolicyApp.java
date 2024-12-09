package io.github.lxxbai.javaversionselector.test;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TableViewResizePolicyApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        TableView<String> tableView = new TableView<>();

        // 创建列并设置初始百分比宽度
        TableColumn<String, String> column1 = new TableColumn<>("Column 1");
        TableColumn<String, String> column2 = new TableColumn<>("Column 2");
        TableColumn<String, String> column3 = new TableColumn<>("Column 3");
        TableColumn<String, String> column4 = new TableColumn<>("Column 3");

        // 设置列的初始比例 (如：30%，40%，30%)
        double[] columnPercentages = {0.1, 0.5, 0.3,0.1};
        TableColumn[] columns = new TableColumn[]{column1, column2, column3};

        // 添加列到表格
        tableView.getColumns().addAll(column1, column2, column3);

        // 设置表格的自定义 RESIZE_POLICY
        tableView.setColumnResizePolicy(param -> {
            double totalWidth = param.getTable().getWidth();
            for (int i = 0; i < columns.length; i++) {
                columns[i].setPrefWidth(totalWidth * columnPercentages[i]);
            }
            return true; // 表示完成调整
        });

        VBox root = new VBox(tableView);
        Scene scene = new Scene(root, 600, 400);

        primaryStage.setScene(scene);
        primaryStage.setTitle("TableView Resize Policy Example");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
