package domain.service;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.annotation.Nullable;

public class MovietRequester {
    public static final String API_HOST = "localhost";
    public static final String API_VERSION = "1";
    public static final String API_URL = "http://" + API_HOST + "/api/v" + API_VERSION + "/";

    @Nullable
    private OkHttpClient okHttpClient;
    @Nullable
    private Retrofit retrofit;

    protected synchronized OkHttpClient okHttpClient() {
        if (okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            // builder interceptor could be added here
            okHttpClient = builder.build();
        }
        return okHttpClient;
    }

    protected Retrofit.Builder retrofitBuilder() {
        return new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create(MovietRequesterHelper.getGsonBuilder().create()))
                .client(okHttpClient());
    }

    protected Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = retrofitBuilder().build();
        }
        return retrofit;
    }

    public GroupServiceRequesterInterface groupRequester() {
        return getRetrofit().create(GroupServiceRequesterInterface.class);
    }

    public MovieServiceRequesterInterface movieRequester() {
        return getRetrofit().create(MovieServiceRequesterInterface.class);
    }

}
