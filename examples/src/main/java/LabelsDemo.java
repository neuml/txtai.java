import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import txtai.Labels;
import txtai.Labels.Score;

/**
 * Example labels functionality.
 * 
 * Implements logic found in this notebook: https://github.com/neuml/txtai/blob/master/examples/05_Labeling_with_zero_shot_classification.ipynb
 */
public class LabelsDemo {
    public static void main(String[] args) {
        try {
            Labels labels = new Labels("http://localhost:8000");

            List <String> sections = 
                Arrays.asList("Dodgers lose again, give up 3 HRs in a loss to the Giants",
                              "Giants 5 Cardinals 4 final in extra innings",
                              "Dodgers drop Game 2 against the Giants, 5-4",
                              "Flyers 4 Lightning 1 final. 45 saves for the Lightning.",
                              "Slashing, penalty, 2 minute power play coming up",
                              "What a stick save!",
                              "Leads the NFL in sacks with 9.5",
                              "UCF 38 Temple 13",
                              "With the 30 yard completion, down to the 10 yard line",
                              "Drains the 3pt shot!!, 0:15 remaining in the game",
                              "Intercepted! Drives down the court and shoots for the win",
                              "Massive dunk!!! they are now up by 15 with 2 minutes to go");

            // List of labels
            List<String> tags = Arrays.asList("Baseball", "Football", "Hockey", "Basketball");

            System.out.printf("%-75s %s%n", "Text", "Label");
            System.out.println(new String(new char[100]).replace("\0", "-"));

            for (String text: sections) {
                List<Score> scores = labels.label(text, tags);
                System.out.printf("%-75s %s%n", text, scores.get(0).id);
            }

            System.out.println();
            System.out.printf("%-75s %s%n", "Text", "Label");
            System.out.println(new String(new char[100]).replace("\0", "-"));

            tags = Arrays.asList("😀", "😡");

            for (String text: sections) {
                List<Score> scores = labels.label(text, tags);
                System.out.printf("%-75s %s%n", text, scores.get(0).id);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
