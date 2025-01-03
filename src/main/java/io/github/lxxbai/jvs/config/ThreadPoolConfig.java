package io.github.lxxbai.jvs.config;


import cn.hutool.core.thread.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPoolConfig {

    @Bean
    public ThreadPoolExecutor reportThreadPool() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNamePrefix("v-pool-%d").build();
        return new ThreadPoolExecutor(3, 10, 1,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(20),
                threadFactory, new ThreadPoolExecutor.AbortPolicy());
    }
}
