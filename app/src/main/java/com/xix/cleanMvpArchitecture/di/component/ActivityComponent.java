package com.xix.cleanMvpArchitecture.di.component;

import com.xix.cleanMvpArchitecture.di.PerActivity;
import com.xix.cleanMvpArchitecture.di.module.ActivityModule;
import com.xix.cleanMvpArchitecture.presentation.screens.navigation.NavigationActivity;
import dagger.Component;

/**
 * Created by xix on 3/12/17.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(NavigationActivity activity);

}
