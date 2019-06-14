import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;

public class ScriptAnalzer {
	
	private double edge_threshold;
	private ArrayList<Scene> scenes; // 극중의 모든 Scene

	public ScriptAnalzer() {
	}

	/* 임계점을 사용자가 지정할 수 있도록 한다. */
	public void setThresholds(double edge_threshold) {
		this.edge_threshold = edge_threshold;
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

	public void generateActorMapData(String outputFilePath, int node_mode, int edge_mode) {
		MapDataGenerator mapDataGenerator = new MapDataGenerator(scenes);
		MapData mapData = mapDataGenerator.getMapData(node_mode, edge_mode, scenes);

		System.out.println("노드 평균값 : " + mapData.getNodeAverage() + " 엣지 평균값 : " + mapData.getEdgeAverage());
		System.out.println(
				"노드 표준편차: " + mapData.getNodeStandardDeviation() + " 엣지 표준편차: " + mapData.getEdgeStandardDeviation());
		edge_threshold = mapData.getEdgeAverage();
//		edge_threshold = mapData.getEdgeAverage() + mapData.getEdgeStandardDeviation()/2;
		System.out.println(mapData.getNodeData().size());
		printData(mapData, outputFilePath);
	}

	/* Networkx에 이용할 수 있는 형식으로 data formatting 후 outputFile 생성 */
	private void printData(MapData mapData, String fileName) {
		TreeMap<String, Double> nodes = mapData.getNodeData();
		double[][] edges = mapData.getEdgeData();
		ArrayList<String> actorNames = mapData.getNames();
		int n = edges.length;

		try {
			FileWriter fw = new FileWriter(fileName + mapData.getModeStr() + ".txt");
			BufferedWriter bw = new BufferedWriter(fw);
			HashSet<String> names = new HashSet<String>();

			/* edge */
			bw.write("EDGE");
			bw.newLine();
			for (int i = 0; i < n; i++) {
				for (int j = i + 1; j < n; j++) {
					if (edges[i][j] > edge_threshold) {
						if (nodes.get(actorNames.get(i)) == 1 | nodes.get(actorNames.get(j)) == 1)
							continue;
						bw.write(actorNames.get(i) + "-" + actorNames.get(j) + "\t" + edges[i][j]);
						bw.newLine();
						names.add(actorNames.get(i));
						names.add(actorNames.get(j));
					}
				}
			}

			/* node */
			bw.write("NODE");
			bw.newLine();
			ArrayList<String> Nodenames = new ArrayList<>(names);
			for (int i = 0; i < Nodenames.size(); i++) {
				bw.write(Nodenames.get(i) + "\t" + nodes.get(Nodenames.get(i)));
				bw.newLine();
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
