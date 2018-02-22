package proj.test.com.articles.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NytimesAdapter {
    private static final String API_KEY = "65fdacee91bc450fb4bd40cd461d8f24";
    private static final String BASE_URL = "http://api.nytimes.com/svc/mostpopular/v2/";

    private static NytimesAdapter instance = new NytimesAdapter();

    private NytimesApi nytimesApi;

    private NytimesAdapter() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addNetworkInterceptor(new AddHeaderInterceptor());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        nytimesApi = retrofit.create(NytimesApi.class);
    }

    public static NytimesAdapter getInstance() {
        return instance;
    }

    public NytimesApi getApi() {
        return nytimesApi;
    }

    private class AddHeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();
            builder.addHeader("api-key", API_KEY);
            return chain.proceed(builder.build());
        }
    }

}
