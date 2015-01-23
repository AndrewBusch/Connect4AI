
public class Eval {
	
	
	 public static int eval( Board board) {
		int sum = 50;
		int score = board.isConnectN();
		if( score == 1 ) {			// -1 no winner, 0 tie, 1 win, 2 loss
			sum = 1000;
		} else if( score == 2) {
			sum = -1000;
		} else if( score == -1) {
	 	}
		return sum;
	 }
	 
}
