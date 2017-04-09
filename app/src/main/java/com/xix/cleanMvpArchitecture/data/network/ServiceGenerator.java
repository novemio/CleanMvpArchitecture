package com.xix.cleanMvpArchitecture.data.network;

import android.util.Log;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.xix.cleanMvpArchitecture.BuildConfig;
import com.xix.cleanMvpArchitecture.data.network.exception.RequestException;
import com.xix.cleanMvpArchitecture.data.network.exception.ServerConnectionException;
import java.io.IOException;
import java.net.ConnectException;
import java.util.Locale;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xix on 12.3.17..
 *
 */

/**
 * The ServiceGenerator is our API/HTTP client heart.
 * In its current state, it only defines one method to create a basic  rest adapter for a given class/interface
 */
public class ServiceGenerator {

    private static final String LOG_TAG = ServiceGenerator.class.getSimpleName();

    /**
     * The constant API_BASE_URL.
     */


    private static OkHttpClient.Builder httpClientBuilder;

    /**
     * Create service s.
     *
     * @param <S> the type parameter
     * @param serviceClass the service class
     * @return the s
     */
    public static <S> S createService(Class<S> serviceClass, String CLOUD_URL) {
        Retrofit retrofit = builder(CLOUD_URL).build();
        return retrofit.create(serviceClass);
    }

    private static Retrofit.Builder builder(String API_BASE_URL) {
        initHttpClient();
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClientBuilder.build());
        return builder;
    }

    private static void initHttpClient() {
        httpClientBuilder = new OkHttpClient.Builder().addInterceptor(ServiceGenerator::createHeaderInterceptor);
        if (BuildConfig.DEBUG) {
            Log.d(LOG_TAG, "initHttpClient: add Login Interceptor");
            //HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            //interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //httpClientBuilder.addInterceptor(interceptor);
            httpClientBuilder.addInterceptor(ServiceGenerator::createLogInterceptor);
            httpClientBuilder.addNetworkInterceptor(new StethoInterceptor());

        }
    }

    private static Response createHeaderInterceptor(Interceptor.Chain chain) throws IOException {
        Request request =
            chain.request().newBuilder().addHeader("Accept", "application/json").addHeader("Content-Type", "application/json").build();
        return chain.proceed(request);
    }

    private static Response createLogInterceptor(Interceptor.Chain chain) throws IOException {
        /****************PRINT REQUEST LOG**********************************/
        Request request = chain.request();
        long t1 = System.nanoTime();
        String requestLog = String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers(),t1);
        if (request.method().compareToIgnoreCase("get") != 0) {
        requestLog = "\n" + requestLog + "\n" + "method " + request.method() + "\nbody \n" + bodyToString(request);
        }
        //Log.d("OkHttp", "request" + "\n" + requestLog);
        try {
            Response response = chain.proceed(request);

            /****************PRINT RESPONSE  LOG**********************************/
            long t2 = System.nanoTime();
            String responseLog =
                String.format(Locale.ENGLISH,"Received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d,
                    response.headers
                    ());
            String bodyString = response.body().string();
            JSONObject jsonBody = stringToJson(bodyString); //convert for clear log

            String logBody = null;
            if (jsonBody != null) {
                try {
                    logBody = jsonBody.toString(4);
                } catch (JSONException e) {
                    e.printStackTrace();
                    logBody = bodyString;
                }
            } else {
                logBody = bodyString;
            }

            Log.d(LOG_TAG + "OkHttp",
                "************************************request************************************"
                    + "\n"
                    + requestLog
                    + "\n"
                    +
                    "************************************response************************************"
                    + "\n"
                    + responseLog
                    + "\nStatus: "
                    + response.code()
                    + "\n"
                    + logBody
                    + "\n"
                    + "****************************************************************************** \n");
            // invalid request
            if (!response.isSuccessful() && jsonBody == null) {
                throw new RequestException(bodyString);
            }
            return response.newBuilder().body(ResponseBody.create(response.body().contentType(), bodyString)).build();
        } catch (ConnectException e) {
            throw new ServerConnectionException(e.getMessage());
        }
    }

    /**
     * Body to string string.
     *
     * @param request the request
     * @return the string
     */
    public static String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);

            JSONObject jsonObject = new JSONObject(buffer.readUtf8());
            return jsonObject.toString(4);
        } catch (final IOException e) {
            return "empty";
        } catch (JSONException e) {
            return "empty";
        }
    }

    public static JSONObject stringToJson(String bodyString) {
        try {
            return new JSONObject(bodyString);
        } catch (JSONException e) {
            try {
                return new JSONObject(bodyString.substring(1, bodyString.length() - 1));
            } catch (JSONException e1) {
                return null;
            }
        }
    }


}
