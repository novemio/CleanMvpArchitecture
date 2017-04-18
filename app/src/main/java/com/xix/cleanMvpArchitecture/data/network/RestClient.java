package com.xix.cleanMvpArchitecture.data.network;

import android.util.Log;
import com.xix.cleanMvpArchitecture.data.model.Item;
import com.xix.cleanMvpArchitecture.data.network.exception.RestHttpException;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import java.util.List;
import retrofit2.Response;

/**
 * Created by xix on 22.3.17.
 *
 */
public class RestClient {
    private static final String NOT_AUTHORIZED = "notAuthorized";
    private static final String LOG_TAG = RestClient.class.getSimpleName();
    private static final String IDENTITY = "identity";
    private String sessionId = NOT_AUTHORIZED;
    private ItemsNetworkService mItemsNetworkService;
    private final static String CLOUD_URL = "https://raw.githubusercontent.com/danieloskarsson/mobile-coding-exercise/master/";
    private static RestClient instance;

    public static RestClient getInstance() {
        if (instance == null) {
            instance = new RestClient();
        }
        return instance;
    }


    private RestClient() {
        if (mItemsNetworkService == null) {
            mItemsNetworkService = ServiceGenerator.createService(ItemsNetworkService.class, CLOUD_URL);
        }
    }

    public Observable<List<Item>> getItems() {
        Observable<Response<List<Item>>> request = mItemsNetworkService.getItems();
        return request.flatMap(response -> Observable.create((ObservableOnSubscribe<List<Item>>) observableEmitter -> {
            if (response.isSuccessful()) {
                observableEmitter.onNext(response.body());
            } else {
                observableEmitter.onError(new RestHttpException(response));
            }
        }));
    }

    private boolean isSessionValid() {
        boolean session = !sessionId.equals(NOT_AUTHORIZED);
        if (!session) {
            Log.e(LOG_TAG, "SessionId is not valid");
        }
        return session;
    }
}