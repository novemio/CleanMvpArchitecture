package com.xix.cleanMvpArchitecture.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import com.xix.cleanMvpArchitecture.R;
import com.xix.cleanMvpArchitecture.data.DataManagerImpl;
import com.xix.cleanMvpArchitecture.data.JobExecutor;
import com.xix.cleanMvpArchitecture.data.cache.DataCache;
import com.xix.cleanMvpArchitecture.data.cache.DataCacheImpl;
import com.xix.cleanMvpArchitecture.di.ApplicationContext;
import com.xix.cleanMvpArchitecture.di.DatabaseInfo;
import com.xix.cleanMvpArchitecture.domain.DataManager;
import com.xix.cleanMvpArchitecture.domain.PostExecutionThread;
import com.xix.cleanMvpArchitecture.domain.ThreadExecutor;
import com.xix.cleanMvpArchitecture.presentation.UIThread;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by xix on 3/12/17.
 */

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides @ApplicationContext Context provideContext() {
        return mApplication;
    }

    @Provides Application provideApplication() {
        return mApplication;
    }

    @Provides @DatabaseInfo String provideDatabaseName() {
        return "db-Dagger.db";
    }

    @Provides @DatabaseInfo Integer provideDatabaseVersion() {
        return 1;
    }

    @Provides SharedPreferences provideSharedPrefs() {
        return mApplication.getSharedPreferences("app-prefs", Context.MODE_PRIVATE);
    }

    @Provides @Singleton CalligraphyConfig provideCalligraphyDefaultConfig() {
        return new CalligraphyConfig.Builder().setDefaultFontPath("fonts/source-sans-pro/SourceSansPro-Regular.ttf")
            .setFontAttrId(R.attr.fontPath)
            .build();
    }

    @Provides @Singleton DataManager provideDataManager(DataManagerImpl appDataManager) {
        return appDataManager;
    }
    @Provides @Singleton ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides @Singleton PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides @Singleton DataCache provideUserCache(DataCacheImpl userCache) {
        return userCache;
    }

}
