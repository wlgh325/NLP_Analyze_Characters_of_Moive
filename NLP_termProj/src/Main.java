import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException{
		String scriptFileName = "./Script/Deadpool.txt";
		FileParser fileparser = new FileParser();
		fileparser.ReadFile(scriptFileName);
	}

}
