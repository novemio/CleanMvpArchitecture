package com.xix.cleanMvpArchitecture.data.network;

import com.xix.cleanMvpArchitecture.data.model.Item;
import io.reactivex.Observable;
import java.util.List;
import retrofit2.Response;
import retrofit2.http.GET;

/**
 * Created by 8 on 12.09.2016.
 */
public interface ItemsNetworkService {

    @GET("items.json")
     Observable<Response<List<Item>>> getItems();




}
