package machinelearning;

import java.util.*;
import org.jgrapht.graph.*;

public class Test
{

    private final String file = "./DataSets/CarEvaluation/car.data.txt";
    private Stack<String> feats;
    private Stack<String> labels;
    private HashMap<String, HashSet<String>> values;

    private SimpleGraph<ExampleSet, String> g;

    public Test()
    {
        feats = new Stack();
        labels = new Stack();

        g = new SimpleGraph<>(String.class);
    }

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

        feats.addAll(Arrays.asList(examples.getFeatures()));
        labels.addAll(Arrays.asList(examples.getLabels()));

        values = examples.getValues();
        /*for (String f : feats)
         {
         System.out.print(f + " : ");
         HashSet<String> vals = values.get(f);
         for (String v : vals)
         {
         System.out.print(v + ", ");
         }
         System.out.println();
         }*/

        //System.out.println(examples.size());
        g.addVertex(examples);
        addFeatToGraph(examples, feats.pop());

        //Visualise what we've added so far
        JGraphFrame jGraphFrame = new JGraphFrame(g);
    }

    private void addFeatToGraph(ExampleSet parent, String feat)
    {

        HashSet<String> allCurrentFeatValues = values.get(feat);
        for (String currentValue : allCurrentFeatValues)
        {
            //System.out.println("\ncurrentValue: " + currentValue);
            ExampleSet currentIteration = parent.getExamples(feat, currentValue);

            g.addVertex(currentIteration);
            g.addEdge(parent, currentIteration, feat + ": " + currentValue);

            if (feats.size() > 2)
            {
                addFeatToGraph( currentIteration, feats.pop() );
            }

            //currentIteration.printProbabilityInfo();
        }
    }

}
