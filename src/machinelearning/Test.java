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

        addFeatToGraph(examples, feats, featToNewValueStack(feats.peek()));

        //Visualise what we've added so far
        JGraphFrame jGraphFrame = new JGraphFrame(g);
    }

    private Stack<String> featToNewValueStack(String feat)
    {
        Stack<String> valueStack = new Stack<>();

        LinkedHashSet<String> allCurrentFeatValues = values.get(feat);

        for (String currentFeatValue : allCurrentFeatValues)
        {
            valueStack.push(currentFeatValue);
        }

        return valueStack;
    }

    private void addFeatToGraph(ExampleSet parent, Stack<String> featStack, Stack<String> valueStack)
    {
        if (valueStack.isEmpty())
        {
            //Advance to next Feature
            String newFeat = null;
            try
            {
                newFeat = featStack.pop();
            }
            catch (EmptyStackException e)
            {
                return;
            }
            valueStack = featToNewValueStack(newFeat);
            addFeatToGraph(parent, featStack, valueStack);
            return;
        }

        if (featStack.size() < 6)
        {
            //End of recursion
            return;
        }

        String newValue = valueStack.pop();

        ExampleSet currentIteration = parent.getExamples(featStack.peek(), newValue);

        g.addVertex(currentIteration);
        g.addEdge(parent, currentIteration, featStack.peek() + ": " + newValue);

        addFeatToGraph(parent, featStack, valueStack);

    }

}
