package com.xix.cleanMvpArchitecture.domain;

import dagger.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by xix on 4/8/17.
 */

public class UseCase<T> {

    private final CompositeDisposable disposables;
    private Observable<T> mTObservable;

    public UseCase(Observable<T> TObservable) {
        mTObservable = TObservable;
        this.disposables = new CompositeDisposable();
    }

    public Disposable execute(DefaultObserver<T> tDefaultObserver) {
      return addDisposable(mTObservable.subscribeWith(tDefaultObserver));
    }

    /**
     * Dispose from current {@link CompositeDisposable}.
     */
    private Disposable addDisposable(Disposable disposable) {
        Preconditions.checkNotNull(disposable);
        Preconditions.checkNotNull(disposables);
        disposables.add(disposable);

        return disposable;
    }

    public void dispose() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }

   public CompositeDisposable getDisposable(){
        return disposables;
    }
}
