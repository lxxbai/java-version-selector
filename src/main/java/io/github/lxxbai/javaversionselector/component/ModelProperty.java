package io.github.lxxbai.javaversionselector.component;

import cn.hutool.core.lang.func.Func1;
import cn.hutool.core.lang.func.LambdaUtil;
import cn.hutool.core.util.ReflectUtil;
import io.github.lxxbai.javaversionselector.common.util.FxPropertyUtil;
import javafx.beans.property.Property;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ModelProperty<T> {

    private T model;

    private Class<T> clazz;

    private Map<Field, Property<?>> propertyMap = new HashMap<>();

    public ModelProperty(Class<T> clazz) {

    }


    public T getModel() {

        return model;
    }

    public <P> Property<P> buildProperty(Func1<T, ?> function) {
        // 获取属性类型
        String fieldName = LambdaUtil.getFieldName(function);
        //获取字段
        Field field = ReflectUtil.getField(clazz, fieldName);
        //获取属性值
        Object fieldValue = ReflectUtil.getFieldValue(model, field);
        // 获取属性类型
        Class<?> type = field.getType();
        //创建属性
        Property property = FxPropertyUtil.getProperty(type, fieldValue);
        propertyMap.put(field, property);
        return property;
    }
}
