package io.github.lxxbai.jvs.component.builder;

import io.github.lxxbai.jvs.component.XxbBadge;
import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * @author lxxbai
 */
public final class XxbBadgeBuilder {

    private Node control;

    private Node badge;

    private Integer translateX;

    private Integer translateY;

    public XxbBadgeBuilder() {
        translateX = 3;
        translateY = -3;
    }

    public static XxbBadgeBuilder builder() {
        return new XxbBadgeBuilder();
    }

    public XxbBadgeBuilder control(Node control) {
        this.control = control;
        return this;
    }

    public XxbBadgeBuilder badge(Node badge) {
        this.badge = badge;
        return this;
    }

    public XxbBadgeBuilder badge(String badge) {
        this.badge = new Label(badge);
        return this;
    }

    public XxbBadgeBuilder translate(Integer translateX, Integer translateY) {
        this.translateX = translateX;
        this.translateY = translateY;
        return this;
    }

    public XxbBadge build() {
        return new XxbBadge(control, badge, translateX, translateY);
    }
}
