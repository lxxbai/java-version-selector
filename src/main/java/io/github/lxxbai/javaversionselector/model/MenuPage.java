
package io.github.lxxbai.javaversionselector.model;

import javafx.scene.Node;
import lombok.Data;

@Data
public class MenuPage {
    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 内容
     */
    private Node content;


    public MenuPage(String menuName, Node content) {
        this.menuName = menuName;
        this.content = content;
    }
}
