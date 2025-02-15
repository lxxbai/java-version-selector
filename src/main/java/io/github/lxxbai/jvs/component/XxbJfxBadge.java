package io.github.lxxbai.jvs.component;

import cn.hutool.core.util.StrUtil;
import javafx.animation.FadeTransition;
import javafx.beans.DefaultProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;

/**
 * @author lxxbai
 * @since 2024/12/21
 */
@DefaultProperty("control")
public class XxbJfxBadge extends StackPane {

    private final SimpleObjectProperty<Object> text;
    private Group badge;
    @Getter
    protected Node control;
    @Setter
    private boolean enabled;
    private static final String DEFAULT_STYLE_CLASS = "jfx-badge";
    private final double x;
    private final double y;

    public XxbJfxBadge() {
        this(null, 10D, 10D);
    }

    public XxbJfxBadge(Node control, Double x, Double y) {
        this.enabled = true;
        this.x = x;
        this.y = y;
        this.text = new SimpleObjectProperty<>();
        this.initialize();
        this.setControl(control);
    }

    public void setControl(Node control) {
        if (control != null) {
            this.control = control;
            this.badge = new Group();
            this.getChildren().add(control);
            this.getChildren().add(this.badge);
            if (control instanceof Region) {
                ((Region) control).widthProperty().addListener((o, oldVal, newVal) -> this.refreshBadge());
                ((Region) control).heightProperty().addListener((o, oldVal, newVal) -> this.refreshBadge());
            }
            this.text.addListener((o, oldVal, newVal) -> this.refreshBadge());
        }

    }

    public void refreshBadge() {
        this.badge.getChildren().clear();
        if (StrUtil.isBlank(getText())) {
            return;
        }
        if (this.enabled) {
            Label labelControl = new Label(String.valueOf(this.text.getValue()));
            StackPane badgePane = new StackPane();
            badgePane.getStyleClass().add("badge-pane");
            badgePane.getChildren().add(labelControl);
            this.badge.getChildren().add(badgePane);
            badge.setTranslateX(x);
            badge.setTranslateY(y);
            FadeTransition ft = new FadeTransition(Duration.millis(666.0), this.badge);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.setCycleCount(1);
            ft.setAutoReverse(true);
            ft.play();
        }
    }


    public final String getText() {
        return StrUtil.toStringOrNull(this.text.get());
    }

    public final void setText(Object value) {
        this.text.set(value);
    }

    public final ObjectProperty<Object> textProperty() {
        return this.text;
    }

    private void initialize() {
        this.getStyleClass().add(DEFAULT_STYLE_CLASS);
    }
}
