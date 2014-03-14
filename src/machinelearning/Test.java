package machinelearning;

import java.util.*;
import org.jgraph.JGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.*;

public class Test
{

    private final String file = "./DataSets/CarEvaluation/car.data.txt";
    private Stack<String> feats;
    private Stack<String> labels;

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

        HashMap<String, HashSet<String>> values = examples.getValues();
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
        
        
        ExampleSet currentParent = examples;
        
        g.addVertex(currentParent);
        String currentFeat = feats.pop();
        
        HashSet<String> allCurrentFeatValues = values.get(currentFeat);
        for (String currentValue : allCurrentFeatValues)
        {
            //System.out.println("\ncurrentValue: " + currentValue);
            //g.
            ExampleSet currentIteration = examples.getExamples(currentFeat, currentValue);
            
            g.addVertex(currentIteration);
            g.addEdge(currentParent, currentIteration, currentValue);
            
            //currentIteration.printProbabilityInfo();
        }
        
        JGraphFrame jGraphFrame = new JGraphFrame(g);
    }
}
