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
 * txtai segmentation instance
 */
@SuppressWarnings("rawtypes")
public class Segmentation {
    private Remote api;

    /**
     * API definition
     */
    public interface Remote {
        @GET("segment")
        Call<Object> segment(@Query("text") String text);

        @POST("batchsegment")
        Call<List<Object>> batchsegment(@Body HashMap params);
    }

    /**
     * Creates a Segmentation instance.
     */
    public Segmentation() {
        // Create API instance
        this.api = API.create(null, null, Remote.class);
    }

    /**
     * Creates a Segmentation instance.
     * 
     * @param url API url
     */
    public Segmentation(String url) {
        // Create API instance
        this.api = API.create(url, null, Remote.class);
    }

    /**
     * Creates a Segmentation instance.
     * 
     * @param url API url
     * @param token API token
     */
    public Segmentation(String url, String token) {
        // Create API instance
        this.api = API.create(url, token, Remote.class);
    }

    /**
     * Segments text into semantic units.
     * 
     * @param text input text
     * @return segmented text
     */
    public Object segment(String text) throws IOException {
        return this.api.segment(text).execute().body();
    }

    /**
     * Segments text into semantic units.
     * 
     * @param texts list of texts to segment
     * @return list of segmented text
     */
    public List<Object> batchsegment(List<String> texts) throws IOException {
        // Post parameters
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("texts", texts);

        return this.api.batchsegment(params).execute().body();
    }
}
