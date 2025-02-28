package io.github.lxxbai.jvs.component.bo;

import lombok.Getter;
import lombok.Setter;

import java.util.function.Consumer;

/**
 * @author liaosp
 */
@Getter
public class ListViewItem<T> {

    private final T item;

    @Setter
    private Consumer<T> onclickConsumer;

    private final Integer order;

    public ListViewItem(T item, Integer order) {
        this.item = item;
        this.order = order;
    }
}
