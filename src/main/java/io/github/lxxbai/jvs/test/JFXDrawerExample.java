package io.github.lxxbai.jvs.test;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class JFXDrawerExample extends Application {
    private JFXDrawer drawer;

    private void createDrawerContent() {
        VBox drawerContent = new VBox();
        drawerContent.getChildren().add(new Label("Item 1"));
        drawerContent.getChildren().add(new Label("Item 2"));
        drawerContent.getChildren().add(new Label("Item 3"));
        drawer = new JFXDrawer();
        drawer.setSidePane(drawerContent);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        createDrawerContent();

        JFXHamburger hamburger = new JFXHamburger();
        root.setTop(hamburger);

        HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
        transition.setRate(-1);

        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
            transition.setRate(transition.getRate() * -1);
            transition.play();
            if (drawer.isClosed() || drawer.isClosing()) {
                drawer.open();
            } else {
                drawer.close();
            }
        });

        root.setCenter(new Label("Main Content"));

        drawer.setDefaultDrawerSize(200);
        drawer.setOverLayVisible(false);
        root.setLeft(drawer);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}