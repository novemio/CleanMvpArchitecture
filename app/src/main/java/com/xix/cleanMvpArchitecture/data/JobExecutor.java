package com.xix.cleanMvpArchitecture.data;

import android.support.annotation.NonNull;
import com.xix.cleanMvpArchitecture.domain.ThreadExecutor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by xix on 4/8/17.
 */

@Singleton
public class JobExecutor implements ThreadExecutor {

    private static final int POOL_SIZE = 3;
    private static final int MAX_POOL_SIZE = 5;
    private static final long KEEP_ALIVE_TIME = 10;
    private final ThreadPoolExecutor mThreadPoolExecutor;

    @Inject public JobExecutor() {
        this.mThreadPoolExecutor =
            new ThreadPoolExecutor(POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue<>(),
                new JobThreadFactory());
    }

    @Override public void execute(@NonNull Runnable command) {
        this.mThreadPoolExecutor.execute(command);
    }

    private static class JobThreadFactory implements ThreadFactory {
        private int counter = 0;

        @Override public Thread newThread(@NonNull Runnable runnable) {
            return new Thread(runnable, "android_" + counter++);
        }
    }
}
