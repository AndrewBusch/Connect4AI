
public class Eval {
	
	
	 public static int eval( Board board) {
		int sum = (int)(Math.random() * 10); //random seed so it doesn't always go for left column when it can't see any advantage
		int[] advantages;
		double denominator = board.N * board.N * board.N * board.N * board.N;
		for(int i = board.N  - 1; i >= 2; i--) {
			double numerator = i * i *  i * i * i;
			int multiplier =(int) (numerator/denominator * 1000) + 1;
			advantages = board.numOfConnectN(i);
			//System.out.println("Current Sum: " + sum + " Multiplier: " + multiplier + " Advantage Scores: " + advantages[0] + ", -" + advantages[1] + ", "+ advantages[2] + ", -" + advantages[3]);
			sum = sum + multiplier*advantages[0] - multiplier*advantages[1] + multiplier*advantages[2] - multiplier*advantages[3];
		}
		
		int score = board.isConnectN();
		if( score == 1 ) {			// -1 no winner, 0 tie, 1 win, 2 loss
			sum = 1000000;
		} else if( score == 2) {
			sum = -1000000;
		}
		return sum;
	 }
	 
}
