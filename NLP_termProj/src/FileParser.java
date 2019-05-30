import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileParser {

	// Constructor
	FileParser(){
		
	}
	
	// 같은 actor가 들어가 있는지 check
	// 중복이 있다면 중복되는 index return
	// 중복이 없다면 -1 반환
	int isReduplication(ArrayList<Actor> actors, String actorName) {
		for(int i=0; i<actors.size(); i++) {
			if(actors.get(i).getName().equals(actorName))
				return i;
		}
		return -1;
		
	}
	
	void ReadFile(String fileName) throws IOException{
		File file = new File(fileName);
		        
        // Declare Input stream
        FileReader filereader = new FileReader(file);

        // Declare read buffer
        BufferedReader bufReader = new BufferedReader(filereader);
        
        String line = "";
        
        ArrayList<Scene> scenes = new ArrayList<Scene>();
        ArrayList<String> indicator = new ArrayList<String>();
        indicator.add("CUT TO");
        indicator.add("CONTINUED");
        
        ArrayList<Actor> actors = new ArrayList<Actor>();
        int totalCount = 0;
        
        Scene scene;
        int scene_flag = 0;
        
        while((line = bufReader.readLine()) != null){
        	
        	// Scene이 바뀜
        	if (line.contains("INT.")) {
        		scene = new Scene(actors, totalCount);
        		scenes.add(scene);
        		
        		actors = new ArrayList<Actor>();
        		totalCount = 0;
        		scene_flag = -1;
        	}
        	else if (line.contains("EXT.")) {
        		scene = new Scene(actors, totalCount);
        		scenes.add(scene);
        		
        		actors = new ArrayList<Actor>();
        		totalCount = 0;
        		scene_flag = -1;
        	}
        	else if (line.equals("FINISH")) {
        		scene = new Scene(actors, totalCount);
        		scenes.add(scene);
        		
        		actors = new ArrayList<Actor>();
        		totalCount = 0;
        		scene_flag = -1;
        	}
        	
        	int literal_length = 0;
        	int space_count = 0;
        	
        	if (scene_flag == 0) {
        		for(int i=0; i<line.length(); i++) {
            		if( (line.charAt(i) >= 'A' && line.charAt(i) <= 'Z')) {
            			literal_length ++;
            		}
            		else if (line.charAt(i) == ' ')
            			space_count ++;	
            	}
        	}
        	
        	int line_length = literal_length + space_count;
        	
        	Actor actor = new Actor();
        	
        	if(line_length == line.length() && literal_length != 0) {
        		int indicator_count = 0;
        		for(int i=0; i<indicator.size(); i++) {
        			if(line.contains(indicator.get(i)))
        				indicator_count ++;
        		}
        		// 지시자에 해당되지 않으면 등장인물로 count
        		if(indicator_count == 0) {
        			String trimed_line = line.trim();
        			int reduplication = isReduplication(actors, trimed_line);
        			if(reduplication == -1) {
        				actor.setName(trimed_line);
        				int temp_count = actor.getCount();
        				actor.setCount(++temp_count);
        				actors.add(actor);
        			}
        			else {
        				int temp_count = actors.get(reduplication).getCount();
        				actors.get(reduplication).setCount(++temp_count);
        			}
        			totalCount++;
        		}
        	}
        	scene_flag = 0;
        }	//end of reading file
        
        // print
        for(int i = 1; i<scenes.size(); i++) {
        	System.out.println("Scene " + i);
        	System.out.println("Total Count: " + scenes.get(i).getTotalCount());
        	System.out.println("Actor: " + scenes.get(i).getActors().toString());
        }
        
        bufReader.close();
        
	}
	
}
