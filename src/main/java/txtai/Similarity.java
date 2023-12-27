package txtai;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import txtai.API.IndexResult;

/**
 * txtai similarity instance
 */
@SuppressWarnings("rawtypes")
public class Similarity {
    private Remote api;

    /**
     * API definition
     */
    public interface Remote {
        @POST("similarity")
        Call<List<IndexResult>> similarity(@Body HashMap params);

        @POST("batchsimilarity")
        Call<List<List<IndexResult>>> batchsimilarity(@Body HashMap params);
    }

    /**
     * Creates a Similarity instance.
     */
    public Similarity() {
        // Create API instance
        this.api = API.create(null, null, Remote.class);
    }

    /**
     * Creates a Similarity instance.
     * 
     * @param url API url
     */
    public Similarity(String url) {
        // Create API instance
        this.api = API.create(url, null, Remote.class);
    }

    /**
     * Creates a Similarity instance.
     * 
     * @param url API url
     * @param token API token
     */
    public Similarity(String url, String token) {
        // Create API instance
        this.api = API.create(url, token, Remote.class);
    }

    /**
     * Computes the similarity between query and list of text. Returns a list of
     * {id: value, score: value} sorted by highest score, where id is the index
     * in texts.
     *
     * @param query query text
     * @param texts list of text
     * @return list of {id: value, score: value}
     */
    public List<IndexResult> similarity(String query, List<String> texts) throws IOException {
        // Post parameters
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("query", query);
        params.put("texts", texts);

        return this.api.similarity(params).execute().body();
    }

    /**
     * Computes the similarity between list of queries and list of text. Returns a list
     * of {id: value, score: value} sorted by highest score per query, where id is the
     * index in texts.
     * 
     * @param queries queries text
     * @param texts list of text
     * @return list of {id: value, score: value} per query
     */
    public List<List<IndexResult>> batchsimilarity(List<String> queries, List<String> texts) throws IOException {
        // Post parameters
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("queries", queries);
        params.put("texts", texts);

        return this.api.batchsimilarity(params).execute().body();
    }
}
