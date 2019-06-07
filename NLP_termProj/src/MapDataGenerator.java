import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

public class MapDataGenerator {
	public final static int NODE_WEIGHT_1 = 1;
	public final static int NODE_WEIGHT_COUNT = 2;
	public final static int EDGE_WEIGHT_1 = 1;
	public final static int EDGE_WEIGHT_MULTIPLY = 2;
	public final static int EDGE_WEIGHT_MULTIPLY_AND_COUNT = 3;

	private MapData mapData;
	private ArrayList<String> actorNames; // 극중의 모든 배우들의 이름

	public MapDataGenerator(ArrayList<Scene> scenes) {
		this.mapData = new MapData();
		setActorNames(scenes);
	}

	/* set MapData and return */
	public MapData getMapData(int NODE_PARAM, int EDGE_PARAM, ArrayList<Scene> scenes) {
		/* set nodes */
		switch (NODE_PARAM) {
		case NODE_WEIGHT_1:
			mapData.setNodeData(fillNodeDataByWeight1(scenes));
			break;
		case NODE_WEIGHT_COUNT:
			mapData.setNodeData(fillNodeDataByWeightByCount(scenes));
			break;
		}
		/* set edges */
		switch (EDGE_PARAM) {
		case EDGE_WEIGHT_1:
			mapData.setEdgeData(fillEdgeDataByWeight1(scenes));
			break;
		case EDGE_WEIGHT_MULTIPLY:
			mapData.setEdgeData(fillEdgeDataByWeightMultiplex(scenes));
			break;
		case EDGE_WEIGHT_MULTIPLY_AND_COUNT:
			mapData.setEdgeData(fillEdgeDataByWeightMultiplexAndCount(scenes));
			break;
		}

		return this.mapData;
	}

	// 말하면 node 가중치 씬마다 +1
	private TreeMap<String, Double> fillNodeDataByWeight1(ArrayList<Scene> scenes) {
		HashMap<String, Double> tempNodeData = new HashMap<String, Double>();
		for (Scene scene : scenes) {
			ArrayList<Actor> actors = scene.getActors();
			for (Actor actor : actors) {
				if (tempNodeData.containsKey(actor.getName())) {
					double value = tempNodeData.get(actor.getName());
					tempNodeData.put(actor.getName(), value + 1.0);
				} else {
					tempNodeData.put(actor.getName(), 1.0);
				}
			}
		}
		TreeMap<String, Double> mapNodeData = new TreeMap<>(tempNodeData);
		return mapNodeData;
	}

	// 말하면 node 가중치 씬마다 말한 횟수만큼 +
	private TreeMap<String, Double> fillNodeDataByWeightByCount(ArrayList<Scene> scenes) {
		HashMap<String, Double> tempNodeData = new HashMap<String, Double>();
		for (Scene scene : scenes) {
			ArrayList<Actor> actors = scene.getActors();
			for (Actor actor : actors) {
				if (tempNodeData.containsKey(actor.getName())) {
					double value = tempNodeData.get(actor.getName());
					tempNodeData.put(actor.getName(), value + actor.getCount());
				} else {
					tempNodeData.put(actor.getName(), (double) actor.getCount());
				}
			}
		}
		TreeMap<String, Double> mapNodeData = new TreeMap<>(tempNodeData);
		return mapNodeData;
	}

	// 씬마다 등장 인물 weight=1
	// 씬의 등장마다 관계의 정도를 같다고 가정
	private double[][] fillEdgeDataByWeight1(ArrayList<Scene> scenes) {
		double[][] mapEdgeData = new double[actorNames.size()][actorNames.size()];
		// 씬마다 Actor 가져와서 진행
		for (Scene scene : scenes) {
			ArrayList<Actor> actors = scene.getActors();
			ArrayList<Integer> positions = new ArrayList<>();
			for (Actor actor : actors) {
				positions.add(actorNames.indexOf(actor.getName()));
			}
			Collections.sort(positions);
			int actorNum = positions.size();

			if (actorNum >= 2) { // 독백 무시 -> Node에서 영향주면 됨
				// mapData에 추가, 가중치 = 1
				for (int i = 0; i < actorNum; i++) {
					for (int j = i + 1; j < actorNum; j++) {
						if (i != j)
							mapEdgeData[positions.get(i)][positions.get(j)] += 1; // 가중치 = 1
					}
				}
			}
		}
		/*
		 * 결과는 영화명//weightTest 디렉터리에 함수명으로 저장 sort하여 삼각 행렬 형태로 나옴(아래 삼각형 0)
		 */
		return mapEdgeData;
	}

