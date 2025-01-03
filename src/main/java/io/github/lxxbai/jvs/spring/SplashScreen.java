package io.github.lxxbai.jvs.spring;

import com.jfoenix.svg.SVGGlyph;
import io.github.lxxbai.jvs.common.util.SVGGlyphUtil;
import javafx.scene.Parent;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * A default standard splash pane implementation Subclass it and override it's
 * methods to customize with your own behavior. Be aware that you can not use
 * Spring features here yet.
 *
 * @author Felix Roske
 * @author Andreas Jay
 */
public class SplashScreen {

    private static final String DEFAULT_IMAGE = "svg/java-solid.svg";

    /**
     * Override this to create your own splash pane parent node.
     *
     * @return A standard image
     */
    public Parent getParent() {
        SVGGlyph svgGlyph = SVGGlyphUtil.loadGlyph(DEFAULT_IMAGE);
        svgGlyph.setFill(Color.PINK);
        svgGlyph.setSize(500, 350);
        final ProgressBar splashProgressBar = new ProgressBar();
        splashProgressBar.setPrefWidth(500);
        final VBox vbox = new VBox();
        vbox.getChildren().addAll(svgGlyph, splashProgressBar);
        return vbox;
    }

    /**
     * Customize if the splash screen should be visible at all.
     *
     * @return true by default
     */
    public boolean visible() {
        return true;
    }
}