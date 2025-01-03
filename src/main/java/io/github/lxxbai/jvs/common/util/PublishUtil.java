package io.github.lxxbai.jvs.common.util;

import cn.hutool.extra.spring.SpringUtil;

/**
 * @author lxxbai
 */
public class PublishUtil {


    /**
     * 发布事件
     *
     * @param object object
     */
    public static void publishEvent(Object object) {
        SpringUtil.getApplicationContext().publishEvent(object);
    }
}
