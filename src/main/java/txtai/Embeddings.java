package txtai;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

import txtai.API.IndexResult;

/**
 * txtai embeddings instance
 */
@SuppressWarnings("rawtypes")
public class Embeddings {
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

        @Multipart
        @POST("addobject")
        Call<Void> addobject(@Part List<MultipartBody.Part> data,
                             @Part("uid") List<RequestBody> uid,
                             @Part("field") RequestBody field);

        @Multipart
        @POST("addimage")
        Call<Void> addimage(@Part List<MultipartBody.Part> data,
                            @Part("uid") List<RequestBody> uid,
                            @Part("field") RequestBody field);
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
     */
    public Embeddings() {
        // Create API instance
        this.api = API.create(null, null, Remote.class);
    }

    /**
     * Creates an Embeddings instance.
     * 
     * @param url API url
     */
    public Embeddings(String url) {
        // Create API instance
        this.api = API.create(url, null, Remote.class);
    }

    /**
     * Creates an Embeddings instance.
     * 
     * @param url API url
     * @param token API token
     */
    public Embeddings(String url, String token) {
        // Create API instance
        this.api = API.create(url, token, Remote.class);
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

    /**
     * Adds a batch of binary objects for indexing.
     *
     * @param data list of binary data as byte arrays
     * @param uid list of corresponding ids (optional, can be null)
     * @param field optional object field name (can be null)
     */
    public void addobject(List<byte[]> data, List<String> uid, String field) throws IOException {
        List<MultipartBody.Part> parts = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), data.get(i));
            parts.add(MultipartBody.Part.createFormData("data", "file" + i, body));
        }

        List<RequestBody> uidBodies = null;
        if (uid != null) {
            uidBodies = new ArrayList<>();
            for (String id : uid) {
                uidBodies.add(RequestBody.create(MediaType.parse("text/plain"), id));
            }
        }

        RequestBody fieldBody = field != null ? RequestBody.create(MediaType.parse("text/plain"), field) : null;

        this.api.addobject(parts, uidBodies, fieldBody).execute();
    }

    /**
     * Adds a batch of binary objects for indexing using files.
     *
     * @param files list of files to upload
     * @param uid list of corresponding ids (optional, can be null)
     * @param field optional object field name (can be null)
     */
    public void addobject(File[] files, List<String> uid, String field) throws IOException {
        List<MultipartBody.Part> parts = new ArrayList<>();
        for (File file : files) {
            RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), file);
            parts.add(MultipartBody.Part.createFormData("data", file.getName(), body));
        }

        List<RequestBody> uidBodies = null;
        if (uid != null) {
            uidBodies = new ArrayList<>();
            for (String id : uid) {
                uidBodies.add(RequestBody.create(MediaType.parse("text/plain"), id));
            }
        }

        RequestBody fieldBody = field != null ? RequestBody.create(MediaType.parse("text/plain"), field) : null;

        this.api.addobject(parts, uidBodies, fieldBody).execute();
    }

    /**
     * Adds a batch of images for indexing.
     *
     * @param files list of image files to upload
     * @param uid list of corresponding ids
     * @param field optional object field name (can be null)
     */
    public void addimage(File[] files, List<String> uid, String field) throws IOException {
        List<MultipartBody.Part> parts = new ArrayList<>();
        for (File file : files) {
            String contentType = getImageContentType(file.getName());
            RequestBody body = RequestBody.create(MediaType.parse(contentType), file);
            parts.add(MultipartBody.Part.createFormData("data", file.getName(), body));
        }

        List<RequestBody> uidBodies = null;
        if (uid != null) {
            uidBodies = new ArrayList<>();
            for (String id : uid) {
                uidBodies.add(RequestBody.create(MediaType.parse("text/plain"), id));
            }
        }

        RequestBody fieldBody = field != null ? RequestBody.create(MediaType.parse("text/plain"), field) : null;

        this.api.addimage(parts, uidBodies, fieldBody).execute();
    }

    /**
     * Adds a batch of images for indexing using byte arrays.
     *
     * @param data list of image data as byte arrays
     * @param uid list of corresponding ids
     * @param field optional object field name (can be null)
     */
    public void addimage(List<byte[]> data, List<String> uid, String field) throws IOException {
        List<MultipartBody.Part> parts = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            RequestBody body = RequestBody.create(MediaType.parse("image/jpeg"), data.get(i));
            parts.add(MultipartBody.Part.createFormData("data", "image" + i + ".jpg", body));
        }

        List<RequestBody> uidBodies = null;
        if (uid != null) {
            uidBodies = new ArrayList<>();
            for (String id : uid) {
                uidBodies.add(RequestBody.create(MediaType.parse("text/plain"), id));
            }
        }

        RequestBody fieldBody = field != null ? RequestBody.create(MediaType.parse("text/plain"), field) : null;

        this.api.addimage(parts, uidBodies, fieldBody).execute();
    }

    /**
     * Determines the content type based on file extension.
     *
     * @param filename the filename to check
     * @return the content type string
     */
    private String getImageContentType(String filename) {
        String lower = filename.toLowerCase();
        if (lower.endsWith(".png")) {
            return "image/png";
        } else if (lower.endsWith(".gif")) {
            return "image/gif";
        } else if (lower.endsWith(".webp")) {
            return "image/webp";
        } else if (lower.endsWith(".bmp")) {
            return "image/bmp";
        }
        return "image/jpeg";
    }
}
