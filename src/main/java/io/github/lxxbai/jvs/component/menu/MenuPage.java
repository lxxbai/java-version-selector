
package io.github.lxxbai.jvs.component.menu;

import javafx.scene.Node;
import lombok.Data;

/**
 * @author lxxbai
 */
@Data
public class MenuPage {

    private MenuItem menuItem;

    /**
     * 内容
     */
    private Node content;

    private int order;


    public MenuPage(MenuItem menuItem, Node content, int order) {
        this.menuItem = menuItem;
        this.content = content;
        this.order = order;
    }
}
