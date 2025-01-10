package io.github.lxxbai.jvs.common.util;

import cn.hutool.core.thread.ThreadFactoryBuilder;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolUtil {

    /**
     * 任务线程池
     */
    private static final ThreadPoolExecutor TASK_POOL_EXECUTOR =
            new ThreadPoolExecutor(3, 10, 1,
                    TimeUnit.SECONDS, new ArrayBlockingQueue<>(20),
                    new ThreadFactoryBuilder().setNamePrefix("v-pool-%d").build(),
                    new ThreadPoolExecutor.AbortPolicy());


    /**
     * 执行任务
     *
     * @param command 任务
     */
    public static void execute(Runnable command) {
        TASK_POOL_EXECUTOR.execute(command);
    }

    /**
     * 执行任务
     */
    public static boolean havingRunningTasks() {
        return TASK_POOL_EXECUTOR.getActiveCount() > 0;
    }


    /**
     * 关闭任务
     */
    public static void shutdownAll() {
        if (TASK_POOL_EXECUTOR.getActiveCount() > 0) {
            TASK_POOL_EXECUTOR.shutdown();
        }
    }

    /**
     * 立即关闭任务
     */
    public static void shutdownAllNow() {
        if (TASK_POOL_EXECUTOR.getActiveCount() > 0) {
            TASK_POOL_EXECUTOR.shutdownNow();
        }
    }
}
