package com.xix.cleanMvpArchitecture.presentation;

import com.xix.cleanMvpArchitecture.domain.PostExecutionThread;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by xix on 4/8/17.
 */

@Singleton
public class UIThread implements PostExecutionThread {

    @Inject UIThread() {
    }

    @Override public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
