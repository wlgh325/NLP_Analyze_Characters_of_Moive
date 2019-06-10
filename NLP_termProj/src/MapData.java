import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class MapData {
	private TreeMap<String, Double> nodeData;	//	Node 정보: Actor 이름, 비중
	private double[][] edgeData;					//	Edge 정보: [i][j] = 관계의 정도
	
	public MapData() {}
	
	public MapData(TreeMap<String, Double> nodeData, double[][] edgeData) {
		this.nodeData = nodeData;
		this.edgeData = edgeData;
	}
	
	public TreeMap<String, Double> getNodeData() {
		return nodeData;
	}
	
	public void setNodeData(TreeMap<String, Double> nodeData) {
		this.nodeData = nodeData;
	}
	
	public double[][] getEdgeData() {
		return edgeData;
	}
	
	public void setEdgeData(double[][] edgeData) {
		this.edgeData = edgeData;
	}
	
	public double getNodeAverage() {
		List<Double> values = new ArrayList<>(nodeData.values());
		double sum = 0;
		for (int i = 0; i < values.size(); i++)
			sum += values.get(i);
		return sum / values.size();
	}
	
	public double getEdgeAverage() {
		int n = edgeData.length;
		double sum = 0;
		int count = 0;
		for (int i = 0; i < n; i++) 
			for (int j = i+1; j < n; j++) 
				if (edgeData[i][j] != 0) {
					sum += edgeData[i][j];
					count++;
				}
		return sum / count;
	}
	
	public double getEdgeAverageWith0() {
		int n = edgeData.length;
		double sum = 0;
		for (int i = 0; i < n; i++) 
			for (int j = i+1; j < n; j++) 
				sum += edgeData[i][j];
		return sum / ((n-1)*(n-2) / 2);
	}
	
	public double getNodeStandardDeviation() {
		List<Double> values = new ArrayList<>(nodeData.values());
		double mean = getNodeAverage();
		double sum = 0;
		for (int i = 0; i < values.size(); i++)
			sum += Math.pow(values.get(i) - mean, 2);
		return Math.sqrt(sum / values.size());
	}

	public double getEdgeStandardDeviation() {
		int n = edgeData.length;
		double sum = 0;
		int count = 0;
		for (int i = 0; i < n; i++) 
			for (int j = i+1; j < n; j++) 
				if (edgeData[i][j] != 0) {
					sum += Math.pow(edgeData[i][j], 2);
					count++;
				}
		return Math.sqrt(sum / count);
	}

	
	public ArrayList<String> getNames() {
		ArrayList<String> names = new ArrayList<>(nodeData.keySet());
		return names;
	}
}
