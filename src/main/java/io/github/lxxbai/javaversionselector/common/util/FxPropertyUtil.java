package io.github.lxxbai.javaversionselector.common.util;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import io.github.lxxbai.javaversionselector.common.exception.ClientException;
import javafx.beans.property.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class FxPropertyUtil {


    public static Property getProperty(Class fieldType, Object value) {
        if (String.class == fieldType || Date.class == fieldType || BigInteger.class == fieldType) {
            return new SimpleStringProperty(StrUtil.toStringOrNull(value));
        } else if (BigDecimal.class == fieldType || (Double.TYPE == fieldType) || (Double.class == fieldType)) {
            return new SimpleDoubleProperty();
        } else if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
            return new SimpleIntegerProperty();
        } else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
            return new SimpleLongProperty();
        } else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
            return new SimpleFloatProperty();
        } else {
            throw new ClientException("未适配的属性");
        }
    }
}
