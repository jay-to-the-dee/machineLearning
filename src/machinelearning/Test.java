package machinelearning;

import java.util.*;
import org.jgrapht.graph.*;

public class Test
{
    private final static int MAX_DEPTH = 2;

    private final String file = "./DataSets/CarEvaluation/car.data.txt";
    private Stack<String> feats;
    private Stack<String> labels;
    private HashMap<String, LinkedHashSet<String>> values;

    private SimpleGraph<ExampleSet, UniqueStringEdge> g;

    public Test()
    {
        feats = new Stack();
        labels = new Stack();

        g = new SimpleGraph<>(UniqueStringEdge.class);
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

        LinkedHashSet<ExampleSet> exampleSet = new LinkedHashSet<>();
        addFeatToGraph(examples, feats);

        //Visualise what we've added so far
        JGraphFrame jGraphFrame = new JGraphFrame(g);

        //System.out.println(g.toString());
    }

    private Set<ExampleSet> doSplitAndReturnNewParents(ExampleSet parent, String feat)
    {
        Set<ExampleSet> parentSet = new LinkedHashSet<>();
        Set<String> allCurrentFeatValues = values.get(feat);

        for (String currentFeatValue : allCurrentFeatValues)
        {
            ExampleSet currentIteration = parent.getExamples(feat, currentFeatValue); //Split from parent

            parentSet.add(currentIteration);

            g.addVertex(currentIteration);
            //System.out.println("currentId:" + currentIteration.getId() + "\tparentId:" + parent.getId());
            //System.out.println(feat + ": " + currentFeatValue);
            UniqueStringEdge edgeString = new UniqueStringEdge(feat + ": " + currentFeatValue);

            g.addEdge(currentIteration, parent, edgeString);

        }
        return parentSet;
    }

    /*private void addFeatToGraph(Set<ExampleSet> parents, Iterator<Set<ExampleSet>> i, Stack<String> featStack)
     {

     }*/
    private void addFeatToGraph(ExampleSet parent, Stack<String> featStack)
    {
        if (featStack.empty() || featStack.size() <= feats.size() - MAX_DEPTH)
        {
            return; //End this recursion NOW!!
        }

        Set<ExampleSet> parents = doSplitAndReturnNewParents(parent, featStack.pop());

        for (ExampleSet nextParent : parents)
        {
            addFeatToGraph(nextParent, (Stack<String>) featStack.clone()); //Split from featStack
        }
    }

}
