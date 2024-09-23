package io.github.lxxbai.javaversionselector.datasource.repository;


import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * 内存数据仓库
 *
 * @author lxxbai
 */
public abstract class InMemoryRepository<T> {

    /**
     * 数据集合
     */
    private final List<T> dataList;

    public InMemoryRepository() {
        this.dataList = init();
    }

    /**
     * 初始化数据
     *
     * @return 数据
     */
    abstract List<T> init();


    /**
     * 查询所有数据
     *
     * @return 查询所有数据
     */
    public List<T> findAll() {
        return Collections.unmodifiableList(dataList);
    }

    /**
     * 查询第一个数据
     *
     * @param function 方法
     * @param value    值
     * @return 第一个数据
     */
    public T findFirst(Function<T, ?> function, Object value) {
        return dataList.stream().filter(t -> Objects.equals(function.apply(t), value)).findFirst().orElse(null);
    }

    /**
     * 新增数据
     */
    public void add(T t) {
        dataList.add(t);
    }

    /**
     * 删除数据
     */
    public void delete(Function<T, ?> function, Object value) {
        dataList.removeIf(t -> Objects.equals(function.apply(t), value));
    }
}
