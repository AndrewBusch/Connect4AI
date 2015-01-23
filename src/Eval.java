
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
	 
	 public int isConnectN( int tempN){
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
						if(max2==N) {
							 player2_win=true;
						}
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
						if(max2==N) {
							player2_win=true;
							//log.writeLog("player2 wonned");
						}
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
}
