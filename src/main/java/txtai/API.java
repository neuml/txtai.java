package txtai;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Interface to Retrofit library. Provides common functionality.
 */
public class API {
    /**
     * IndexResult
     */
    public static class IndexResult {
        public int id;
        public double score;

        public IndexResult(int id, double score) {
            this.id = id;
            this.score = score;
        }
    }

    /**
     * Creates a new Retrofit API instance.
     * 
     * @param url base url
     * @param type service interface class
     * @return instance of type
     */
    public static <T> T create(String url, Class<T> type) {
        // Configure API instance
        Retrofit retrofit =
            new Retrofit.Builder()
                .baseUrl(url)
                .client(API.client())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create and return API
        return retrofit.create(type);
    }

    /**
     * Builds a custom http client that raises an Exception when calls are not
     * successful.
     * 
     * @return client instance
     */
    public static OkHttpClient client() {
        return new OkHttpClient.Builder()  
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    Response response = chain.proceed(request);

                    if (!response.isSuccessful()) {
                        throw new IOException(response.toString());
                    }

                    return response;
                }
             })
            .build();
    }
}
