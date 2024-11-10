package io.github.lxxbai.javaversionselector.model;

import javafx.scene.Node;
import lombok.Data;

@Data
public class ViewResult<C, N extends Node> {

    private C controller;

    private N viewNode;
}
