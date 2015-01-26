
public class Move {
	int column;
	int type;
	
	
	/**
	 * Constructor of Move using integers
	 * @param column Column of the move
	 * @param type The type of move 0=pop, 1=drop
	 */
	public Move(int column, int type) {
		this.column = column;
		this.type = type;
	}
	
	/**
	 * Constructor of Move using Strings
	 * @param column Column of the move
	 * @param type The type of move 0=pop, 1=drop
	 */
	public Move(String column, String type) {
		this.column = Integer.parseInt(column);
		this.type = Integer.parseInt(type);
	}
	
	public String toString() {
		return column + " " + type;
	}
}
