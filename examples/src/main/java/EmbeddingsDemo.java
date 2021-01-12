import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import txtai.API.IndexResult;
import txtai.Embeddings;
import txtai.Embeddings.Document;
import txtai.Embeddings.SearchResult;

/**
 * Example embeddings functionality.
 * 
 * Implements functionality found in this notebook: https://github.com/neuml/txtai/blob/master/examples/01_Introducing_txtai.ipynb
 */
public class EmbeddingsDemo {
    public static void main(String[] args) {
        try {
            Embeddings embeddings = new Embeddings("http://localhost:8000");

            List<String> data =
                Arrays.asList("US tops 5 million confirmed virus cases", 
                              "Canada's last fully intact ice shelf has suddenly collapsed, forming a Manhattan-sized iceberg",
                              "Beijing mobilises invasion craft along coast as Taiwan tensions escalate",
                              "The National Park Service warns against sacrificing slower friends in a bear attack",
                              "Maine man wins $1M from $25 lottery ticket",
                              "Make huge profits without work, earn up to $100,000 a day");

            List<Document> documents = new ArrayList<Document>(); 
            for(int x = 0; x < data.size(); x++) {
                Document d = new Document(String.valueOf(x), data.get(x));
                documents.add(d);
            }

            System.out.println("Running similarity queries");
            System.out.printf("%-20s %s%n", "Query", "Best Match");
            System.out.println(new String(new char[50]).replace("\0", "-"));

            for (String query: Arrays.asList("feel good story", "climate change", "health", "war", "wildlife", "asia", "north america", "dishonest junk")) {
                List<IndexResult> results = embeddings.similarity(query, data);
                int uid = results.get(0).id;
                System.out.printf("%-20s %s%n", query, data.get(uid));
            }

            embeddings.add(documents);
            embeddings.index();

            System.out.println("\nBuilding an Embeddings index");
            System.out.printf("%-20s %s%n", "Query", "Best Match");
            System.out.println(new String(new char[50]).replace("\0", "-"));

            for (String query: Arrays.asList("feel good story", "climate change", "health", "war", "wildlife", "asia", "north america", "dishonest junk")) {
                List<SearchResult> results = embeddings.search(query, 1);
                int uid = Integer.parseInt(results.get(0).id);
                System.out.printf("%-20s %s%n", query, data.get(uid));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }    
}
