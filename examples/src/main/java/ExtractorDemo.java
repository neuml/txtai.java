import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import txtai.Extractor;
import txtai.Extractor.Answer;
import txtai.Extractor.Question;

/**
 * Example extractor functionality.
 * 
 * Implements logic found in this notebook: https://github.com/neuml/txtai/blob/master/examples/05_Extractive_QA_with_txtai.ipynb
 */
public class ExtractorDemo {
    public static void main(String[] args) {
        try {
            Extractor extractor = new Extractor("http://localhost:8000");

            List<String> data =
                Arrays.asList("Giants hit 3 HRs to down Dodgers",
                              "Giants 5 Dodgers 4 final",
                              "Dodgers drop Game 2 against the Giants, 5-4",
                              "Blue Jays beat Red Sox final score 2-1",
                              "Red Sox lost to the Blue Jays, 2-1",
                              "Blue Jays at Red Sox is over. Score: 2-1",
                              "Phillies win over the Braves, 5-0",
                              "Phillies 5 Braves 0 final",
                              "Final: Braves lose to the Phillies in the series opener, 5-0",
                              "Lightning goaltender pulled, lose to Flyers 4-1",
                              "Flyers 4 Lightning 1 final",
                              "Flyers win 4-1");

            // Run series of questions
            List<String> questions = Arrays.asList("What team won the game?", "What was score?");
            for (String query: Arrays.asList("Red Sox - Blue Jays", "Phillies - Braves", "Dodgers - Giants", "Flyers - Lightning")) {
                System.out.println("----" + query + "----");

                List<Question> queue = new ArrayList<Question>();
                for (String question: questions) {
                    queue.add(new Question(question, query, question, false));
                }
                
                for (Answer answer: extractor.extract(queue, data)) {
                    System.out.println(answer);
                }
                System.out.println();
            }

            // Ad-hoc questions
            String question = "What hockey team won?";
            
            System.out.println("----" + question + "----");
            List<Question> queue = new ArrayList<Question>();
            queue.add(new Question(question, question, question, false));
            
            for (Answer answer: extractor.extract(queue, data)) {
                System.out.println(answer);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }   
}
