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
     * @param url API url
     * @param token API token
     * @param type service interface class
     * @return instance of type
     */
    public static <T> T create(String url, String token, Class<T> type) {
        // Default url and token to environment variables, if empty
        url = url != null ? url : System.getenv("TXTAI_API_URL");
        token = token != null ? token : System.getenv("TXTAI_API_TOKEN");

        // Configure API instance
        Retrofit retrofit =
            new Retrofit.Builder()
                .baseUrl(url)
                .client(API.client(token))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create and return API
        return retrofit.create(type);
    }

    /**
     * Builds a custom http client that raises an Exception when calls are not
     * successful.
     * 
     * @param token API token
     * @return client instance
     */
    public static OkHttpClient client(String token) {
        return new OkHttpClient.Builder()  
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();

                    // Add authorization header
                    if (token != null) {
                        request = request.newBuilder().addHeader("Authorization", "Bearer " + token).build();
                    }

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
