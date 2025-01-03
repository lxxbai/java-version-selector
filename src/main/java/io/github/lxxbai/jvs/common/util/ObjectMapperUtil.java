package io.github.lxxbai.jvs.common.util;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author shupeng_liao
 * @date 2022/3/7 11:29
 **/
@Slf4j
public class ObjectMapperUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    private static final TypeReference<HashMap<String, Object>> OBJ_MAP_REF = new TypeReference<HashMap<String, Object>>() {
    };

    private static final TypeReference<HashMap<String, String>> STRING_MAP_REF = new TypeReference<HashMap<String, String>>() {
    };

    private static final TypeReference<List<String>> STR_LIST_REF = new TypeReference<List<String>>() {
    };

    public static final TypeReference<Set<String>> STR_SET_REF = new TypeReference<Set<String>>() {
    };

    static {
        // 如果json中有新增的字段并且是实体类中不存在的，不报错
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        // 如果存在未知属性，则忽略不报错
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 允许key没有双引号
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 允许key有单引号
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        //忽略空值
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //科学计数
        OBJECT_MAPPER.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
    }

    /**
     * 转json
     *
     * @param data 原始数据
     * @return json串
     */
    public static String toJsonString(Object data) {
        try {
            return OBJECT_MAPPER.writeValueAsString(data);
        } catch (Exception e) {
            log.error("转json字符串失败,原因:", e);
        }
        return null;
    }


    /**
     * 数据转换成目标数据
     *
     * @param reqData 请求对象
     * @return 目标数据
     */
    public static <T> T converter(Object reqData, TypeReference<T> typeRef) {
        try {
            //数据转换
            return OBJECT_MAPPER.convertValue(reqData, typeRef);
        } catch (Exception e) {
            log.error("数据转换失败!,异常:", e);
        }
        return null;
    }

    /**
     * 数据转换成目标数据
     *
     * @param reqData 请求对象
     * @return 目标数据
     */
    public static Map<String, Object> objToMap(Object reqData) {
        try {
            //数据转换
            return OBJECT_MAPPER.convertValue(reqData, OBJ_MAP_REF);
        } catch (Exception e) {
            log.error("数据转换失败!,异常:", e);
        }
        return null;
    }

    /**
     * 数据转换成目标数据
     *
     * @param reqData 请求对象
     * @return 目标数据
     */
    public static <T> T toObj(String reqData, Class<T> clazz) {
        try {
            if (StrUtil.isBlank(reqData)) {
                return null;
            }
            //数据转换
            return OBJECT_MAPPER.readValue(reqData, clazz);
        } catch (Exception e) {
            log.error("数据转换失败!,异常:", e);
        }
        return null;
    }

    /**
     * 数据转换成目标数据
     *
     * @param reqData 请求对象
     * @return 目标数据
     */
    public static List<String> toStringList(String reqData) {
        try {
            if (StrUtil.isBlank(reqData)) {
                return List.of();
            }
            //数据转换
            return OBJECT_MAPPER.readValue(reqData, STR_LIST_REF);
        } catch (Exception e) {
            log.error("数据转换失败!,异常:", e);
        }
        return List.of();
    }

    /**
     * 数据转换成目标数据
     *
     * @param reqData 请求对象
     * @return 目标数据
     */
    public static <T> T toObj(String reqData, TypeReference<T> typeRef) {
        try {
            //数据转换
            return OBJECT_MAPPER.readValue(reqData, typeRef);
        } catch (Exception e) {
            log.error("数据转换失败!,异常:", e);
        }
        return null;
    }

    /**
     * 将 json字符串 转换为 Map
     *
     * @param value json字符串
     * @return Map结果集
     */
    public static Map<String, String> toMap(String value) {
        return StrUtil.isNotBlank(value) ? toStringMap(value) : new HashMap<>();
    }

    private static Map<String, String> toStringMap(String value) {
        try {
            return OBJECT_MAPPER.readValue(value, STRING_MAP_REF);
        } catch (Exception e) {
            log.error(String.format("toMap exception\n%s", value), e);
        }
        return Map.of();
    }
}
