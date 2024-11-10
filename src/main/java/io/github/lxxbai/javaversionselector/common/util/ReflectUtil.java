package io.github.lxxbai.javaversionselector.common.util;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.util.ReflectionUtils;

import java.beans.Introspector;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author shupeng_liao
 * @date 2020/12/22 15:31
 **/
@Slf4j
public class ReflectUtil {

    private static final Pattern GET_PATTERN = Pattern.compile("^get[A-Z].*");
    private static final Pattern IS_PATTERN = Pattern.compile("^is[A-Z].*");

    private static final Map<Class<?>, Field[]> declaredFieldsCache = new ConcurrentReferenceHashMap(256);
    private static final Field[] EMPTY_FIELD_ARRAY = new Field[0];

    /**
     * 对象赋值
     *
     * @param fieldName 字段名称
     * @param value     字段值
     * @param target    目标
     */
    public static void setValue(String fieldName, Object value, Object target) {
        Field field = ReflectionUtils.findField(target.getClass(), fieldName);
        if (Objects.isNull(field)) {
            log.error("类：{},字段：{}不存在！", target.getClass(), fieldName);
            return;
        }
        Object typeValue = getTypeValue(field, value);
        if (Objects.nonNull(field)) {
            field.setAccessible(true);
            ReflectionUtils.setField(field, target, typeValue);
        }
    }


    /**
     * 对象赋值
     *
     * @param function 方法
     * @param value    字段值
     * @param target   目标
     */
    public static void setValue(SFunction function, Object target, Object value) {
        String fieldName = fnToFieldName(function);
        Field field = ReflectionUtils.findField(target.getClass(), fieldName);
        if (Objects.nonNull(field)) {
            field.setAccessible(true);
            ReflectionUtils.setField(field, target, value);
        }
    }

    /**
     * 获取getter方法的变量名称
     *
     * @param fn fn
     * @return 变量名称
     */
    public static <T> String fnToFieldName(SFunction<T, ?> fn) {
        try {
            Method method = fn.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(Boolean.TRUE);
            SerializedLambda serializedLambda = (SerializedLambda) method.invoke(fn);
            String getter = serializedLambda.getImplMethodName();
            if (GET_PATTERN.matcher(getter).matches()) {
                getter = getter.substring(3);
            } else if (IS_PATTERN.matcher(getter).matches()) {
                getter = getter.substring(2);
            }
            return Introspector.decapitalize(getter);
        } catch (ReflectiveOperationException var4) {
            throw new RuntimeException("反射异常");
        }
    }

    /**
     * 对象赋值
     *
     * @param field  字段
     * @param value  字段值
     * @param target 目标
     */
    public static void setValue(Field field, Object target, Object value) {
        Object realValue = getTypeValue(field, value);
        ReflectionUtils.setField(field, target, realValue);
    }


    /**
     * 获取真实值类型
     *
     * @param field 字段
     * @param value 值
     * @return 值
     */
    private static Object getTypeValue(Field field, Object value) {
        if (Objects.isNull(value)) {
            return null;
        }
        String strValue = String.valueOf(value);
        Class fieldType = field.getType();
        if (String.class == fieldType) {
            return strValue;
        } else if (BigDecimal.class == fieldType) {
            return new BigDecimal(strValue);
        } else if (BigInteger.class == fieldType) {
            return new BigInteger(strValue);
        } else if (Date.class == fieldType) {
            return null == strValue ? null : DateUtil.parse(strValue, "yyyy-MM-dd");
        } else if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
            return Integer.valueOf(strValue);
        } else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
            return Long.valueOf(strValue);
        } else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
            return Float.valueOf(strValue);
        } else if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {
            return Short.valueOf(strValue);
        } else if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
            return Double.valueOf(strValue);
        } else {
            return null;
        }
    }


    /**
     * 取值
     *
     * @param fieldName 字段名称
     * @param target    target
     * @return 字段值
     */
    public static Object getValue(String fieldName, Object target) {
        try {
            Field field = ReflectionUtils.findField(target.getClass(), fieldName);
            if (Objects.isNull(field)) {
                return null;
            }
            field.setAccessible(true);
            return field.get(target);
        } catch (Exception e) {
            log.error("反射获取值失败:", e);
        }
        return null;
    }

    public static Object getValue(Field field, Object value) {
        try {
            return field.get(value);
        } catch (Exception e) {
            log.error("反射获取值失败:", e);
        }
        return null;
    }

    public static Field[] getDeclaredFields(Class<?> clazz) {
        Assert.notNull(clazz, "Class must not be null");
        Field[] result = declaredFieldsCache.get(clazz);
        if (result == null) {
            try {
                result = clazz.getDeclaredFields();
                declaredFieldsCache.put(clazz, result.length == 0 ? EMPTY_FIELD_ARRAY : result);
            } catch (Throwable var3) {
                throw new IllegalStateException("Failed to introspect Class [" + clazz.getName() + "] from ClassLoader [" + clazz.getClassLoader() + "]", var3);
            }
        }
        return result;
    }
}
