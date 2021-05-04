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
 * txtai translation instance
 */
@SuppressWarnings("rawtypes")
public class Translation {
    private String url;
    private Remote api;

    /**
     * API definition
     */
    public interface Remote {
        @GET("translate")
        Call<String> translate(@Query("text") String text, @Query("target") String target, @Query("source") String source);

        @POST("batchtranslate")
        Call<List<String>> batchtranslate(@Body HashMap params);
    }

    /**
     * Creates a Translation instance.
     * 
     * @param url base url of txtai API
     */
    public Translation(String url) {
        // Create API instance
        this.url = url;
        this.api = API.create(this.url, Remote.class);
    }

    /**
     * Translates text from source language into target language.
     * 
     * @param text text to translate
     * @param target target language code, defaults to "en"
     * @param source source language code, detects language if not provided
     * @return translated text
     */
    public String translate(String text, String target, String source) throws IOException {
        return this.api.translate(text, target, source).execute().body();
    }

    /**
     * Translates text from source language into target language.
     * 
     * @param texts list of text to translate
     * @param target target language code, defaults to "en"
     * @param source source language code, detects language if not provided
     * @return list of translated text
     */
    public List<String> batchtranslate(List<String> texts, String target, String source) throws IOException {
        // Post parameters
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("texts", texts);

        if (target != null) {
            params.put("target", target);
        }
        if (source != null) {
            params.put("source", source);
        }

        return this.api.batchtranslate(params).execute().body();
    }
}
