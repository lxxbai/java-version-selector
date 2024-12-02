
package io.github.lxxbai.javaversionselector.model;

import javafx.scene.Node;
import lombok.Data;

/**
 * @author lxxbai
 */
@Data
public class MenuPage {
    /**
     * 菜单名称
     */
    private String menuName;


    /**
     * 菜单图片路径
     */
    private String picPath;

    /**
     * 描述
     */
    private String desc;

    /**
     * 内容
     */
    private Node content;


    public MenuPage(String menuName, String picPath, Node content) {
        this.menuName = menuName;
        this.picPath = picPath;
        this.content = content;
    }
}
