package txtai;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * txtai workflow instance
 */
@SuppressWarnings("rawtypes")
public class Workflow {
    private Remote api;

    /**
     * API definition
     */
    public interface Remote {
        @POST("workflow")
        Call<List<Object>> workflow(@Body HashMap params);
    }

    /**
     * Creates a Workflow instance.
     */
    public Workflow() {
        // Create API instance
        this.api = API.create(null, null, Remote.class);
    }

    /**
     * Creates a Workflow instance.
     * 
     * @param url API url
     */
    public Workflow(String url) {
        // Create API instance
        this.api = API.create(url, null, Remote.class);
    }

    /**
     * Creates a Workflow instance.
     * 
     * @param url API url
     * @param token API token
     */
    public Workflow(String url, String token) {
        // Create API instance
        this.api = API.create(url, token, Remote.class);
    }

    /**
     * Executes a named workflow using elements as input.
     *
     * @param name workflow name
     * @param elements list of elements to run through workflow
     * @return list of processed elements
     */
    public List<Object> workflow(String name, List<String> elements) throws IOException {
        // Post parameters
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("name", name);
        params.put("elements", elements);

        return this.api.workflow(params).execute().body();
    }
}
