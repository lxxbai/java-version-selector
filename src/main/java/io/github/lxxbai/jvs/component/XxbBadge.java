package io.github.lxxbai.jvs.component;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * @author lxxbai
 * @since 2024/12/21
 */
public class XxbBadge extends HBox {

    private static final String DEFAULT_STYLE_CLASS = "xxb-badge";

    private StackPane badgePane;

    public XxbBadge() {
        this(null, null, 0, 0);
    }

    public XxbBadge(Node control, Node badge, Integer translateX, Integer translateY) {
        initialize();
        setControl(control);
        setBadge(badge);
        this.setAlignment(Pos.CENTER_LEFT);
        badge.setTranslateX(translateX);
        badge.setTranslateY(translateY);
        badge.setScaleX(0.8);
        badge.setScaleY(0.8);
    }

    private void setBadge(Node badge) {
        if (badge != null) {
            this.badgePane.getChildren().clear();
            this.badgePane.getChildren().add(badge);
        }
    }

    public void setControl(Node control) {
        if (control != null) {
            this.badgePane = new StackPane();
            badgePane.getStyleClass().add("xxb-badge-info");
            this.getChildren().add(control);
            this.getChildren().add(badgePane);
        }
    }

    private void initialize() {
        this.getStyleClass().add(DEFAULT_STYLE_CLASS);
    }
}
