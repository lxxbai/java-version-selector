package io.github.lxxbai.jvs.manager;

import io.github.lxxbai.jvs.component.DownloadTask;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * 下载任务管理器
 *
 * @author lxxbai
 */
@Component
public class DownloadTaskManager {

    private final Map<String, DownloadTask> tasks = new ConcurrentHashMap<>();

    /**
     * 添加下载任务
     *
     * @param downloadTask 下载任务
     */
    public DownloadTask addTask(DownloadTask downloadTask) {
        return tasks.putIfAbsent(downloadTask.getKey(), downloadTask);
    }

    public DownloadTask getOrCreateTask(String key, Supplier<DownloadTask> taskSupplier) {
        return tasks.computeIfAbsent(key, x -> taskSupplier.get());
    }

    /**
     * 获取下载任务
     *
     * @param key key
     */
    public DownloadTask getTask(String key) {
        return tasks.get(key);
    }

    public void removeTask(String key) {
        tasks.remove(key);
    }
}
