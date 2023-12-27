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
public class Textractor {
    private Remote api;

    /**
     * API definition
     */
    public interface Remote {
        @GET("textract")
        Call<Object> textract(@Query("file") String file);

        @POST("batchtextract")
        Call<List<Object>> batchtextract(@Body HashMap params);
    }

    /**
     * Creates a Textractor instance.
     */
    public Textractor() {
        // Create API instance
        this.api = API.create(null, null, Remote.class);
    }

    /**
     * Creates a Textractor instance.
     * 
     * @param url API url
     */
    public Textractor(String url) {
        // Create API instance
        this.api = API.create(url, null, Remote.class);
    }

    /**
     * Creates a Textractor instance.
     * 
     * @param url API url
     * @param token API token
     */
    public Textractor(String url, String token) {
        // Create API instance
        this.api = API.create(url, token, Remote.class);
    }

    /**
     * Extracts text from a file at path.
     * 
     * @param file file to extract text
     * @return extracted text
     */
    public Object textract(String file) throws IOException {
        return this.api.textract(file).execute().body();
    }

    /**
     * Extracts text from a file at path.
     * 
     * @param files list of files to extract text
     * @return list of extracted text
     */
    public List<Object> batchtextract(List<String> files) throws IOException {
        // Post parameters
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("files", files);

        return this.api.batchtextract(params).execute().body();
    }
}
