package br.com.daniel.hackathon.network.api;

import com.pixplicity.easyprefs.library.Prefs;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import br.com.daniel.hackathon.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    private static Retrofit retrofit;

    public static <S> S getRetrofitInstance(Class<S> serviceClass) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient
        .Builder()
        .readTimeout(15, TimeUnit.SECONDS);
        httpClient.addInterceptor(loggingInterceptor);
        httpClient.readTimeout(1000, TimeUnit.SECONDS);
        httpClient.writeTimeout(1000, TimeUnit.SECONDS);


        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        return retrofit.create(serviceClass);
    }

}
