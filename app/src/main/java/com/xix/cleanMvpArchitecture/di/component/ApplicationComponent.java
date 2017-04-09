package com.xix.cleanMvpArchitecture.di.component;

import android.app.Application;
import android.content.Context;
import com.xix.cleanMvpArchitecture.ProjectApplication;
import com.xix.cleanMvpArchitecture.di.ApplicationContext;
import com.xix.cleanMvpArchitecture.di.module.ApplicationModule;
import com.xix.cleanMvpArchitecture.domain.DataManager;
import dagger.Component;
import javax.inject.Singleton;

/**
 * Created by xix on 3/12/17.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(ProjectApplication projectApplication);

    @ApplicationContext Context getContext();


    Application getApplication();

    DataManager getDataManager();
}