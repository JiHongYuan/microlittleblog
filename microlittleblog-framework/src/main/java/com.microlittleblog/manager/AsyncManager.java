package com.microlittleblog.manager;

import com.microlittleblog.common.utils.Threads;
import com.microlittleblog.common.utils.spring.SpringUtils;

import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 异步任务管理器
 *
 * @author liuhulu
 */
public class AsyncManager {
    /**
     * 操作延迟10毫秒
     */
    private final int OPERATE_DELAY_TIME = 10;

    /**
     * 异步操作任务调度线程池
     */
    private ScheduledExecutorService executor = SpringUtils.getBean("scheduledExecutorService");

    private AsyncManager() {
    }

    public static AsyncManager getInstance() {
        return AsyncManagerInstance.asyncManager;
    }

    /**
     * 执行任务
     *
     * @param runnable 任务
     */
    public void execute(Runnable runnable) {
        executor.schedule(runnable, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
    }

    /**
     * 停止任务线程池
     */
    public void shutdown() {
        Threads.shutdownAndAwaitTermination(executor);
    }


    private static class AsyncManagerInstance {
        private static AsyncManager asyncManager = new AsyncManager();
    }

}
