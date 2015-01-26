import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


// java -jar Referee.jar "java -jar C:\Users\Crozic\Documents\work\CS\AI\testPlayer.jar 1" "java -jar C:\Users\Crozic\Documents\work\CS\AI\testPlayer.jar 0" 6 7 4 10 10



public class PlayerAI {
	String playerName;
	BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	Board board;
	boolean isPlayerOne;
	final int MAX_DEPTH = 4;
	static Log log;

	/**
	 * Processes the input from the referee and chooses its next move
	 * @return True if successful, false on failure.
	 * @throws IOException
	 */
	public boolean processInput() throws IOException{
		boolean gameOver = false;
    	String s=input.readLine();						// begin reading input
		List<String> ls = Arrays.asList(s.split(" ", 5));
		if(ls.size()==2){ 								// if opponent just played
			updateBoard(ls.get(0), ls.get(1), 2);
			if(ls.get(1).equals("0")) {					// did they pop out?
				board.p2DropUsed = true;
			}
			Move moveColumn = alphaBetaSearch(board);	// alpha beta to pick move
			if(moveColumn.type == 0) {					// did we pop out?
				board.p1DropUsed = true;
			}
			updateBoard(moveColumn, 1);
			System.out.println(moveColumn.toString());
		}
		else if(ls.size()==1){							//if game over?
			log.writeLog("game over");
			return true;
		}
		else if(ls.size()==5){          				//ls contains game info
			board = new Board(Integer.parseInt(ls.get(0)), Integer.parseInt(ls.get(1)), Integer.parseInt(ls.get(2)), log);
			if((isPlayerOne && Integer.parseInt(ls.get(3)) == 1) || (!isPlayerOne && Integer.parseInt(ls.get(3)) == 2)) {							//Make first move
				System.out.println("3 1");				// TODO: WORRY ABOUT BOARD SIZE
				updateBoard("3", "1", 1);
			}
		}
		else if(ls.size()==4){							//player1: aa player2: bb
			if(ls.indexOf(playerName) == 1) {
				isPlayerOne = true;
			} else {
				isPlayerOne = false;
			}
		}
		else {
			System.out.println("not what I want");
		}
		return false;
	}
	
	/*
	 * Uses the alpha beta search algorithm to select the best move for our player
	 */
	/**
	 * @param currentBoard the board to be searched
	 * @return the Move that has the highest evaluated score
	 */
	Move alphaBetaSearch(Board currentBoard) {
		log.writeLog("EVALUATING MOVES---------------------------");
		int v = Integer.MIN_VALUE;
		Move index;
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		int score;
		ArrayList<Move> currentMoves = new ArrayList<Move>();
		currentMoves.addAll(currentBoard.getMoves(1));			// get all legal moves
		index = currentMoves.get(0);
		for(Move i : currentMoves) {							// for each legal move
			Board nextMove = new Board(currentBoard);
			nextMove.executeMove(i,1);							// execute move on new board
			if( currentBoard.isConnectN() != -1 || currentBoard.isFull()) {		// if termainal case
				score = Eval.eval(currentBoard);				// evaluate board
			}
			else score = min(nextMove, 1, alpha, beta);
			if( v < score) {
				v = score;
				index = i;
			}
			if( v >= alpha) alpha = v;
			//log.writeLog("Index: " + i + " Value: " + score);
		}
		return index;
	}
	
	/**
	 * @param currentBoard The board to be evaluated
	 * @param currentDepth The current depth traversed
	 * @param alpha The highest value so far used for alpha beta pruning
	 * @param beta The lowest value so far used for alpha beta pruning
	 * @return The highest move score of the original currentBoard
	 */
	int max( Board currentBoard, int currentDepth, int alpha, int beta) {
		if( currentDepth == MAX_DEPTH || currentBoard.isConnectN() != -1 || currentBoard.isFull()) {
			return (int)(Eval.eval(currentBoard) * (1 - (double) (currentDepth -1) / MAX_DEPTH));
		}
		
		int v = Integer.MIN_VALUE;
		ArrayList<Move> currentMoves;
		currentMoves = board.getMoves(1);
		for(Move i : currentMoves) {
			if( currentBoard.canMakeMove(i, 1)){
				Board nextMove = new Board(currentBoard);
				nextMove.executeMove(i, 1);
				int score = min(nextMove, currentDepth+1, alpha, beta);
				if( v < score) v = score;
				if( v >= beta) return v;
				if( v >= alpha) alpha = v;
			}
		}
		return v;
	}

	/**
	 * @param currentBoard The board to be evaluated
	 * @param currentDepth The current depth traversed
	 * @param alpha The highest value so far used for alpha beta pruning
	 * @param beta The lowest value so far used for alpha beta pruning
	 * @return The lowest evaluation score of the original currentBoard
	 */
	int min( Board currentBoard, int currentDepth, int alpha, int beta) {
		if( currentDepth == MAX_DEPTH || currentBoard.isConnectN() != -1 || currentBoard.isFull()) {
			return (int)(Eval.eval(currentBoard) * (1- (double) (currentDepth -1) / MAX_DEPTH));
		}
		
		int v = Integer.MAX_VALUE;
		ArrayList<Move> currentMoves;
		currentMoves = board.getMoves(2);
		for(Move i : currentMoves) {
			if( currentBoard.canMakeMove(i, 2)){
				Board nextMove = new Board(currentBoard);
				nextMove.executeMove(i, 2);
				int score = max(nextMove, currentDepth+1, alpha, beta);
				if( v > score) v = score;
				if( v <= alpha) return v;
				if( v <= beta) beta = v;
			}
		}
		return v;
	}
	
	/**
	 * Updates the board with a given move
	 * @param col The column of the move
	 * @param action The type of move, pop or drop
	 * @param player The player making the move
	 */
	void updateBoard(String col, String action, int player) {
		if(Integer.parseInt(action) == 1)	{
			board.dropADiscFromTop(Integer.parseInt(col), player);
		} else  if(Integer.parseInt(action) == 0) {
			board.removeADiscFromBottom(Integer.parseInt(col));
		}
	}
	
	/**
	 * Updates the board with a given move
	 * @param move The move to be executed
	 * @param player The player making the move
	 */
	void updateBoard(Move move, int player) {
		if(move.type == 1)	{
			board.dropADiscFromTop(move.column, player);
		} else  if(move.type == 0) {
			board.removeADiscFromBottom(move.column);
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
