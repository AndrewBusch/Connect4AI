import java.util.Arrays;

/**
 * This code is created for cs 4341 AI 2013a at WPI. All rights are reserved. 
 */


/**
 * @author lzhu
 *
 */
public class Board {
	
	int width;
	int height;
	short board[][];
	int numOfDiscsInColumn[];
	short emptyCell=9;
	int N;
	int PLAYER1=1;
	int PLAYER2=2;
	int NOCONNECTION=-1;
	int TIE=0;
	Log log = new Log();
	
	 Board(int height, int width, int N){
		this.width=width;
		this.height=height;
		board =new short[height][width];
		for(int i=0;i<height;i++)
			for(int j=0;j<width;j++){
				board[i][j]=this.emptyCell;
			}
		numOfDiscsInColumn=new int[this.width];
//		for(int j=0;j<width;j++)
//			numOfDiscsInColumn[j]=0;
		this.N=N;
	 }
	 
	 Board( Board another) {
		this.width = another.width;
		this.height = another.height;
		board = new short[height][width];
		numOfDiscsInColumn=new int[this.width];
		for(int i = 0; i < height; i++){
			this.board[i] = Arrays.copyOf(another.board[i], another.board[i].length);
		}
		this.numOfDiscsInColumn = Arrays.copyOf(another.numOfDiscsInColumn, another.numOfDiscsInColumn.length);
		this.N = another.N;
	 }
	 
	 public void printBoard(){
		 log.writeLog("Board: ");
		 for(int i=0;i<height;i++){
				for(int j=0;j<width;j++){
					log.writeLogNoLine(board[i][j]+" ");
				}
				log.writeLog("");
		 }
	 }
	 
	 public int eval() {
		int sum = 0;
		int score = isConnectN();
		if( score == 1 ) {			// -1 no winner, 0 tie, 1 win, 2 loss
			sum = 100;
		} else if( score == 2) {
			sum = -100;
		} else if( score == -1) {
	 	}
		return sum;
	 }
	 
	 public boolean canRemoveADiscFromBottom(int col, int currentPlayer){
		 if(col<0 || col>=this.width) {
			 log.writeLog("Illegal column!");
			 return false;
			 }
		 else if(board[height-1][col]!=currentPlayer){
			 log.writeLog("You don't have a checker in column "+col+" to pop out!");
			 return false;
		 }
		 else 
			 return true;
	 }
	 
	 
	 
	 public void removeADiscFromBottom(int col){
		 int i;
		 for(i=height-1;i>height-this.numOfDiscsInColumn[col];i--){
			 board[i][col]=board[i-1][col];
		 }
		 board[i][col]=this.emptyCell;
		 this.numOfDiscsInColumn[col]--;
	 }
	 
	 
	 public boolean canDropADiscFromTop(int col, int currentPlayer){
		 log.writeLog( "hang on " + this.numOfDiscsInColumn[col] + " " + this.height);
		 if(col<0 || col>=this.width) {
			 log.writeLog("Illegal column!");
			 return false;
		 }
		 else if(this.numOfDiscsInColumn[col] == this.height){
			 log.writeLog("Column is already full. Cannot drop more disc in it.");
			 return false;
		 }
		 else{
			 return true;
		 }
	 }
	 
	 public void dropADiscFromTop(int col, int currentplayer){
		 int firstEmptyCellRow=height-this.numOfDiscsInColumn[col]-1;
		 //log.writeLog("really? " + (height-this.numOfDiscsInColumn[col]-1));
		 board[firstEmptyCellRow][col]=(short)currentplayer;
		 log.writeLog("in the thing1");
		 this.numOfDiscsInColumn[col]++;
	 }
	 
	 /**
	  * Check if one of the players gets N checkers in a row (horizontally, vertically or diagonally) 
	  *  @return the value of winner. If winner=-1, nobody win and game continues; If winner=0/TIE, it's a tie;
	  * 			If winner=1, player1 wins; If winner=2, player2 wins. 
	  */
	 
