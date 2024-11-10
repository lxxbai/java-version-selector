package io.github.lxxbai.javaversionselector.common.util;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;

/**
 * @author lxxbai
 */
public class LocalCacheUtil {


    private static final Cache<String, Object> LRU_CACHE = CacheUtil.newLRUCache(1000);

    public static void put(String key, Object value) {
        LRU_CACHE.put(key, value);
    }

    public static <T> T get(String key) {
        return (T) LRU_CACHE.get(key);
    }

    public static void remove(String key) {
        LRU_CACHE.remove(key);
    }


    public static <T> T getDownloadCache(String key) {
        return (T) LRU_CACHE.get("DOWNLOAD:" + key);
    }

    public static <T> T getInstallingCache(String key) {
        return (T) LRU_CACHE.get("INSTALLING:" + key);
    }


    public static void putDownloadCache(String key, Object value) {
        LRU_CACHE.put("DOWNLOAD:" + key, value);
    }

    public static void putInstallingCache(String key, Object value) {
        LRU_CACHE.put("INSTALLING:" + key, value);
    }
}
