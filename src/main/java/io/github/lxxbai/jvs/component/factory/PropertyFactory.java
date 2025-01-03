package io.github.lxxbai.jvs.component.factory;

import cn.hutool.core.util.StrUtil;
import javafx.beans.property.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author lxxbai
 */
@SuppressWarnings("unchecked")
public class PropertyFactory {

    private static final Map<Class<?>, PropertyCreator<?>> PROPERTY_CREATORS = new HashMap<>();

    static {
        PROPERTY_CREATORS.put(String.class, (value) -> (Property) new SimpleStringProperty(StrUtil.toStringOrNull(value)));
        PROPERTY_CREATORS.put(Integer.class, (value) -> (Property) (Objects.isNull(value) ? new SimpleIntegerProperty() :
                new SimpleIntegerProperty((Integer) value)));
        PROPERTY_CREATORS.put(Double.class, (value) -> (Property) (Objects.isNull(value) ? new SimpleDoubleProperty() :
                new SimpleDoubleProperty((Double) value)));
        PROPERTY_CREATORS.put(Long.class, (value) -> (Property) (Objects.isNull(value) ? new SimpleLongProperty() : new SimpleLongProperty((Long) value)));
        PROPERTY_CREATORS.put(Float.class, (value) -> (Property) (Objects.isNull(value) ? new SimpleFloatProperty() : new SimpleFloatProperty((Float) value)));
        PROPERTY_CREATORS.put(Boolean.class, (value) -> (Property) (Objects.isNull(value) ? new SimpleBooleanProperty() : new SimpleBooleanProperty((Boolean) value)));
    }

    /**
     * 创建属性
     *
     * @param fieldType 字段类型
     * @param value     字段值
     * @return 属性
     */
    public static <T> Property<T> buildProperty(Class<T> fieldType, T value) {
        if (fieldType == null) {
            throw new IllegalArgumentException("fieldType cannot be null");
        }
        PropertyCreator<T> creator = (PropertyCreator<T>) PROPERTY_CREATORS.get(fieldType);
        if (creator != null) {
            return creator.create(value);
        } else {
            return Objects.isNull(value) ? new SimpleObjectProperty<>() : new SimpleObjectProperty<>(value);
        }
    }

    @FunctionalInterface
    interface PropertyCreator<T> {
        Property<T> create(T value);
    }
}
