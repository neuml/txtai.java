package txtai;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

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
        Call<List<List>> label(@Body HashMap params);
    }
 
    /**
     * Label score.
     */
    public static class Score {
        public String id;
        public double score;

        public Score(String id, double score) {
            this.id = id;
            this.score = score;
        }
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
     * Applies a zero shot classifier to a text section using a list of labels.
     * 
     * @param text input text
     * @param labels list of labels
     * @return list of Scores
     */
    public List<Score> label(String text, List<String> labels) throws IOException {
        // Post parameters
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("text", text);
        params.put("labels", labels);

        // Execute API call
        List<List> response = this.api.label(params).execute().body();

        // Map array results to scores
        List<Score> scores = new ArrayList<Score>();
        for (List entry: response) {
            scores.add(new Score((String)entry.get(0), (double)entry.get(1)));
        }

        return scores;
    }
}
