package io.github.lxxbai.javaversionselector.common.util;

import cn.hutool.core.util.StrUtil;

/**
 * @author lxxbai
 */
public class StringUtil {


    /**
     * 判断字符串是否为空或与指定字符串相等（不区分大小写）.
     * <p>
     * 此方法主要用于简化字符串的空检查或不区分大小写的比较操作. 它提供了一种更简洁的方式来实现这一需求,
     * 避免了在代码中多次调用字符串方法和比较操作.
     *
     * @param sourcesStr 待检查的字符串.
     * @param compareStr 待比较的字符串.
     * @return 如果 sourcesStr 为空或与 compareStr 相等（不区分大小写），则返回 true；否则返回 false.
     */
    public static boolean isBlankOrEqual(String sourcesStr, String compareStr) {
        return StrUtil.isBlank(sourcesStr) || StrUtil.equalsIgnoreCase(sourcesStr, compareStr);
    }

}
