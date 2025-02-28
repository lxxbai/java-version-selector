package io.github.lxxbai.jvs.spring;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author lxxbai
 */
public class FxmlViewUtil {

    private static final Map<String, List<AbstractFxmlView>> VIEW_MAP = new ConcurrentHashMap<>(16);


    /**
     * 根据分组key获取分组的view
     *
     * @param key 分组key
     * @return view
     */
    public static List<AbstractFxmlView> getGroupView(String key) {
        if (CollUtil.isEmpty(VIEW_MAP)) {
            //初始化
            Map<String, AbstractFxmlView> beans = SpringUtil.getBeansOfType(AbstractFxmlView.class);
            Map<String, List<AbstractFxmlView>> viewMap = beans.values()
                    .stream()
                    .filter(x -> StrUtil.isNotBlank(x.getAnnotation().groupKey()))
                    //分组+排序
                    .sorted(Comparator.comparingInt(x -> x.getAnnotation().order()))
                    .collect(Collectors.groupingBy(x -> x.getAnnotation().groupKey()));
            VIEW_MAP.putAll(viewMap);
        }
        return VIEW_MAP.get(key);
    }
}
