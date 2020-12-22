package txtai;

import java.io.IOException;
import java.util.ArrayList;
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
    private String url;
    private Remote api;

    /**
     * API definition
     */
    public interface Remote {
        @POST("extract")
        Call<List<List<String>>> extract(@Body HashMap params);
    }
 
    /**
     * Text section.
     */
    public static class Section {
        public int id;
        public String text;

        public Section(int id, String text) {
            this.id = id;
            this.text = text;
        }
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
        public String question;
        public String answer;

        /**
         * Creates an answer.
         * 
         * @param question question
         * @param answer answer to question
         */
        public Answer(String question, String answer) {
            this.question = question;
            this.answer = answer;
        }

        /**
         * Answer as String.
         */
        public String toString() {
            return this.question + " " + this.answer;
        }
    }

    /**
     * Creates an Extractor instance.
     * 
     * @param url base url of txtai API
     */
    public Extractor(String url) {
        // Create API instance
        this.url = url;
        this.api = API.create(this.url, Remote.class);
    }

    /**
     * Extracts answers to input questions
     * 
     * @param documents list of {id: value, text: value}
     * @param queue list of {name: value, query: value, question: value, snippet: value)
     * @return extracted answers
     */
    public List<Answer> extract(List<Section> documents, List<Question> queue) throws IOException {
        // Post parameters
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("documents", documents);
        params.put("queue", queue);

        // Execute API call
        List<List<String>> response = this.api.extract(params).execute().body();

        // Map array results to answers
        List<Answer> answers = new ArrayList<Answer>();
        for (List<String> entry: response) {
            answers.add(new Answer(entry.get(0), entry.get(1)));
        }

        return answers;
    }
}
