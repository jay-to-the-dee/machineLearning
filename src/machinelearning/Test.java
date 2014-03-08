package machinelearning;

import java.util.HashMap;
import java.util.HashSet;

public class Test
{

    private final String file = "./DataSets/CarEvaluation/car.data.txt";

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

        String[] feats = examples.getFeatures();

        String[] labels = examples.getLabels();

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

        for (String label : labels)
        {
            ExampleSet ex = examples.getExamples(label);
            System.out.println(label + " : " + ex.size());
        }
    }
}
