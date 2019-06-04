import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ScriptAnalzer {
	
	private ArrayList<Scene> scenes;			//	극중의 모든 Scene
	private HashMap<Actor, Double> mapNodeData;	//	Node
	private double[][] mapData;					//	Edge
	

	public ScriptAnalzer() {

	}
	
	public void analyzeScriptFile(String filePath) {
		FileParser fileparser = new FileParser();
		try {
			fileparser.ReadFile(filePath);
			scenes = fileparser.getScenes();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void generateActorMapData() {
		MapDataGenerator mapDataGenerator = new MapDataGenerator();
//		mapData = mapDataGenerator.fillMapDataByWeight1(scenes);
		mapData = mapDataGenerator.fillMapDataByWeightMultiplex(scenes);
		printMapData();
	}
	
	/* 콘솔	*/
	public void printMapData() {
		int n = mapData.length;
		for (int i = 0; i < n; i++) {
			System.out.print(String.format("%02d", i) + "| ");
			for (int j = 0; j < n; j++)
				System.out.print(String.format("%.2f ", mapData[i][j]));
			System.out.println();
		}
	}
	
	/* 파일 */
	public void printMapData(String filePath) {
		
	}
	
		
}
