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
public class Transcription {
    private Remote api;

    /**
     * API definition
     */
    public interface Remote {
        @GET("transcribe")
        Call<String> transcribe(@Query("file") String file);

        @POST("batchtranscribe")
        Call<List<String>> batchtranscribe(@Body HashMap params);
    }

    /**
     * Creates a Transcription instance.
     */
    public Transcription() {
        // Create API instance
        this.api = API.create(null, null, Remote.class);
    }

    /**
     * Creates a Transcription instance.
     * 
     * @param url API url
     */
    public Transcription(String url) {
        // Create API instance
        this.api = API.create(url, null, Remote.class);
    }

    /**
     * Creates a Transcription instance.
     * 
     * @param url API url
     * @param token API token
     */
    public Transcription(String url, String token) {
        // Create API instance
        this.api = API.create(url, token, Remote.class);
    }

    /**
     * Transcribes audio files to text.
     * 
     * @param file file to transcribe
     * @return transcribed text
     */
    public String transcribe(String file) throws IOException {
        return this.api.transcribe(file).execute().body();
    }

    /**
     * Transcribes audio files to text.
     * 
     * @param files list of files to transcribe
     * @return list of transcribed text
     */
    public List<String> batchtranscribe(List<String> files) throws IOException {
        // Post parameters
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("files", files);

        return this.api.batchtranscribe(params).execute().body();
    }
}
