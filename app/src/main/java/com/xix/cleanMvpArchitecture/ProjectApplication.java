package com.xix.cleanMvpArchitecture;

import android.app.Application;
import android.content.Context;
import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;
import com.xix.cleanMvpArchitecture.di.component.ApplicationComponent;
import com.xix.cleanMvpArchitecture.di.component.DaggerApplicationComponent;
import com.xix.cleanMvpArchitecture.di.module.ApplicationModule;
import com.xix.cleanMvpArchitecture.domain.DataManager;
import io.fabric.sdk.android.Fabric;
import javax.inject.Inject;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by xix on 3/12/17.
 */

public class ProjectApplication extends Application {

    protected ApplicationComponent mApplicationComponent;
    @Inject CalligraphyConfig mCalligraphyConfig;
    @Inject DataManager mDataManager;

    public static ProjectApplication get(Context context) {
        return (ProjectApplication) context.getApplicationContext();
    }

    @Override public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }else {
            Fabric.with(this, new Crashlytics());

        }
        mApplicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        mApplicationComponent.inject(this);

        CalligraphyConfig.initDefault(mCalligraphyConfig);
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }
}
