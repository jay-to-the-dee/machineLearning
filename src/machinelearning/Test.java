package machinelearning;

import java.util.HashMap;
import java.util.HashSet;


public class Test {

	private final String file = "./DataSets/CarEvaluation/car.data.txt";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Test t = new Test();
		t.run();

	}
	
	public Test() {

	}
	
	public void run() {		
		ExampleSet examples = ExampleSet.getData(file);


		String[] feats = examples.getFeatures();
		//for (int i=0; i<feats.length; i++) System.out.println(feats[i]);
		String[] labels = examples.getLabels();
		//for (int i=0; i<labels.length; i++ ) System.out.println(labels[i]);
		HashMap<String,HashSet<String>> values = examples.getValues();
		for (String f : feats) {
			System.out.print(f + " : ");
			HashSet<String> vals = values.get(f);
			for (String v : vals) {
				System.out.print(v + ", ");
			}
			System.out.println();
		}
		System.out.println(examples.size());
		
		
		ExampleSet ex1 = examples.getExamples(labels[0]);
		/*
		String[] feats1 = ex1.getFeatures();
		for (int i=0; i<feats.length; i++) System.out.println(feats[i]);
		String[] labels1 = ex1.getLabels();
		for (int i=0; i<labels.length; i++ ) System.out.println(labels[i]);
		HashMap<String,HashSet<String>> values1 = examples.getValues();
		for (String f : feats) {
			System.out.print(f + " : ");
			HashSet<String> vals = values1.get(f);
			for (String v : vals) {
				System.out.print(v + ", ");
			}
			System.out.println();
		}
		*/
		System.out.println(labels[0] + " : " + ex1.size());
		
	
		ExampleSet ex2 = examples.getExamples(labels[1]);
		/*
		String[] feats2 = ex2.getFeatures();
		for (int i=0; i<feats.length; i++) System.out.println(feats[i]);
		String[] labels2 = ex2.getLabels();
		for (int i=0; i<labels.length; i++ ) System.out.println(labels[i]);
		HashMap<String,HashSet<String>> values2 = examples.getValues();
		for (String f : feats) {
			System.out.print(f + " : ");
			HashSet<String> vals = values2.get(f);
			for (String v : vals) {
				System.out.print(v + ", ");
			}
			System.out.println();
		}
		*/
		System.out.println(labels[1] + " : " + ex2.size());
		
		//System.out.println(train.getSplit(feats[3]));

	}

}
