package txtai;

import java.io.IOException;
import java.util.List;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.POST;

/**
 * txtai embeddings instance
 */
@SuppressWarnings("rawtypes")
public class Embeddings {
    private String url;
    private Remote api;

    /**
     * API definition
     */
    public interface Remote {
        @GET("search")
        Call<List> search(@Query("q") String q, @Query("n") int n);

        @POST("add")
        Call<Void> add(@Body List documents);

        @GET("index")
        Call<Void> index();

        @POST("similarity")
        Call<List<Double>> similarity(@Body HashMap params);

        @GET("embeddings")
        Call<List<Double>> embeddings(@Query("t") String t);
    }

    /**
     * Default Document.
     */
    public static class Document {
        public String id;
        public String text;

        /**
         * Creates a document.
         * 
         * @param id document id
         * @param text document text
         */
        public Document(String id, String text) {
            this.id = id;
            this.text = text;
        }
    }

    /**
     * Creates an Embeddings instance.
     * 
     * @param url base url of txtai API
     */
    public Embeddings(String url) {
        // Create API instance
        this.url = url;
        this.api = API.create(this.url, Remote.class);
    }

    /**
     * Runs an embeddings search.
     * 
     * @param q query string
     * @param n number of results to return (defaults to 10)
     * @return search results
     */
    public List search(String q, int n) throws IOException {
        return this.api.search(q, n).execute().body();
    }

    /**
     * Adds a batch of documents for indexing.
     * 
     * @param documents list of objects each containing an id and text element
     */
    public void add(List documents) throws IOException {
        this.api.add(documents).execute();
    }

    /**
     * Builds an embeddings index. No further documents can be added after this call.
     */
    public void index() throws IOException {
        this.api.index().execute();
    }

    /**
     * Calculates the similarity between search and list of elements in data.
     * 
     * @param search text
     * @param data list of text to compare against
     * @return list of similarity scores
     */
    public List<Double> similarity(String search, List data) throws IOException {
        // Post parameters
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("search", search);
        params.put("data", data);

        return this.api.similarity(params).execute().body();
    }

    /**
     * Transforms text into an embeddings array.
     * 
     * @param t input text
     * @return embeddings array
     */
    public List<Double> embeddings(String t) throws IOException {
        return this.api.embeddings(t).execute().body();
    }
}
