package io.github.lxxbai.javaversionselector.common.util;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import io.github.lxxbai.javaversionselector.component.DownloadProgress;

/**
 * @author lxxbai
 */
public class DownloadUtil {


    private static final Cache<String, DownloadProgress> LRU_CACHE = CacheUtil.newLRUCache(100);

    public static void put(String version, DownloadProgress downloadProgress) {
        LRU_CACHE.put(version, downloadProgress);
    }

    public static DownloadProgress get(String version) {
        return LRU_CACHE.get(version);
    }

    public static void remove(String version) {
        LRU_CACHE.remove(version);
    }
}