	 public int isConnectN(){
		int tmp_winner=checkHorizontally();
		
		if(tmp_winner!=this.NOCONNECTION)
			return tmp_winner;
		
		 tmp_winner=checkVertically();
		 if(tmp_winner!=this.NOCONNECTION)
				return tmp_winner;
		 
		 tmp_winner=checkDiagonally1();
		 if(tmp_winner!=this.NOCONNECTION)
				return tmp_winner; 
		 tmp_winner=checkDiagonally2();
		 if(tmp_winner!=this.NOCONNECTION)
				return tmp_winner; 
		 
		 return this.NOCONNECTION;
		 
	 }
	 
  public int checkHorizontally(){
	  int max1=0;
		 int max2=0;
		 boolean player1_win=false;
		 boolean player2_win=false;
		 //check each row, horizontally
		 for(int i=0;i<this.height;i++){
			 max1=0;
			 max2=0;
			for(int j=0;j<this.width;j++){
				if(board[i][j]==PLAYER1){
					max1++;
					max2=0;
					if(max1==N)
						 player1_win=true;
				}
				else if(board[i][j]==PLAYER2){
					max1=0;
					max2++;
					if(max2==N)
						 player2_win=true;
				}
				else{
					max1=0;
					max2=0;
				}
			}
		 } 
		 if (player1_win && player2_win)
			 return this.TIE;
		 if (player1_win)
			 return this.PLAYER1;
		 if (player2_win)
			 return this.PLAYER2;
		 
		 return this.NOCONNECTION;
  }

  public int checkVertically(){
	  //check each column, vertically
	  int max1=0;
	  int max2=0;
	  boolean player1_win=false;
	  boolean player2_win=false;
		 
		 for(int j=0;j<this.width;j++){
			 max1=0;
			 max2=0;
			for(int i=0;i<this.height;i++){
				if(board[i][j]==PLAYER1){
					max1++;
					max2=0;
					if(max1==N)
						 player1_win=true;
				}
				else if(board[i][j]==PLAYER2){
					max1=0;
					max2++;
					if(max2==N)
						player2_win=true;
				}
				else{
					max1=0;
					max2=0;
				}
			}
		 } 
		 if (player1_win && player2_win)
			 return this.TIE;
		 if (player1_win)
			 return this.PLAYER1;
		 if (player2_win)
			 return this.PLAYER2;
		 
		 return this.NOCONNECTION;
  }
  
   public int checkDiagonally1(){
	 //check diagonally y=-x+k
	   int max1=0;
	   int max2=0;
	   boolean player1_win=false;
	   boolean player2_win=false;
	   int upper_bound=height-1+width-1-(N-1);
	   
		 for(int k=N-1;k<=upper_bound;k++){			
			 max1=0;
			 max2=0;
			 int x,y;
			 if(k<width) 
				 x=k;
			 else
				 x=width-1;
			 y=-x+k;
			 
			while(x>=0  && y<height){
				// log.writeLog("k: "+k+", x: "+x+", y: "+y);
				if(board[height-1-y][x]==PLAYER1){
					max1++;
					max2=0;
					if(max1==N)
						 player1_win=true;
				}
				else if(board[height-1-y][x]==PLAYER2){
					max1=0;
					max2++;
					if(max2==N)
						player2_win=true;
				}
				else{
					max1=0;
					max2=0;
				}
				x--;
				y++;
			}	 
			 
		 }
		 if (player1_win && player2_win)
			 return this.TIE;
		 if (player1_win)
			 return this.PLAYER1;
		 if (player2_win)
			 return this.PLAYER2;
		 
		 return this.NOCONNECTION;
   }
	 
   public int checkDiagonally2(){
	 //check diagonally y=x-k
	   int max1=0;
	   int max2=0;
	   boolean player1_win=false;
	   boolean player2_win=false;
	   int upper_bound=width-1-(N-1);
	   int  lower_bound=-(height-1-(N-1));
	  // log.writeLog("lower: "+lower_bound+", upper_bound: "+upper_bound);
		 for(int k=lower_bound;k<=upper_bound;k++){			
			 max1=0;
			 max2=0;
			 int x,y;
			 if(k>=0) 
				 x=k;
			 else
				 x=0;
			 y=x-k;
			while(x>=0 && x<width && y<height){
				// log.writeLog("k: "+k+", x: "+x+", y: "+y);
				if(board[height-1-y][x]==PLAYER1){
					max1++;
					max2=0;
					if(max1==N)
						 player1_win=true;
				}
				else if(board[height-1-y][x]==PLAYER2){
					max1=0;
					max2++;
					if(max2==N)
						player2_win=true;
				}
				else{
					max1=0;
					max2=0;
				}
				x++;
				y++;
			}	 
			 
		 }	 //end for y=x-k
		 
		 if (player1_win && player2_win)
			 return this.TIE;
		 if (player1_win)
			 return this.PLAYER1;
		 if (player2_win)
			 return this.PLAYER2;
		 
		 return this.NOCONNECTION;
   }
   
