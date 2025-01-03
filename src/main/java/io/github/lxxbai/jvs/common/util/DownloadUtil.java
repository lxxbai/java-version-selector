package io.github.lxxbai.jvs.common.util;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import io.github.lxxbai.jvs.component.DownloadProgressBar;

/**
 * @author lxxbai
 */
public class DownloadUtil {


    private static final Cache<String, DownloadProgressBar> LRU_CACHE = CacheUtil.newLRUCache(100);

    public static void put(String version, DownloadProgressBar downloadProgressBar) {
        LRU_CACHE.put(version, downloadProgressBar);
    }

    public static DownloadProgressBar get(String version) {
        return LRU_CACHE.get(version);
    }

    public static void remove(String version) {
        LRU_CACHE.remove(version);
    }
}
