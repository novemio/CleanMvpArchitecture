package com.xix.cleanMvpArchitecture.presentation.base.mvp;

/**
 * Created by xix on 27/03/17.
 */

import com.xix.cleanMvpArchitecture.domain.DataManager;
import io.reactivex.disposables.CompositeDisposable;
import javax.inject.Inject;

/**
 * Base class that implements the NavigationMvpPresenter interface and provides a base implementation for
 * onAttach() and onDetach(). It also handles keeping a reference to the mvpView that
 * can be accessed from the children classes by calling getMvpView().
 */
public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private static final String TAG = "BasePresenter";

    private final DataManager mDataManager;

    private final CompositeDisposable mCompositeDisposable;

    private V mMvpView;

    @Inject
    public BasePresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        this.mDataManager = dataManager;
        this.mCompositeDisposable = compositeDisposable;
    }

    @Override
    public void onAttach(V mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void onDetach() {
        mCompositeDisposable.dispose();
        mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public V getMvpView() {
        return mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    //@Override
    //public void handleApiError(ANError error) {
    //
    //    if (error == null || error.getErrorBody() == null) {
    //        getMvpView().onError(R.string.api_default_error);
    //        return;
    //    }
    //
    //    if (error.getErrorCode() == AppConstants.API_STATUS_CODE_LOCAL_ERROR
    //            && error.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
    //        getMvpView().onError(R.string.connection_error);
    //        return;
    //    }
    //
    //    if (error.getErrorCode() == AppConstants.API_STATUS_CODE_LOCAL_ERROR
    //            && error.getErrorDetail().equals(ANConstants.REQUEST_CANCELLED_ERROR)) {
    //        getMvpView().onError(R.string.api_retry_error);
    //        return;
    //    }
    //
    //    final GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
    //    final Gson gson = builder.create();
    //
    //    try {
    //        ApiError apiError = gson.fromJson(error.getErrorBody(), ApiError.class);
    //
    //        if (apiError == null || apiError.getMessage() == null) {
    //            getMvpView().onError(R.string.api_default_error);
    //            return;
    //        }
    //
    //        switch (error.getErrorCode()) {
    //            case HttpsURLConnection.HTTP_UNAUTHORIZED:
    //            case HttpsURLConnection.HTTP_FORBIDDEN:
    //                setUserAsLoggedOut();
    //                getMvpView().openActivityOnTokenExpire();
    //            case HttpsURLConnection.HTTP_INTERNAL_ERROR:
    //            case HttpsURLConnection.HTTP_NOT_FOUND:
    //            default:
    //                getMvpView().onError(apiError.getMessage());
    //        }
    //    } catch (JsonSyntaxException | NullPointerException e) {
    //        Log.e(TAG, "handleApiError", e);
    //        getMvpView().onError(R.string.api_default_error);
    //    }
    //}

    @Override
    public void setUserAsLoggedOut() {
        //getDataManager().setAccessToken(null);
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call NavigationMvpPresenter.onAttach(MvpView) before" +
                    " requesting data to the NavigationMvpPresenter");
        }
    }
}
