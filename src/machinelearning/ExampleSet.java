package machinelearning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


 /**
 * ExampleSet: class for representing set of train or test examples (labelled instances). 
 * Methods provided for reading data from file, extracting feature information, deriving
 * subsets of the data set.
 * @author billk
 * @version Februray 2013
 */

public class ExampleSet {
	
	private String[] features;
	private String[] labels;
	private HashMap<String,HashSet<String>> values;
	private ArrayList<Instance> examples;
	private int size;
	
	
	/**
	 * Construct an initially empty set of examples structured around the given features and labelsa
	 * @param features a set of feature names
	 * @param labels a set of instance labels
	 */
	private ExampleSet(String[] features, String[] labels) {
		this.features = features;
		this.labels = labels;
		examples = new ArrayList<Instance>();
		values = new HashMap<String,HashSet<String>>();
		size = 0;
	}
	
	/**
	 * Build a set of examples from a file. Assumes that examples in file are stored one per line, in 
	 * comma separated format. Also assumes that two lines of information precede the data proper: 
	 * the first must be a list of the feature names (comma-separated format) and the second must be 
	 * a list of the possible labels (comma-separated format). Any blank lines are ignored.
	 * @param file the name of the file to read data from
	 * @return a set of examples.
	 */
	public static ExampleSet getData(String file) {
		FileIO in = new FileIO("r", file);
		String[] features = readCommaSeparatedValues(in);
		String[] labels = readCommaSeparatedValues(in);
		ExampleSet data = new ExampleSet(features, labels);
		String[] example;
		while ((example = readCommaSeparatedValues(in)) != null) {
			data.addValues(example);
			Instance instance = new Instance(features, example);
			data.addExample(instance);

		}
		return data;	
	}
	
	
	private void addValues(String[] example) {
		for( int i=0; i<features.length; i++ ) {
			addValue(features[i], example[i]);
		}
		
	}

	private void addValue(String f, String v) {
		if (values.get(f) == null) {
			values.put(f, new HashSet<String>());
		}
		values.get(f).add(v);		
	}

	private static String[] readCommaSeparatedValues(FileIO in) {
		String line, empty="";
		while (empty.equals(line = in.readLine()));	
		return (line == null) ? null : line.replaceAll(" ", "").split(",");
	}
	
	private void addExample(Instance instance) {
		examples.add(instance);
		size++;
	}
	
	/**
	 * Get all of the examples meeting the condition that feature = value
	 * @param feature the name of the feature
	 * @param value the value the feature must have
	 * @return a subset of the examples
	 */
	public ExampleSet getExamples(String feature, String value) {
		ExampleSet data = new ExampleSet(features, labels);
		for (Instance instance : examples) {
			if (instance.contains(feature, value)) {
				data.addExample(instance);
			}
		}
		return data;
	}
	
	/**
	 * Get all of the examples having the specified label
	 * @param label the label
	 * @return a subset of the examples
	 */
	public ExampleSet getExamples(String label) {
		ExampleSet data = new ExampleSet(features, labels);
		for (Instance instance : examples) {
			if (instance.hasLabel(label)) {
				data.addExample(instance);
			}
		}
		return data;
	}
	
	/**
	 * Get the feature names
	 * @return an array of String objects representing the features, in order.
	 */
	public String[] getFeatures() {
		return features;
	}
	/**
	 * Get the labels
	 * @return an array of String objects representing the labels
	 */
	public String[] getLabels() {
		return labels;
	}
	
	/**
	 * Get the size of the data
	 * @return integer number of examples in data set
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Get the sets of values associated with the different features
	 * @return a map from feature names to sets of values
	 */
	public HashMap<String, HashSet<String>> getValues() {
		return values;
	}
	
    public void printProbabilityInfo()
    {
        for (String label : labels)
        {
            ExampleSet ex = getExamples(label);
            System.out.print(label + " : " + ex.size()+ " : ");
            System.out.println((float) ex.size() / size() * 100 + "%");
        }
    }
}
