package txtai;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * txtai summary instance
 */
@SuppressWarnings("rawtypes")
public class Summary {
    private Remote api;

    /**
     * API definition
     */
    public interface Remote {
        @GET("summary")
        Call<String> summary(@Query("text") String text, @Query("minlength") Integer minlength, @Query("maxlength") Integer maxlength);

        @POST("batchsummary")
        Call<List<String>> batchsummary(@Body HashMap params);
    }

    /**
     * Creates a Summary instance.
     */
    public Summary() {
        // Create API instance
        this.api = API.create(null, null, Remote.class);
    }

    /**
     * Creates a Summary instance.
     * 
     * @param url API url
     */
    public Summary(String url) {
        // Create API instance
        this.api = API.create(url, null, Remote.class);
    }

    /**
     * Creates a Summary instance.
     * 
     * @param url API url
     * @param token API token
     */
    public Summary(String url, String token) {
        // Create API instance
        this.api = API.create(url, token, Remote.class);
    }

    /**
     * Runs a summarization model against a block of text.
     *
     * @param text text to summarize
     * @param minlength minimum length for summary
     * @param maxlength maximum length for summary
     * @return summary text
     */
    public String summary(String text, int minlength, int maxlength) throws IOException {
        return this.api.summary(text, minlength != -1 ? minlength:null, maxlength != -1 ? maxlength:null).execute().body();
    }

    /**
     * Runs a summarization model against a block of text.
     *
     * @param texts list of text to summarize
     * @param minlength minimum length for summary
     * @param maxlength maximum length for summary
     * @return list of summary text
     */
    public List<String> batchsummary(List<String> texts, int minlength, int maxlength) throws IOException {
        // Post parameters
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("texts", texts);

        if (minlength != -1) {
            params.put("minlength", minlength);
        }
        if (maxlength != -1) {
            params.put("maxlength", maxlength);
        }

        return this.api.batchsummary(params).execute().body();
    }
}
