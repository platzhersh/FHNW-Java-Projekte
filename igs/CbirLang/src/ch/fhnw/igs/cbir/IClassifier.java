package ch.fhnw.igs.cbir;
import java.util.Map;
import java.util.Vector;


public interface IClassifier {
	
	public String classify(int[] histogram);
	public void learn(Map<String,Vector<int[]>> dataSet);
}
