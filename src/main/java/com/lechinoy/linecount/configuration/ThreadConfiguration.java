package com.lechinoy.linecount.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by liqianlong
 * 2019 2019/5/22 23:03
 */
@Configuration
public class ThreadConfiguration {


    @Bean("lineCountThreadPool")
    public ThreadPoolExecutor getCardGameThreadPool() {

        return new ThreadPoolExecutor(10, 100, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000), Executors.defaultThreadFactory(),new ThreadPoolExecutor.CallerRunsPolicy());

    }


}
