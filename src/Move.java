
public class Move {
	int column;
	int type;
	
	
	public Move(int column, int type) {
		this.column = column;
		this.type = type;
	}
	
	public Move(String column, String type) {
		this.column = Integer.parseInt(column);
		this.type = Integer.parseInt(type);
	}
	
	public String toString() {
		return column + " " + type;
	}
}
