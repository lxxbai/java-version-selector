
package io.github.lxxbai.jvs.component;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.stage.Popup;
import javafx.stage.Window;

/**
 * @author lxxbai
 */
public class XxbPopup extends Popup {

    public XxbPopup() {
        this(null);
    }

    public XxbPopup(Region content) {
        if (content != null) {
            this.getContent().add(content);
        }
        this.setAutoFix(true);
        this.setAutoHide(true);
        this.setHideOnEscape(true);
        this.setConsumeAutoHidingEvents(false);
    }

    /**
     * show the popup according to the specified position with a certain offset
     */
    public void showTopRight(Node node, double offsetX, double offsetY) {
        if (!isShowing()) {
            if (node.getScene() == null || node.getScene().getWindow() == null) {
                throw new IllegalStateException("Can not show popup. The node must be attached to a scene/window.");
            }
            Window parent = node.getScene().getWindow();
            final Point2D origin = node.localToScene(0, 0);
            double width = ((Region) node).getWidth();
            double height = ((Region) node).getHeight();
            final double anchorX = parent.getX() + origin.getX() + node.getScene().getX() + width + offsetX;
            final double anchorY = parent.getY() + origin.getY() + height - offsetY;
            this.show(parent, anchorX, anchorY);
        }
    }
}
