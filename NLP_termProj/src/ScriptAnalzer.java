import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

public class ScriptAnalzer {
	private final static double NODE_THRESHOLD = 3;
	private final static double EDGE_THRESHOLD = 3;

	private ArrayList<Scene> scenes; // 극중의 모든 Scene

	public ScriptAnalzer() {

	}

	public void analyzeScriptFile(String inputFilePath) {
		FileParser fileparser = new FileParser();
		try {
			fileparser.ReadFile(inputFilePath);
			scenes = fileparser.getScenes();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void generateActorMapData(String outputFilePath) {
		MapDataGenerator mapDataGenerator = new MapDataGenerator(scenes);
		MapData mapData = mapDataGenerator.getMapData(MapDataGenerator.NODE_WEIGHT_1, MapDataGenerator.EDGE_WEIGHT_1, scenes);
		printData(mapData, outputFilePath);
	}


	private void printData(MapData mapData, String fileName) {
		TreeMap<String, Double> nodes = mapData.getNodeData();
		double[][] edges = mapData.getEdgeData();
		ArrayList<String> actorNames = mapData.getNames();
		int n = edges.length;
		
		try {
			FileWriter fw = new FileWriter(fileName);
			BufferedWriter bw = new BufferedWriter(fw);

			/* node */
			bw.write("NODE");
			bw.newLine();
			ArrayList<String> names = new ArrayList<>(nodes.keySet());
			for (int i = 0; i < names.size(); i++) {
				if (nodes.get(names.get(i)) > NODE_THRESHOLD) {
					bw.write(names.get(i) + "-" + nodes.get(names.get(i)));
					bw.newLine();					
				}
			}
			
			/* edge */
			bw.write("EDGE");
			bw.newLine();
			for (int i = 0; i < n; i++) {
				for (int j = i + 1; j < n; j++) {
					if (edges[i][j] > EDGE_THRESHOLD) {
						bw.write(actorNames.get(i) + "-" + actorNames.get(j) + "\t" + edges[i][j]);
						bw.newLine();
					}
				}
			}
			bw.close();
			fw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}

	/* 콘솔에 Node와 Edge정보 출력 */
	public void printMapData(MapData mapData) {
		double[][] edges = mapData.getEdgeData();
		int n = edges.length;
		System.out.println(mapData.getNodeData());
		for (int i = 0; i < n; i++) {
			System.out.print(String.format("%02d", i) + "| ");
			for (int j = 0; j < n; j++)
				System.out.print(String.format("%.2f ", edges[i][j]));
			System.out.println();
		}
	}

	
	/* 파일 */
	public void printMapData(MapData mapData, String fileName) {
		double[][] edges = mapData.getEdgeData();
		File file = new File(fileName + ".txt");
		FileWriter filewriter;
		try {
			filewriter = new FileWriter(file);
			BufferedWriter bufWriter = new BufferedWriter(filewriter);

			/* write edges */
			int n = edges.length;
			for (int i = 0; i < n; i++) {
				bufWriter.write(String.format("%02d", i) + "| ");
				for (int j = 0; j < n; j++)
					bufWriter.write(String.format("%.2f ", edges[i][j]));
				bufWriter.write("\n");
				bufWriter.flush();
			}
			bufWriter.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
