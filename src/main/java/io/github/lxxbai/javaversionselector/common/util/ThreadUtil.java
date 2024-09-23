package io.github.lxxbai.javaversionselector.common.util;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ThreadUtil {

    private static final Executor THREAD_POOL = Executors.newFixedThreadPool(5);

    public static void execute(Runnable runnable) {
        THREAD_POOL.execute(runnable);
    }
}
