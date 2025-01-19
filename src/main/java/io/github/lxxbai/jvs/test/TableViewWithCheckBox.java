package io.github.lxxbai.jvs.test;

import com.jfoenix.controls.JFXCheckBox;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class TableViewWithCheckBox extends Application {

    public static class Person {
        private final StringProperty name;
        private final BooleanProperty selected;

        public Person(String name, boolean selected) {
            this.name = new SimpleStringProperty(name);
            this.selected = new SimpleBooleanProperty(selected);
        }

        public String getName() {
            return name.get();
        }

        public void setName(String name) {
            this.name.set(name);
        }

        public StringProperty nameProperty() {
            return name;
        }

        public boolean isSelected() {
            return selected.get();
        }

        public void setSelected(boolean selected) {
            this.selected.set(selected);
        }

        public BooleanProperty selectedProperty() {
            return selected;
        }
    }

    @Override
    public void start(Stage stage) {
        TableView<Person> tableView = new TableView<>();
        ObservableList<Person> persons = FXCollections.observableArrayList(
                new Person("John", false),
                new Person("Jane", true)
        );

        TableColumn<Person, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        TableColumn<Person, Boolean> checkBoxColumn = new TableColumn<>("Select");
        checkBoxColumn.setEditable(true);
        checkBoxColumn.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
        checkBoxColumn.setCellFactory(CheckBoxTableCell.forTableColumn(param -> persons.get(param).selectedProperty()));

        tableView.getColumns().addAll(nameColumn, checkBoxColumn);
        tableView.setItems(persons);

        StackPane root = new StackPane();
        root.getChildren().add(tableView);

        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}