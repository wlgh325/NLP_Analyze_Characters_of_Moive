
public class Actor {
	
	private String name;			//	name of actor
	private int count;				//	number of actor's script
	private float weight;			//	score of actor
	
	/*	Constructor	*/
	
	Actor(){
		this.name = null;
		this.count = 0;
		this.weight = 0;
	}
	
	public Actor(String name) {
		this.name = name;
		this.count = 0;
		this.weight = 0;
	}
	public Actor(String name, int count) {
		this.name = name;
		this.count = count;
		this.weight = 0;
	}
	/*	Getter and Setter	*/
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	public void addCount() {
		this.count++;
	}
	
	public void computeWeight(int total) {
		this.weight = (float)this.count / total;
	}
	
	@Override
	public String toString() {
		return "Actor " + this.name + " speaks " + count + "times...\n weight : " + this.weight; 
	}
}