package io.github.lxxbai.jvs.test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.scene.control.Separator;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Popup;


public class PopupExample extends Application {
    private Popup popup;

    private void createPopup() {
        popup = new Popup();
        VBox popupContent = new VBox();
        popupContent.setSpacing(10);
        popupContent.setPadding(new Insets(10));
        popupContent.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 1px;");

        Label label = new Label("This is a Popup");
        popupContent.getChildren().add(label);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> popup.hide());
        popupContent.getChildren().add(closeButton);

        popup.getContent().add(popupContent);
    }

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        Button showPopupButton = new Button("Show Popup");
        showPopupButton.setOnMouseClicked(event -> {
            createPopup();
            popup.show(showPopupButton, event.getScreenX()+100, event.getScreenY()-100);
        });
        root.getChildren().add(showPopupButton);

        Scene scene = new Scene(root, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}