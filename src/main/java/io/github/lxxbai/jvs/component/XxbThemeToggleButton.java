package io.github.lxxbai.jvs.component;

import com.jfoenix.svg.SVGGlyph;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * @author lxxbai
 */
public class XxbThemeToggleButton extends ToggleButton {

    private final StackPane stackPane;

    private static final String SVG_CONTENT = "M438.6 105.4c12.5 12.5 12.5 32.8 0 45.3l-256 256c-12.5 12.5-32.8 12.5-45.3 0l-128-128c-12.5-12.5-12.5-32.8 0-45.3s32.8-12.5 45.3 0L160 338.7 393.4 105.4c12.5-12.5 32.8-12.5 45.3 0z";

    public XxbThemeToggleButton() {
        super();
        this.getStyleClass().add("theme-toggle-button");
        this.setPrefSize(100, 35);
        this.stackPane = new StackPane();
        stackPane.setPrefHeight(23);
        stackPane.setPrefWidth(23);
        setGraphic(stackPane);
        setGraphicTextGap(10);
        stackPane.setStyle("-fx-background-radius: 100;-fx-background-color: green;");
        this.setOnAction(event -> super.setSelected(true));
        super.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (this.isSelected()) {
                setSelected();
            } else {
                clearSelected();
            }
        });
    }

    public void setSelected() {
        SVGGlyph svgGlyph = new SVGGlyph(SVG_CONTENT);
        double prefHeight = stackPane.getPrefHeight();
        svgGlyph.setSize(prefHeight - 2);
        svgGlyph.setFill(Color.WHITE);
        stackPane.getChildren().clear();
        stackPane.getChildren().add(svgGlyph);
    }

    public void clearSelected() {
        stackPane.getChildren().clear();
    }
}
