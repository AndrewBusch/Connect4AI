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
	
	public void processInput() throws IOException{	
		
    	String s=input.readLine();	
		List<String> ls=Arrays.asList(s.split(" "));
		
		if(ls.size()==2){ 								//if opponent just played
			updateBoard(ls.get(0), ls.get(1), 2);
			//Make Move
			//Update the Board Object
		}
		else if(ls.size()==1){							//if game over?
			System.out.println("game over!!!");
		}
		else if(ls.size()==5){          				//ls contains game info
			board = new Board(Integer.parseInt(ls.get(0)), Integer.parseInt(ls.get(1)), Integer.parseInt(ls.get(2)));
			//Make a move if going first				//first move
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
	
	void updateBoard(String col, String action, int player) {
		if(Integer.parseInt(action) == 1)	{
			board.dropADiscFromTop(Integer.parseInt(col), player);
		} else  if(Integer.parseInt(action) == 0) {
			board.removeADiscFromBottom(Integer.parseInt(col));
		}
	}
	
	public static void main(String[] args) throws IOException {
		PlayerAI ai=new PlayerAI();
		System.out.println(ai.playerName);
		ai.processInput();
		
	}
	
}
