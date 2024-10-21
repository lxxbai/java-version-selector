package io.github.lxxbai.javaversionselector.component;

import cn.hutool.core.lang.func.Func1;
import cn.hutool.core.lang.func.LambdaUtil;
import cn.hutool.core.util.ReflectUtil;
import io.github.lxxbai.javaversionselector.common.util.FxPropertyUtil;
import javafx.beans.property.Property;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ModelProperty<T> {

    private T model;

    private Class<T> clazz;

    private final Map<Field, Property<?>> propertyMap = new HashMap<>();

    public ModelProperty() {
    }

    public ModelProperty(T model) {
        this.model = model;
        this.clazz = (Class<T>) model.getClass();
    }


    public T getModel() {
        if (propertyMap.isEmpty()) {
            return model;
        }
        if (Objects.isNull(model)) {
            model = ReflectUtil.newInstance(clazz);
        }
        propertyMap.forEach((field, property) ->
                ReflectUtil.setFieldValue(model, field, property.getValue()));
        return model;
    }

    public <P> Property<P> buildProperty(Func1<T, P> function) {
        // 获取属性类型
        String fieldName = LambdaUtil.getFieldName(function);
        if (Objects.isNull(clazz)) {
            clazz = LambdaUtil.getRealClass(function);
        }
        //获取字段
        Field field = ReflectUtil.getField(clazz, fieldName);
        //获取属性值
        P fieldValue = Objects.isNull(model) ? null : (P) ReflectUtil.getFieldValue(model, field);
        // 获取属性类型
        Class<P> type = (Class<P>) field.getType();
        //创建属性
        Property<P> property = FxPropertyUtil.getProperty(type, fieldValue);
        propertyMap.put(field, property);
        return property;
    }
}
