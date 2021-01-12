package txtai;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import txtai.API.IndexResult;

/**
 * txtai labels instance. 
 */
@SuppressWarnings("rawtypes")
public class Labels {
    private String url;
    private Remote api;

    /**
     * API definition
     */
    public interface Remote {
        @POST("label")
        Call<List<IndexResult>> label(@Body HashMap params);

        @POST("batchlabel")
        Call<List<List<IndexResult>>> batchlabel(@Body HashMap params);
    }

    /**
     * Creates a Labels instance.
     * 
     * @param url base url of txtai API
     */
    public Labels(String url) {
        // Create API instance
        this.url = url;
        this.api = API.create(this.url, Remote.class);
    }

    /**
     * Applies a zero shot classifier to text using a list of labels. Returns a list of
     * {id: value, score: value} sorted by highest score, where id is the index in labels.
     * 
     * @param text input text
     * @param labels list of labels
     * @return list of {id: value, score: value} per text element
     */
    public List<IndexResult> label(String text, List<String> labels) throws IOException {
        // Post parameters
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("text", text);
        params.put("labels", labels);

        // Execute API call
        return this.api.label(params).execute().body();
    }

    /**
     * Applies a zero shot classifier to list of text using a list of labels. Returns a list of
     * {id: value, score: value} sorted by highest score, where id is the index in labels per
     * text element.
     *
     * @param texts list of texts
     * @param labels list of labels
     * @return list of {id: value score: value} per text element
     */
    public List<List<IndexResult>> batchlabel(List<String> texts, List<String> labels) throws IOException {
        // Post parameters
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("texts", texts);
        params.put("labels", labels);

        // Execute API call
        return this.api.batchlabel(params).execute().body();
    }
}
