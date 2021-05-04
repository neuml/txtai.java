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
    private String url;
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
     * 
     * @param url base url of txtai API
     */
    public Segmentation(String url) {
        // Create API instance
        this.url = url;
        this.api = API.create(this.url, Remote.class);
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
