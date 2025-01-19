package io.github.lxxbai.jvs.component;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

/**
 * @author lxxbai
 * @since 2024/12/21
 */
public class XxbBadge extends HBox {

    private static final String DEFAULT_STYLE_CLASS = "xxb-badge";

    private final Node control;

    private final Node badge;

    public XxbBadge(Node control, Node badge) {
        this.control = control;
        this.badge = badge;
        this.control.getStyleClass().add("xxb-badge-control");
        this.badge.getStyleClass().add("xxb-badge-info");
        getChildren().addAll(control, badge);
        this.setSpacing(3);
        this.setAlignment(Pos.CENTER_LEFT);
    }

    private void initialize() {
        this.getStyleClass().add(DEFAULT_STYLE_CLASS);
    }
}
