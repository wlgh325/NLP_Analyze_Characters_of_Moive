import java.io.File;

// NLP_Analyze_Characters_of_Moive

public class Main {
	public static String fileName = "";
	public static String inputSource = "./Script/input/";
	public static String outputSource = "./Script/output/";
	
	public static void main(String[] args) {
		// 스크립트 분석을 위한 ScriptAnalzer 생성
		ScriptAnalzer scriptAnalzer = new ScriptAnalzer();
		File dirFile = new File(inputSource);
		File[] inputFiles = dirFile.listFiles();
		try {
			for (File inputFile : inputFiles) {
				System.out.println(inputFile.getPath());
				// 1. 영화 스크립트를 읽어서 분석. ScriptAnalzer 내 scenes을 채우기
				scriptAnalzer.analyzeScriptFile(inputSource + inputFile.getName());
				
				// 2. Python 그림 그려주는 프로그램에 들어갈 input형식에 맞추어 data write
				scriptAnalzer.generateActorMapData(outputSource + inputFile.getName().substring(0, inputFile.getName().length()-4)
						, MapDataGenerator.NODE_WEIGHT_COUNT, MapDataGenerator.EDGE_WEIGHT_MULTIPLY);
			}

		} catch(Exception e) {
			e.printStackTrace();
		}
//		/* test for single file */
//		scriptAnalzer.analyzeScriptFile(inputSource + fileName);
//		scriptAnalzer.generateActorMapData(outputSource + fileName.substring(0, fileName.length()-4)
//				, MapDataGenerator.NODE_WEIGHT_COUNT, MapDataGenerator.EDGE_WEIGHT_MULTIPLY_AND_COUNT);

	}
	
	
}
