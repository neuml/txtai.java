package txtai;

import java.io.IOException;
import java.util.List;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * txtai extractor instance. 
 */
@SuppressWarnings("rawtypes")
public class Extractor {
    private Remote api;

    /**
     * API definition
     */
    public interface Remote {
        @POST("extract")
        Call<List<Answer>> extract(@Body HashMap params);
    }
 
    /**
     * Question parameters.
     */
    public static class Question {
        public String name;
        public String query;
        public String question;
        public boolean snippet;

        /**
         * Creates a question.
         * 
         * @param name question id
         * @param query filtering query 
         * @param question question
         * @param snippet true if surrounding text should be used, false if only answer
         */
        public Question(String name, String query, String question, boolean snippet) {
            this.name = name;
            this.query = query;
            this.question = question;
            this.snippet = snippet;
        }
    }

    /**
     * Answer response.
     */
    public static class Answer {
        public String name;
        public String answer;

        /**
         * Creates an answer.
         * 
         * @param name question identifier/name
         * @param answer answer to question
         */
        public Answer(String name, String answer) {
            this.name = name;
            this.answer = answer;
        }

        /**
         * Answer as String.
         */
        public String toString() {
            return this.name + " " + this.answer;
        }
    }

    /**
     * Creates an Extractor instance.
     */
    public Extractor() {
        // Create API instance
        this.api = API.create(null, null, Remote.class);
    }

    /**
     * Creates an Extractor instance.
     * 
     * @param url API url
     */
    public Extractor(String url) {
        // Create API instance
        this.api = API.create(url, null, Remote.class);
    }

    /**
     * Creates an Extractor instance.
     * 
     * @param url API url
     * @param token API token
     */
    public Extractor(String url, String token) {
        // Create API instance
        this.api = API.create(url, token, Remote.class);
    }

    /**
     * Extracts answers to input questions.
     * 
     * @param queue list of {name: value, query: value, question: value, snippet: value}
     * @param text list of text
     * @return list of {name: value, answer: value}
     */
    public List<Answer> extract(List<Question> queue, List<String> texts) throws IOException {
        // Post parameters
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("queue", queue);
        params.put("texts", texts);

        // Execute API call
        return this.api.extract(params).execute().body();
    }
}
