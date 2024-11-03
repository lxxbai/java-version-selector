package io.github.lxxbai.javaversionselector.component.bo;


import lombok.Data;

/**
 * @author lxxbai
 */
@Data
public class CellDataBO<T> {

    private T data;

    private Integer index;
}
