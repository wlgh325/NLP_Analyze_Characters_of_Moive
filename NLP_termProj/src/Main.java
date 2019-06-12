// NLP_Analyze_Characters_of_Moive

public class Main {
	public static String inputSource = "./Script/input/Wall-E.txt";
	public static String outputSource = "./Script/output/Wall-E.txt";
	
	public static void main(String[] args) {
		// 스크립트 분석을 위한 ScriptAnalzer 생성
		ScriptAnalzer scriptAnalzer = new ScriptAnalzer();
		// 임계값 설정, or default
		//	scriptAnalzer.setThresholds(1, 1);
		// ScriptAnalzer 구현해야 할 것
		// 1. 영화 스크립트를 읽어서 분석. ScriptAnalzer 내 scenes을 채우기
		scriptAnalzer.analyzeScriptFile(inputSource);
		// 2. Python 그림 그려주는 프로그램에 들어갈 input형식에 맞추어 data write
		scriptAnalzer.generateActorMapData(outputSource, MapDataGenerator.NODE_WEIGHT_COUNT, MapDataGenerator.EDGE_WEIGHT_MULTIPLY_AND_COUNT);
	}
	
	
}
