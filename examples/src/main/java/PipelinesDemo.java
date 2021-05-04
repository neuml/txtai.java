import java.util.Arrays;
import java.util.List;

import txtai.Segmentation;
import txtai.Summary;
import txtai.Textractor;
import txtai.Transcription;
import txtai.Translation;
import txtai.Workflow;

/**
 * Example pipeline and workflow functionality.
 *
 * Uses files from txtai unit tests: https://github.com/neuml/txtai/releases/download/v2.0.0/tests.tar.gz
 */
public class PipelinesDemo {
    public static void main(String[] args) {
        try {
            String service = "http://localhost:8000";

            Segmentation segment = new Segmentation(service);

            String sentences = "This is a test. And another test.";

            System.out.println("---- Segmented Text ----");
            System.out.println(segment.segment(sentences));

            Textractor textractor = new Textractor(service);
            String text = (String)textractor.textract("/tmp/txtai/article.pdf");

            System.out.println("\n---- Extracted Text ----");
            System.out.println(text);

            Summary summary = new Summary(service);
            String summarytext = summary.summary(text, -1, -1);

            System.out.println("\n---- Summary Text ----");
            System.out.println(summarytext);

            Translation translate = new Translation(service);
            String translation = translate.translate(summarytext, "es", null);

            System.out.println("\n---- Summary Text in Spanish ----");
            System.out.println(translation);

            Workflow workflow = new Workflow(service);
            List<Object> output = workflow.workflow("sumspanish", Arrays.asList("file:///tmp/txtai/article.pdf"));

            System.out.println("\n---- Workflow [Extract Text->Summarize->Translate] ----");
            System.out.println(output);

            Transcription transcribe = new Transcription(service);
            String transcription = transcribe.transcribe("/tmp/txtai/Make_huge_profits.wav");

            System.out.println("\n---- Transcribed Text ----");
            System.out.println(transcription);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
