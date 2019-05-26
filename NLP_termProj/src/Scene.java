import java.util.ArrayList;

public class Scene {
	private ArrayList<Actor> actors;	//	Scene에서의 Actor들
	private int totalCount;				//	Scene에서 대사 갯수
	
	public Scene() {
		// analyze scene and get actor
	}
	
	/* total count 로 actor count 를 나눠서 scene에서 가중치 계산 */
	public void computeWeight() {
		for (Actor actor : actors) {
			actor.computeWeight(totalCount);
		}
	}
}
