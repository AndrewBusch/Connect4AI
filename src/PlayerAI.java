import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class PlayerAI {
	String playerName = "Computer";
	BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	Board board;
	boolean isFirstPlayer;
	final int MAX_DEPTH = 2;
	
	public void processInput() throws IOException{	
		
    	String s=input.readLine();	
		List<String> ls=Arrays.asList(s.split(" "));
		
		if(ls.size()==2){ 								//if opponent just played
			updateBoard(ls.get(0), ls.get(1), 2);
			int moveColumn = traverse(board);
			updateBoard(Integer.toString(moveColumn), "1", 1);
			System.out.println(moveColumn + " 1");
			//updateBoard("3", "1", 1);
		}
		else if(ls.size()==1){							//if game over?
			System.out.println("game over!!!");
		}
		else if(ls.size()==5){          				//ls contains game info
			board = new Board(Integer.parseInt(ls.get(0)), Integer.parseInt(ls.get(1)), Integer.parseInt(ls.get(2)));
			if(isFirstPlayer) {							//Make first move
				System.out.println("3 1");				// TODO: WORRY ABOUT BOARD SIZE
				updateBoard("3", "1", 1);
			}
		}
		else if(ls.size()==4){							//player1: aa player2: bb
			if(ls.indexOf(playerName) == 1) {
				isFirstPlayer = true;
			} else {
				isFirstPlayer = false;
			}
		}
		else
			System.out.println("not what I want");
	}
	
	int traverse(Board currentBoard) {
		int score = Integer.MIN_VALUE;
		int index = 4;
		for(int i = 0; i < board.width; i++) {
			if( board.canDropADiscFromTop(i, 1)){
				Board nextMove = new Board(currentBoard);
				nextMove.dropADiscFromTop(i, 1);
				int value = scoreOfMove(nextMove, 1);
				if( score < value) {
					score = value;
					index = i;
				}
			}
		}
		return index;
	}
	
	int scoreOfMove(Board currentBoard, int currentDepth) {
		if( currentDepth == MAX_DEPTH || currentBoard.isConnectN() != -1) {
			return currentBoard.eval();
		} else if(currentDepth%2 == 0) {		// MAX
			int score = Integer.MIN_VALUE;
			for(int i = 0; i < board.width; i++) {
				Board nextMove = new Board(currentBoard);
				nextMove.dropADiscFromTop(i, 1);
				int value = scoreOfMove(nextMove, currentDepth++);
				if( score < value) {
					score = value;
				}
			}
			return score;
		} else if(currentDepth%2 == 1) {		// MIN
			/*int score = Integer.MAX_VALUE;
			for(int i = 0; i < board.width; i++) {
				Board nextMove = new Board(currentBoard);
				nextMove.dropADiscFromTop(i, 2);
				int value = scoreOfMove(nextMove, currentDepth++);
				if( score > value) {
					score = value;
				}
			}*/
			return 1;
		}
		return -1;
	}
	
	void updateBoard(String col, String action, int player) {
		if(Integer.parseInt(action) == 1)	{
			board.dropADiscFromTop(Integer.parseInt(col), player);
		} else  if(Integer.parseInt(action) == 0) {
			board.removeADiscFromBottom(Integer.parseInt(col));
		}
	}
	
	public static void main(String[] args) throws IOException {
		Log log = new Log();
		log.writeLog("Intial Log Test----------------");
		PlayerAI ai=new PlayerAI();
		System.out.println(ai.playerName);
			ai.processInput();
			ai.processInput();
			ai.processInput();
			ai.processInput();
			ai.processInput();
			ai.processInput();
			ai.processInput();
			ai.processInput();
			ai.processInput();
			ai.processInput();
			ai.processInput();
			ai.processInput();
	}
}
