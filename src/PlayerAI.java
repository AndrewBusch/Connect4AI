import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


// java -jar Referee.jar "java -jar C:\Users\Crozic\Documents\work\CS\AI\testPlayer.jar 1" "java -jar C:\Users\Crozic\Documents\work\CS\AI\testPlayer.jar 0" 6 7 3 10 10



public class PlayerAI {
	String playerName;
	BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	Board board;
	boolean isFirstPlayer;
	final int MAX_DEPTH = 13;
	static Log log;
	
	public boolean processInput() throws IOException{
		boolean gameOver = false;
		log.writeLog("turn starts");
    	String s=input.readLine();
    	log.writeLog("read input");
		List<String> ls = Arrays.asList(s.split(" ", 5));
		log.writeLog("parsed input");
		if(ls.size()==2){ 								//if opponent just played
			updateBoard(ls.get(0), ls.get(1), 2);
			int moveColumn = alphaBetaSearch(board);
			updateBoard(Integer.toString(moveColumn), "1", 1);
			log.writeLog(moveColumn + " 1");
			System.out.println(moveColumn + " 1");
			//updateBoard("3", "1", 1);
		}
		else if(ls.size()==1){							//if game over?
			log.writeLog("game over");
			return true;
			//System.out.println("4 1");
		}
		else if(ls.size()==5){          				//ls contains game info
			board = new Board(Integer.parseInt(ls.get(0)), Integer.parseInt(ls.get(1)), Integer.parseInt(ls.get(2)), log);
			if(isFirstPlayer) {							//Make first move
				System.out.println("3 1");				// TODO: WORRY ABOUT BOARD SIZE
				updateBoard("3", "1", 1);
			}
		}
		else if(ls.size()==4){							//player1: aa player2: bb
			if(ls.indexOf(playerName) == 1) {
				isFirstPlayer = true;
				log.writeLog("first");
			} else {
				log.writeLog("second");
				isFirstPlayer = false;
			}
		}
		else {
			log.writeLog("not what I want");
			System.out.println("not what I want");
		}
		return false;
	}
	
	int alphaBetaSearch(Board currentBoard) {
		log.writeLog("EVALUATING MOVES---------------------------");
		int v = Integer.MIN_VALUE;
		int index = 4;
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		int score;
		for(int i = 0; i < board.width; i++) {
			if( board.canDropADiscFromTop(i, 1)){
				Board nextMove = new Board(currentBoard);
				nextMove.dropADiscFromTop(i, 1);
				if( currentBoard.isConnectN() != -1 || currentBoard.isFull()) {
					score = Eval.eval(currentBoard);
				}
				else score = min(nextMove, 1, alpha, beta);
				if( v < score) {
					v = score;
					index = i;
				}
				if( v >= alpha) alpha = v;
			}
		}
		return index;
	}
	
	int max( Board currentBoard, int currentDepth, int alpha, int beta) {
		if( currentDepth == MAX_DEPTH || currentBoard.isConnectN() != -1 || currentBoard.isFull()) {
			return Eval.eval(currentBoard) * (1 - (currentDepth / MAX_DEPTH));
		}
		
		int v = Integer.MIN_VALUE;
		for(int i = 0; i < board.width; i++) {
			if( currentBoard.canDropADiscFromTop(i, 1)){
				Board nextMove = new Board(currentBoard);
				nextMove.dropADiscFromTop(i, 1);
				int score = min(nextMove, currentDepth+1, alpha, beta);
				if( v < score) v = score;
				if( v >= beta) return v;
				if( v >= alpha) alpha = v;
			}
		}
		return v;
	}

	int min( Board currentBoard, int currentDepth, int alpha, int beta) {
		if( currentDepth == MAX_DEPTH || currentBoard.isConnectN() != -1 || currentBoard.isFull()) {
			return Eval.eval(currentBoard) * (1 - (currentDepth / MAX_DEPTH));
		}
		
		int v = Integer.MAX_VALUE;
		for(int i = 0; i < board.width; i++) {
			if( currentBoard.canDropADiscFromTop(i, 1)){
				Board nextMove = new Board(currentBoard);
				nextMove.dropADiscFromTop(i, 2);
				int score = max(nextMove, currentDepth+1, alpha, beta);
				if( v > score) v = score;
				if( v <= alpha) return v;
				if( v <= beta) beta = v;
			}
		}
		return v;
	}
	
	void updateBoard(String col, String action, int player) {
		if(Integer.parseInt(action) == 1)	{
			board.dropADiscFromTop(Integer.parseInt(col), player);
		} else  if(Integer.parseInt(action) == 0) {
			board.removeADiscFromBottom(Integer.parseInt(col));
		}
	}
	
	void setName(String name) {
		playerName = name;
	}
	
	public static void main(String[] args) throws IOException {
		log = new Log( args[0]);
		log.writeLog("Intial Log Test----------------");
		PlayerAI ai=new PlayerAI();
		ai.setName("computer" + args[0]);
		System.out.println(ai.playerName);
		boolean gameOver = false;
		while(!gameOver) gameOver = ai.processInput();
	}
}
