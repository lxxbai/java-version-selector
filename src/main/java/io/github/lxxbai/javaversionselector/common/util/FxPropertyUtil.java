package io.github.lxxbai.javaversionselector.common.util;

import cn.hutool.core.util.StrUtil;
import javafx.beans.property.*;

import java.util.Objects;

public class FxPropertyUtil {


    public static <T> Property<T> getProperty(Class<T> fieldType, T value) {
        if (String.class == fieldType) {
            return (Property<T>) new SimpleStringProperty(StrUtil.toStringOrNull(value));
        }
        if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
            return (Property<T>) (Objects.isNull(value) ? new SimpleIntegerProperty() :
                                new SimpleIntegerProperty((Integer) value));
        }
        if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
            return (Property<T>) (Objects.isNull(value) ? new SimpleDoubleProperty() :
                                new SimpleDoubleProperty((Double) value));
        }
        if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
            return (Property<T>) (Objects.isNull(value) ? new SimpleLongProperty() :
                                new SimpleLongProperty((Long) value));
        }
        if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
            return (Property<T>) (Objects.isNull(value) ? new SimpleFloatProperty() :
                                new SimpleFloatProperty((Float) value));
        }
        if ((Boolean.TYPE == fieldType) || (Boolean.class == fieldType)) {
            return (Property<T>) (Objects.isNull(value) ? new SimpleBooleanProperty() :
                                new SimpleBooleanProperty((Boolean) value));
        }
        return Objects.isNull(value) ? new SimpleObjectProperty<>() : new SimpleObjectProperty<>(value);
    }
}
