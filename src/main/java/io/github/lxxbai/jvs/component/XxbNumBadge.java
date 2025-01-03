package io.github.lxxbai.jvs.component;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;

import java.util.Objects;

/**
 * @author lxxbai
 * @since 2024/12/21
 */
public class XxbNumBadge extends XxbBadge {

    private final SimpleIntegerProperty num;

    public XxbNumBadge(Node control, Double x, Double y) {
        super(control, x, y);
        this.num = new SimpleIntegerProperty();
        num.addListener((o, oldVal, newVal) -> {
            if (Objects.isNull(newVal)||num.get()==0){
                this.setText("");
            }else {
                this.setText(String.valueOf(num.get()));
            }
        });
    }


    public SimpleIntegerProperty numProperty() {
        return num;
    }
}
