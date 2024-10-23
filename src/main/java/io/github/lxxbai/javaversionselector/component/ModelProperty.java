package io.github.lxxbai.javaversionselector.component;

import cn.hutool.core.lang.func.Func1;
import cn.hutool.core.lang.func.LambdaUtil;
import cn.hutool.core.util.ReflectUtil;
import io.github.lxxbai.javaversionselector.common.factory.PropertyFactory;
import io.github.lxxbai.javaversionselector.model.DownloadConfig;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author lxxbai
 */
@SuppressWarnings("unchecked")
public class ModelProperty<T> {


    /**
     * model
     */
    private final ObjectProperty<T> model;

    /*
     * model的class
     */
    private Class<T> clazz;

    /**
     * 属性缓存
     */
    private final Map<Field, Property> propertyMap = new HashMap<>();

    public ModelProperty() {
        this.model = new SimpleObjectProperty<>();
        addListener();
    }

    public ModelProperty(T value) {
        this.model = new SimpleObjectProperty<>(value);
        addListener();
    }


    /**
     * 添加model监听器
     */
    private void addListener() {
        this.model.addListener((observable, oldValue, newValue) ->
                propertyMap.forEach((field, property) -> {
                    Object value = ReflectUtil.getFieldValue(newValue, field);
                    property.setValue(value);
                }));
    }


    /*
     * 设置model
     */
    public void setModel(T value) {
        if (Objects.isNull(value)) {
            return;
        }
        this.model.setValue(value);
    }


    /**
     * 获取model
     *
     * @return model
     */
    public T getModel() {
        if (propertyMap.isEmpty()) {
            return model.get();
        }
        T result = Objects.isNull(model.get()) ? ReflectUtil.newInstance(clazz) : model.get();
        //反射设置值，不会触发 属性监听器
        propertyMap.forEach((field, property) ->
                ReflectUtil.setFieldValue(result, field, property.getValue())
        );
        return result;
    }


    /**
     * 构建属性
     *
     * @param function 方法
     * @return 属性
     */
    public <P> Property<P> buildProperty(Func1<T, P> function) {
        // 获取属性类型
        String fieldName = LambdaUtil.getFieldName(function);
        if (Objects.isNull(clazz)) {
            this.clazz = LambdaUtil.getRealClass(function);
        }
        //获取字段
        Field field = ReflectUtil.getField(clazz, fieldName);
        // 获取属性类型
        Class<P> type = (Class<P>) field.getType();
        //创建属性
        Property<P> property = PropertyFactory.buildProperty(type, null);
        propertyMap.put(field, property);
        return property;
    }

    public static void main(String[] args) {
        ModelProperty<DownloadConfig> modelProperty = new ModelProperty<>();
        Property<String> stringProperty = modelProperty.buildProperty(DownloadConfig::getDownloadPath);
        Property<String> downloadPathField = new SimpleObjectProperty<>();
        downloadPathField.bindBidirectional(stringProperty);
        downloadPathField.setValue("123");
        System.out.println(modelProperty.getModel());
    }
}
