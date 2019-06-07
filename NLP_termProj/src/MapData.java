import java.util.ArrayList;
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
	
	public ArrayList<String> getNames() {
		ArrayList<String> names = new ArrayList<>(nodeData.keySet());
		return names;
	}
}
