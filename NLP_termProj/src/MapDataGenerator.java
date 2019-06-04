import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class MapDataGenerator {
	
	private ArrayList<String> actorNames;	//	극중의 모든 배우들의 이름
	private HashMap<Actor, Double> mapNodeData;
	private double[][] mapData;
	
	// 씬마다 등장 인물 weight=1
	// 씬의 등장마다 관계의 정도를 같다고 가정
	public double[][] fillMapDataByWeight1(ArrayList<Scene> scenes) {
		initComponents(scenes);
		// 씬마다 Actor 가져와서 진행
		int count = 1;
		for (Scene scene : scenes) {
			ArrayList<Actor> actors = scene.getActors();
			ArrayList<Integer> positions = new ArrayList<>();
			for (Actor actor : actors) {
				positions.add(actorNames.indexOf(actor.getName()));
			}
			Collections.sort(positions);
//			System.out.println(positions);
			int actorNum = positions.size();
			
			if (actorNum >= 2) {	//	독백 무시 -> Node에서 영향주면 됨
//				System.out.println("# " + count + " Actor " + actorNum + "명");
				// mapData에 추가, 가중치 = 1
				for (int i = 0; i < actorNum; i++) {
					for (int j = i + 1; j < actorNum; j++) {
						if (i != j)
							mapData[positions.get(i)][positions.get(j)] += 1; //	가중치 = 1
					}
				}
			}
			count++;
		}
		/*	결과는 영화명//weightTest 디렉터리에 함수명으로 저장
		 *  sort하여 삼각 행렬 형태로 나옴(아래 삼각형 0)
		 * */
		return mapData;
	}

	// 씬마다 등장 인물 weight는 다른 배우의 weight와 곱만큼의 관계
	// 말을 많이한 인물마다 w가 크고 한 씬에 말을 많이하는 두 인물사이의 관계가 더 높다고 가정
	public double[][] fillMapDataByWeightMultiplex(ArrayList<Scene> scenes) {
		initComponents(scenes);
		int count = 1;
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
							mapData[positions.get(i)][positions.get(j)] += actors.get(i).getWeight() * actors.get(j).getWeight(); // 가중치 = 배우가중치*상대배우가중치
					}
				}
			}
			count++;
		}
		
		return mapData;
	}

	private void initComponents(ArrayList<Scene> scenes) {
		getActorNames(scenes);
		initMapData(actorNames.size());
	}
	
	private void getActorNames(ArrayList<Scene> scenes) {
		HashSet<String> hActorNames = new HashSet<>();
		for (Scene scene : scenes) {
			for (Actor actor : scene.getActors()) {
				hActorNames.add(actor.getName());
			}
		}
		actorNames = new ArrayList<>(hActorNames);
		Collections.sort(actorNames); // sort by name
		System.out.println(actorNames);
		
	}
	private void initMapData(int n) {
		this.mapData = new double[n][n];
	}

}