	public boolean isFull(){
		for(int i=0;i<height;i++)
			for(int j=0;j<width;j++){
				if(board[i][j]==this.emptyCell)
					return false;
			}
		return true;
	}
	 
	 
	 public void setBoard(int row, int col, int player){
		 if(row>=height || col>=width)
			 throw new IllegalArgumentException("The row or column number is out of bound!");
		 if(player!=this.PLAYER1 && player!=this.PLAYER2)
			 throw new IllegalArgumentException("Wrong player!");
		 this.board[row][col]=(short)player;
	 }
	 
	 /**
	  * test is connect N diagonally y=-x+k
	  * */ 
	 private void test1(){
		 dropADiscFromTop(2,1);
		 dropADiscFromTop(1,2);
		 dropADiscFromTop(1,1);
		 dropADiscFromTop(0,2);
		 dropADiscFromTop(0,1);
		 dropADiscFromTop(2,2);
		 dropADiscFromTop(0,1);
		 printBoard();
		 int tmp_winner= checkDiagonally1();
		 log.writeLog("Winner: "+tmp_winner);	
	 }
	 /**
	  * test is connect N diagonally y=-x+k
	  * */ 
	 private void test2(){		
		 setBoard(1,2,this.PLAYER1);
		 setBoard(2,3,this.PLAYER1);
		 setBoard(3,4,this.PLAYER1);		 
		 printBoard();
		 int tmp_winner= checkDiagonally1();
		 //int tmp_winner= isConnectN();
		 log.writeLog("Winner: "+tmp_winner);	
	 }

	 /**
	  * test is connect N diagonally y=x-k
	  * */ 
	 private void test3(){		
//		 setBoard(2,5,this.PLAYER2);
//		 setBoard(4,3,this.PLAYER2);
//		 setBoard(3,4,this.PLAYER2);
		 setBoard(5,4,this.PLAYER1);
		 setBoard(4,5,this.PLAYER1);
		 setBoard(3,6,this.PLAYER1);
		 printBoard();
		 int tmp_winner= checkDiagonally2();
		 //int tmp_winner= isConnectN();
		 log.writeLog("Winner: "+tmp_winner);	
	 }
	 
	 
	 /**
	  * test is connect N diagonally y=-x+k
	  * */ 
	 private void test4(){
		 setBoard(2,0,this.PLAYER1);
		 setBoard(3,1,this.PLAYER1);
		// setBoard(4,2,this.PLAYER1);
		 setBoard(5,3,this.PLAYER1);		 
		 printBoard();
		 int tmp_winner= checkDiagonally1();
		 //int tmp_winner= isConnectN();
		 log.writeLog("Winner: "+tmp_winner);	
	 }
	 /**
	  * test should ends with tie
	  * */ 
	 private void test5(){
		 setBoard(2,0,this.PLAYER1);
		 setBoard(3,1,this.PLAYER1);
		 setBoard(4,2,this.PLAYER1);
		// setBoard(1,4,this.PLAYER1);
		 
//		 setBoard(3,1,this.PLAYER1);
//		 setBoard(3,2,this.PLAYER1);
//		 setBoard(3,3,this.PLAYER1);
//		 setBoard(3,4,this.PLAYER1);
		 
		 setBoard(0,3,this.PLAYER2);
		 setBoard(2,5,this.PLAYER2);
		 setBoard(2,3,this.PLAYER2);
		 setBoard(1,4,this.PLAYER2);
		 printBoard();
		// int tmp_winner= this.checkHorizontally();
		 int tmp_winner= this.checkDiagonally1();
		 log.writeLog("Winner: "+tmp_winner);	
	 } 
	 
	 public static void main(String [] arg){
		 Board b=new Board(6,7,3);

 		 b.printBoard();
//		 b.test1();
//		 b.test2();
//       b.test3();
 //        b.test5();
	 }
}
