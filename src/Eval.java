/**
 * Evaluation Static Object
 * 
 * Provides an evalution function that returns the value of the heuristic
 * utility
 * 
 * @author fmsanchez @author abbusch
 * 
 */
public class Eval {

	/**
	 * Evaluates the strength of a given board
	 * 
	 * @param board
	 *            the current board to be analyzed
	 * @return the value of the heuristic
	 */
	public static int eval(Board board) {
		int sum = 0;
		int[] advantages; // array for the vector returned by numOfConnectN
		// used to weight n in a row
		double denominator = board.N * board.N * board.N * board.N * board.N;
		for (int i = board.N - 1; i >= 2; i--) {
			double numerator = i * i * i * i * i; // used to weight n in a row
			// numnerator/denominator is equivalent to (i/N)^5 but takes less
			// time
			int multiplier = (int) (numerator / denominator * 1000) + 1;
			advantages = board.numOfConnectN(i);
			// calculates the sum of the total advantageous positions
			sum = sum + multiplier * advantages[0] - multiplier * advantages[1]
					+ multiplier * advantages[2] - multiplier * advantages[3];
		}

		int score = board.isConnectN();
		if (score == 1) { // -1 no winner, 0 tie, 1 win, 2 loss
			sum = 1000000;
		} else if (score == 2) {
			sum = -1000000;
		}
		return sum;
	}

}
