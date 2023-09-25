package txtai;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import txtai.API.IndexResult;

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
        Call<List<SearchResult>> search(@Query("query") String query, @Query("limit") int limit,
                                        @Query("weights") Float weights, @Query("index") String index);

        @POST("batchsearch")
        Call<List<List<SearchResult>>> batchsearch(@Body HashMap params);

        @POST("add")
        Call<Void> add(@Body List documents);

        @GET("index")
        Call<Void> index();

        @GET("upsert")
        Call<Void> upsert();

        @POST("delete")
        Call<List<String>> delete(@Body List<String> ids);

        @POST("reindex")
        Call<Void> reindex(@Body HashMap params);

        @GET("count")
        Call<Integer> count();

        @POST("similarity")
        Call<List<IndexResult>> similarity(@Body HashMap params);

        @POST("batchsimilarity")
        Call<List<List<IndexResult>>> batchsimilarity(@Body HashMap params);

        @GET("transform")
        Call<List<Double>> transform(@Query("text") String text);

        @POST("batchtransform")
        Call<List<List<Double>>> batchtransform(@Body List<String> texts);
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
     * SearchResult
     */
    public static class SearchResult {
        public String id;
        public double score;

        public SearchResult(String id, double score) {
            this.id = id;
            this.score = score;
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
     * Finds documents in the embeddings model most similar to the input query. Returns
     * a list of {id: value, score: value} sorted by highest score, where id is the
     * document id in the embeddings model.
     * 
     * @param query query text
     * @param limit maximum results
     * @param weights hybrid score weights, if applicable
     * @param index index name, if applicable
     * @return list of {id: value, score: value}
     */
    public List<SearchResult> search(String query, int limit, Float weights, String index) throws IOException {
        return this.api.search(query, limit, weights, index).execute().body();
    }

    /**
     * Finds documents in the embeddings model most similar to the input queries. Returns
     * a list of {id: value, score: value} sorted by highest score per query, where id is
     * the document id in the embeddings model.
     *
     * @param queries queries text
     * @param limit maximum results
     * @param weights hybrid score weights, if applicable
     * @param index index name, if applicable
     * @return list of {id: value, score: value} per query
     */
    public List<List<SearchResult>> batchsearch(List<String> queries, int limit, Float weights, String index) throws IOException {
        // Post parameters
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("queries", queries);
        params.put("limit", limit);
        params.put("weights", weights);
        params.put("index", index);

        return this.api.batchsearch(params).execute().body();
    }

    /**
     * Adds a batch of documents for indexing.
     * 
     * @param documents list of {id: value, text: value}
     */
    public void add(List documents) throws IOException {
        this.api.add(documents).execute();
    }

    /**
     * Builds an embeddings index for previously batched documents.
     */
    public void index() throws IOException {
        this.api.index().execute();
    }

    /**
     * Runs an embeddings upsert operation for previously batched documents.
     */
    public void upsert() throws IOException {
        this.api.upsert().execute();
    }

    /**
     * Deletes from an embeddings index. Returns list of ids deleted.
     *
     * @param ids list of ids to delete
     * @return ids deleted
     */
    public List<String> delete(List<String> ids) throws IOException {
        return this.api.delete(ids).execute().body();
    }

    /**
     * Recreates this embeddings index using config. This method only works if document content storage is enabled.
     *
     * @param config new config
     * @param function optional function to prepare content for indexing
     */
    public void reindex(HashMap config, String function) throws IOException {
        // Post parameters
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("config", config);
        params.put("function", function);

        this.api.reindex(params).execute();
    }

    /**
     * Total number of elements in this embeddings index.
     *
     * @return number of elements in embeddings index
     */
    public int count() throws IOException {
        return this.api.count().execute().body();
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

    /**
     * Transforms text into an embeddings array.
     * 
     * @param text input text
     * @return embeddings array
     */
    public List<Double> transform(String text) throws IOException {
        return this.api.transform(text).execute().body();
    }

    /**
     * Transforms list of text into embeddings arrays.
     *
     * @param texts list of text
     * @return embeddings arrays
     */
    public List<List<Double>> batchtransform(List<String> texts) throws IOException {
        return this.api.batchtransform(texts).execute().body();
    }
}
