package machinelearning;

import java.util.*;

public class Test
{

    private final String file = "./DataSets/CarEvaluation/car.data.txt";
    private Stack<String> feats;
    private Stack<String> labels;

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        Test t = new Test();
        t.run();

    }

    public void run()
    {
        ExampleSet examples = ExampleSet.getData(file);

        feats = new Stack();
        feats.addAll(Arrays.asList(examples.getFeatures()));

        labels = new Stack();
        labels.addAll(Arrays.asList(examples.getLabels()));

        HashMap<String, HashSet<String>> values = examples.getValues();
        for (String f : feats)
        {
            System.out.print(f + " : ");
            HashSet<String> vals = values.get(f);
            for (String v : vals)
            {
                System.out.print(v + ", ");
            }
            System.out.println();
        }

        System.out.println(examples.size());

        
        
        String currentFeat = feats.pop();
        HashSet<String> allCurrentFeatValues = values.get(currentFeat);
        for (String currentValue : allCurrentFeatValues)
        {
            System.out.println("\ncurrentValue: " + currentValue);
            ExampleSet currentIteration = examples.getExamples(currentFeat, currentValue);
            currentIteration.printProbabilityInfo();
        }

    }
}
