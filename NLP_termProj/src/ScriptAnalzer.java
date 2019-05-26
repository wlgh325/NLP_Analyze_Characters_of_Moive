import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class ScriptAnalzer {
	private ArrayList<Scene> scenes;
	
	public ScriptAnalzer() {
	}

	
	public void analyzeScriptFile(String filePath) {
		File file = new File(filePath);
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
/*///////////////////////////////////////////////////////////////////
	filePath에서 영화 스크립트를 읽어서 배열 scenes를 완성시키면 됨.
			
			
			
///////////////////////////////////////////////////////////////////*/
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void generateActorMapData() {
/*///////////////////////////////////////////////////////////////////
	network 그리는 방식 분석이 완료되면
	scenes에 있는 Actor정보들을 통해  Python에 들어갈 input form을 따르는 output파일 생성.
			
				
				
///////////////////////////////////////////////////////////////////*/
	}

	public ArrayList<Scene> getScenes() {
		return scenes;
	}


}