	// 씬마다 등장 인물 weight는 다른 배우의 weight와 곱만큼의 관계
	// 말을 많이한 인물마다 w가 크고 한 씬에 말을 많이하는 두 인물사이의 관계가 더 높다고 가정
	private double[][] fillEdgeDataByWeightMultiplex(ArrayList<Scene> scenes) {
		double[][] mapEdgeData = new double[actorNames.size()][actorNames.size()];
		for (Scene scene : scenes) {
			ArrayList<Actor> actors = scene.getActors();
			ArrayList<Integer> positions = new ArrayList<>();
			for (Actor actor : actors) {
				positions.add(actorNames.indexOf(actor.getName()));
			}
			// index위치 완성
			// mapData에 추가, 가중치 = 1
			Collections.sort(positions);
			int actorNum = positions.size();
			if (actorNum >= 2) {
				for (int i = 0; i < actorNum; i++) {
					for (int j = i + 1; j < actorNum; j++) {
						if (i != j)
							mapEdgeData[positions.get(i)][positions.get(j)] += actors.get(i).getWeight()
									* actors.get(j).getWeight(); // 가중치 = 배우가중치*상대배우가중치
					}
				}
			}
		}
		return mapEdgeData;
	}

	// fillMapDataByWeightMultiplex에서
	// 씬마다 대화 수에 따른 가중치 추가 부여
	// 100번말한 씬과 1번말한 씬에 대해 가중치가 필요하기 때문에
	private double[][] fillEdgeDataByWeightMultiplexAndCount(ArrayList<Scene> scenes) {
		double[][] mapEdgeData = new double[actorNames.size()][actorNames.size()];
		int maxCount = 0;
		for (Scene scene : scenes) {
			if (maxCount < scene.getTotalCount())
				maxCount = scene.getTotalCount();
			ArrayList<Actor> actors = scene.getActors();
			ArrayList<Integer> positions = new ArrayList<>();
			for (Actor actor : actors) {
				positions.add(actorNames.indexOf(actor.getName()));
			}
			// index위치 완성
			// mapData에 추가, 가중치 = 1
			Collections.sort(positions);
			int actorNum = positions.size();
			if (actorNum >= 2) {
				for (int i = 0; i < actorNum; i++) {
					for (int j = i + 1; j < actorNum; j++) {
						if (i != j)
							mapEdgeData[positions.get(i)][positions.get(j)] += actors.get(i).getWeight()
									* actors.get(j).getWeight() * scene.getTotalCount();
						// 가중치 = 배우가중치*상대배우가중치*씬에서 대화횟수
					}
				}
			}
		}
		for (int i = 0; i < mapEdgeData.length; i++) {
			for (int j = i + 1; j < mapEdgeData.length; j++) {
				mapEdgeData[i][j] /= maxCount;
			}
		}

		return mapEdgeData;
	}


	private void setActorNames(ArrayList<Scene> scenes) {
		HashSet<String> hActorNames = new HashSet<>();
		for (Scene scene : scenes) {
			for (Actor actor : scene.getActors()) {
				hActorNames.add(actor.getName());
			}
		}
		actorNames = new ArrayList<>(hActorNames);
		Collections.sort(actorNames); // sort by name
//		System.out.println(actorNames);
	}
	
	public ArrayList<String> getActorNames() {
		return this.actorNames;
	}

}
