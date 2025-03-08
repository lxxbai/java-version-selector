package io.github.lxxbai.jvs.component;

import com.jfoenix.svg.SVGGlyph;
import javafx.beans.DefaultProperty;
import javafx.beans.NamedArg;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author lxxbai
 */
@DefaultProperty("graphic")
public class XxbToggleImage extends StackPane implements Toggle {

    private ObjectProperty<ToggleGroup> toggleGroup;

    private BooleanProperty selected;

    private final ColorAdjust colorAdjust;

    private static final String SVG_CONTENT = "M438.6 105.4c12.5 12.5 12.5 32.8 0 45.3l-256 256c-12.5 12.5-32.8 12.5-45.3 0l-128-128c-12.5-12.5-12.5-32.8 0-45.3s32.8-12.5 45.3 0L160 338.7 393.4 105.4c12.5-12.5 32.8-12.5 45.3 0z";

    public XxbToggleImage(@NamedArg("graphic") Node graphic) {
        this.colorAdjust = new ColorAdjust();
        graphic.setEffect(colorAdjust);
        graphic.setCursor(Cursor.HAND);
        // 创建一个矩形并设置圆角
        Rectangle clip = new Rectangle(graphic.getBoundsInLocal().getWidth(), graphic.getBoundsInLocal().getHeight());
        // 圆角宽度
        clip.setArcWidth(8);
        clip.setArcHeight(8);
        graphic.setClip(clip);
        this.getChildren().add(graphic);
        graphic.setOnMouseClicked(event -> {
            if (!isSelected()) {
                setSelected(true);
            }
        });
        graphic.setOnMouseEntered(event -> {
            if (!isSelected()) {
                colorAdjust.setBrightness(-0.3);
            }
        });
        graphic.setOnMouseExited(event -> {
            if (!isSelected()) {
                colorAdjust.setBrightness(0);
            }
        });
    }

    public void setSelected() {
        ObservableList<Node> children = this.getChildren();
        if (children.size() > 1) {
            children.remove(1, children.size());
        }
        colorAdjust.setBrightness(-0.3);
        StackPane stackPane = new StackPane();
        stackPane.setPrefSize(25, 25);
        stackPane.setMaxSize(25, 25);
        SVGGlyph svgGlyph = new SVGGlyph(SVG_CONTENT);
        svgGlyph.setSize(14, 10);
        svgGlyph.setFill(Color.WHITE);
        svgGlyph.setBackground(new Background(new BackgroundFill(Color.GOLDENROD, null, null)));
        stackPane.getChildren().add(svgGlyph);
        stackPane.setStyle("-fx-background-color: #fff;-fx-background-radius: 50%");
        children.add(stackPane);
    }

    public void clearSelected() {
        ObservableList<Node> children = this.getChildren();
        if (children.size() > 1) {
            children.remove(1, children.size());
        }
        colorAdjust.setBrightness(0);
    }

    @Override
    public final ToggleGroup getToggleGroup() {
        return toggleGroup == null ? null : toggleGroup.get();
    }

    @Override
    public void setToggleGroup(ToggleGroup toggleGroup) {
        toggleGroupProperty().set(toggleGroup);
    }

    @Override
    public ObjectProperty<ToggleGroup> toggleGroupProperty() {
        if (toggleGroup == null) {
            toggleGroup = new ObjectPropertyBase<>() {
                private ToggleGroup old;

                @Override
                protected void invalidated() {
                    if (old != null) {
                        old.getToggles().remove(XxbToggleImage.this);
                    }
                    old = get();
                    if (get() != null && !get().getToggles().contains(XxbToggleImage.this)) {
                        get().getToggles().add(XxbToggleImage.this);
                    }
                }

                @Override
                public Object getBean() {
                    return XxbToggleImage.this;
                }

                @Override
                public String getName() {
                    return "toggleGroup";
                }
            };
        }
        return toggleGroup;
    }

    @Override
    public boolean isSelected() {
        return selected != null && selected.get();
    }

    @Override
    public void setSelected(boolean selected) {
        selectedProperty().set(selected);
    }

    @Override
    public BooleanProperty selectedProperty() {
        if (selected == null) {
            selected = new BooleanPropertyBase() {
                @Override
                protected void invalidated() {
                    if (getToggleGroup() != null) {
                        if (get()) {
                            getToggleGroup().selectToggle(XxbToggleImage.this);
                        } else if (getToggleGroup().getSelectedToggle() == XxbToggleImage.this) {
                            getToggleGroup().selectToggle(XxbToggleImage.this);
                        }
                    }
                    if (isSelected()) {
                        setSelected();
                    } else {
                        clearSelected();
                    }
                }

                @Override
                public Object getBean() {
                    return XxbToggleImage.this;
                }

                @Override
                public String getName() {
                    return "selected";
                }
            };
        }
        return selected;
    }
}
