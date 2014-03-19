package machinelearning;

import java.util.*;
import org.jgrapht.graph.*;

public class Test
{

    private final String file = "./DataSets/CarEvaluation/car.data.txt";
    private Stack<String> feats;
    private Stack<String> labels;
    private HashMap<String, LinkedHashSet<String>> values;

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

        //LinkedHashSet<ExampleSet> exampleSet = new LinkedHashSet<>();
        addFeatToGraph(examples);

        doSplitAndReturnNewParents(examples, "maint");

        //Visualise what we've added so far
        JGraphFrame jGraphFrame = new JGraphFrame(g);
    }

    private Set<ExampleSet> doSplitAndReturnNewParents(ExampleSet parent, String feat)
    {
        Set<ExampleSet> parentSet = new LinkedHashSet<>();
        Set<String> allCurrentFeatValues = values.get(feat);

        for (String currentFeatValue : allCurrentFeatValues)
        {
            ExampleSet currentIteration = parent.getExamples(feat, currentFeatValue);

            parentSet.add(currentIteration);

            g.addVertex(currentIteration);
            g.addEdge(parent, currentIteration, feat + ": " + currentFeatValue);

        }
        return parentSet;
    }

    /*private void addFeatToGraph(Set<ExampleSet> parents, Iterator<Set<ExampleSet>> i, Stack<String> featStack)
     {

     }*/
    private void addFeatToGraph(ExampleSet parent)
    {
        if (feats.size() < 4)
        {
            return; //End this recursion NOW!!
        }

        Set<ExampleSet> parents = doSplitAndReturnNewParents(parent, feats.pop());
        Iterator i = parents.iterator();
        for (ExampleSet nextParent : parents)
        {
            addFeatToGraph(nextParent);
        }

        //feats.pop();
    }

}
