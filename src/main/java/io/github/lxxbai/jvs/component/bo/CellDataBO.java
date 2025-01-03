package io.github.lxxbai.jvs.component.bo;


import lombok.Data;

/**
 * @author lxxbai
 */
@Data
public class CellDataBO<T> {

    private T data;

    private Integer index;
}
