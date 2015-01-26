/**
 * Board for ConnectN
 * 
 * This code is created for cs 4341 AI 2013a at WPI. All rights are reserved. 
 * Modified for use with our connectN AI
 * 
 * Keeps track of the current board state
 * 
 * @author lzhu @author fmsanchez @author abbusch
 */

import java.util.ArrayList;
import java.util.Arrays;

public class Board {

	int width;
	int center;
	int height;
	short[][] board;
	int numOfDiscsInColumn[];
	int emptyCell = 0;
	int N;
	int PLAYER1 = 1;
	int PLAYER2 = 2;
	int NOCONNECTION = -1;
	int TIE = 0;
	Log log;
	boolean p1DropUsed;
	boolean p2DropUsed;

	Board(int height, int width, int N, Log log) {
		this.width = width;
		this.center = (int) ((double) width / 2);
		this.height = height;
		board = new short[height][width];
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++) {
				board[i][j] = (short) this.emptyCell;
			}
		numOfDiscsInColumn = new int[this.width];
		// for(int j=0;j<width;j++)
		// numOfDiscsInColumn[j]=0;
		this.N = N;
		this.log = log;
		this.p1DropUsed = false;
		this.p2DropUsed = false;
	}

	Board(int height, int width, int N) {
		this.width = width;
		this.center = (int) ((double) width / 2);
		this.height = height;
		board = new short[height][width];
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++) {
				board[i][j] = (short) this.emptyCell;
			}
		numOfDiscsInColumn = new int[this.width];
		// for(int j=0;j<width;j++)
		// numOfDiscsInColumn[j]=0;
		this.N = N;
	}

	Board(Board another) {
		this.width = another.width;
		this.height = another.height;
		this.center = another.center;
		board = new short[height][width];
		numOfDiscsInColumn = new int[this.width];
		for (int i = 0; i < height; i++) {
			this.board[i] = Arrays.copyOf(another.board[i],
					another.board[i].length);
		}
		this.numOfDiscsInColumn = Arrays.copyOf(another.numOfDiscsInColumn,
				another.numOfDiscsInColumn.length);
		this.N = another.N;
		this.log = another.log;
		this.p1DropUsed = another.p1DropUsed;
		this.p2DropUsed = another.p2DropUsed;

	}

	/**
	 * Checks a certain Move to see if it is legal
	 * 
	 * @param i
	 *            The Move to be checked for legality
	 * @param player
	 *            The player making the move
	 * @return True if the move is legal, false if it is not
	 */
	public boolean canMakeMove(Move i, int player) {
		// TODO Auto-generated method stub
		if (i.type == 1) {
			return this.canDropADiscFromTop(i.column, player);
		} else if (i.type == 0) {
			return this.canRemoveADiscFromBottom(i.column, player);
		}
		log.writeLog("illegal move");
		return false;
	}

	/**
	 * Executes a certain move on this board
	 * 
	 * @param i
	 *            The move to be executed
	 * @param player
	 *            The player executing the move
	 */
	public void executeMove(Move i, int player) {
		if (i.type == 1) {
			this.dropADiscFromTop(i.column, player);
		}
		if (i.type == 0) {
			this.removeADiscFromBottom(i.column);
			if (player == 1) {
				this.p1DropUsed = true;
			}
			if (player == 2) {
				this.p2DropUsed = true;
			}
		}
	}

	/**
	 * Gets all the legal moves for a player.
	 * 
	 * @param player
	 *            The player for whom the moves must be collected
	 * @return An ArrayList<Move> containing all legal moves for that player
	 */
	public ArrayList<Move> getMoves(int player) {
		ArrayList<Move> moves = new ArrayList<Move>();
		for (int i = 0; i < this.width; i++) {
			Move k = new Move(i, 1);
			Move j = new Move(i, 0);
			if (this.canMakeMove(k, player)) {
				moves.add(k);
			}
			if (this.canMakeMove(j, player)) {
				moves.add(j);
			}
		}
		return moves;
	}

	/**
	 * Prints the current state of the board to the log.
	 */
	public void printBoard() {
		log.writeLog("Board: ");
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				log.writeLogNoLine(board[i][j] + " ");
			}
			log.writeLog("");
		}
	}

	/**
	 * Prints the current state of the board to stdout for testing
	 */
	public void printBoard2() {
		System.out.println("Board: ");
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}

	/**
	 * Checks if a given player can remove a disk from the bottom of a column
	 * 
	 * @param col
	 *            Column to check for removal
	 * @param currentPlayer
	 *            player making the removal
	 * @return return true if the player can, false if not
	 */
	public boolean canRemoveADiscFromBottom(int col, int currentPlayer) {
		if (col < 0 || col >= this.width) {
			log.writeLog("Illegal column!");
			return false;
		} else if (board[height - 1][col] != currentPlayer) {
			// log.writeLog("You don't have a checker in column "+col+" to pop out!");
			return false;
		} else {
			if (currentPlayer == 1 && !this.p1DropUsed) {
				return true;
			}
			if (currentPlayer == 2 && !this.p2DropUsed) {
				return true;
			}
			return false;
		}
	}

	/**
	 * Remove a disk from the bottom of a column
	 * 
	 * @param col
	 *            Column from which the disk will be removed
	 */
	public void removeADiscFromBottom(int col) {
		int i;
		for (i = height - 1; i > height - this.numOfDiscsInColumn[col]; i--) {
			board[i][col] = board[i - 1][col];
		}
		board[i][col] = (short) this.emptyCell;
		this.numOfDiscsInColumn[col]--;
	}

	/**
	 * Checks if the player can drop a disk in the given column
	 * 
	 * @param col
	 *            Column to drop the disk in
	 * @param currentPlayer
	 *            The player dropping the disk
	 * @return True if the player can, false if not
	 */
	public boolean canDropADiscFromTop(int col, int currentPlayer) {
		if (col < 0 || col >= this.width) {
			log.writeLog("Illegal column!");
			return false;
		} else if (this.numOfDiscsInColumn[col] == this.height) {
			// log.writeLog("Column is already full. Cannot drop more disc in it.");
			return false;
		} else
			return true;
	}

	/**
	 * Drops a disk from the top of a column
	 * 
	 * @param col
	 *            Column to drop disk in
	 * @param currentplayer
	 *            Player dropping the disk
	 */
	public void dropADiscFromTop(int col, int currentplayer) {
		int firstEmptyCellRow = this.height - this.numOfDiscsInColumn[col] - 1;
		if (firstEmptyCellRow == -1) {
			printBoard();
		}
		board[firstEmptyCellRow][col] = (short) currentplayer;
		this.numOfDiscsInColumn[col]++;
	}

	/**
	 * Check if one of the players gets N checkers in a row (horizontally,
	 * vertically or diagonally)
	 * 
	 * @return the value of winner. If winner=-1, nobody win and game continues;
	 *         If winner=0/TIE, it's a tie; If winner=1, player1 wins; If
	 *         winner=2, player2 wins.
	 */

	public int isConnectN() {
		int tmp_winner = checkHorizontally();

		if (tmp_winner != this.NOCONNECTION)
			return tmp_winner;

		tmp_winner = checkVertically();
		if (tmp_winner != this.NOCONNECTION)
			return tmp_winner;

		tmp_winner = checkDiagonally1();
		if (tmp_winner != this.NOCONNECTION)
			return tmp_winner;
		tmp_winner = checkDiagonally2();
		if (tmp_winner != this.NOCONNECTION)
			return tmp_winner;

		return this.NOCONNECTION;

	}

	/**
	 * Check if one of the players has n checkers in an position to get N in a
	 * row (horizontally, vertically or diagonally)
	 * 
	 * @param n
	 *            the number in a row you are searching for
	 * @return a vector of the total number of checkers in an advantageous
	 *         position
	 */

	public int[] numOfConnectN(int n) {
		int[] vals = { 0, 0, 0, 0 }; // Player 1 connected n's, Player 2 connect
										// n's, Player 1 split n's, Player 2
										// split n's
		checkHorizontally(n, vals);
		checkVertically(n, vals);
		checkDiagonally1(n, vals);
		checkDiagonally2(n, vals);

		return vals;

	}

	/**
	 * Check if one of the players gets N checkers in a row horizontally
	 * 
	 * @return the value of winner. If winner=-1, nobody win and game continues;
	 *         If winner=0/TIE, it's a tie; If winner=1, player1 wins; If
	 *         winner=2, player2 wins.
	 */
	public int checkHorizontally() {
		int max1 = 0;
		int max2 = 0;
		boolean player1_win = false;
		boolean player2_win = false;
		// check each row, horizontally
		for (int i = 0; i < this.height; i++) {
			max1 = 0;
			max2 = 0;
			for (int j = 0; j < this.width; j++) {
				if (board[i][j] == PLAYER1) {
					max1++;
					max2 = 0;
					if (max1 == N)
						player1_win = true;
				} else if (board[i][j] == PLAYER2) {
					max1 = 0;
					max2++;
					if (max2 == N)
						player2_win = true;
				} else {
					max1 = 0;
					max2 = 0;
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

	/**
	 * checks the board horizontally to find n in a row with up one space
	 * between checkers
	 * 
	 * @param n
	 *            the number in a row to search for
	 * @param vals
	 *            the array to populate with counts for num connected, and num
	 *            split
	 */
	public void checkHorizontally(int n, int[] vals) {
		int max1 = 0;
		int max2 = 0;
		// keeps track of number of gaps per player
		int gap1 = 0;
		int gap2 = 0;
		// check each row, horizontally
		for (int i = 0; i < this.height; i++) {
			max1 = 0;
			max2 = 0;
			gap1 = 0;
			gap2 = 0;
			for (int j = 0; j < this.width; j++) {
				if (board[i][j] == PLAYER1) {
					max1++;
					max2 = 0;
					if (max1 == n) {
						// test gaps to determine where to add value to
						if (gap1 == 0) {
							vals[0]++;
						} else {
							vals[2]++;
						}
						max1 = 0;
					}
				} else if (board[i][j] == PLAYER2) {
					max1 = 0;
					max2++;
					if (max2 == n) {
						// test gaps to determine where to add value to
						if (gap2 == 0) {
							vals[1]++;
						} else {
							vals[3]++;
						}
						max2 = 0;
					}
				} else {
					// check gaps before resetting variables
					if (++gap1 > 1) {
						max1 = 0;
					}
					if (++gap2 > 1) {
						max2 = 0;
					}
				}
			}
		}
	}

	/**
	 * Check if one of the players gets N checkers in a row vertically
	 * 
	 * @return the value of winner. If winner=-1, nobody win and game continues;
	 *         If winner=0/TIE, it's a tie; If winner=1, player1 wins; If
	 *         winner=2, player2 wins.
	 */
	public int checkVertically() {
		// check each column, vertically
		int max1 = 0;
		int max2 = 0;
		boolean player1_win = false;
		boolean player2_win = false;

		for (int j = 0; j < this.width; j++) {
			max1 = 0;
			max2 = 0;
			for (int i = 0; i < this.height; i++) {
				if (board[i][j] == PLAYER1) {
					max1++;
					max2 = 0;
					if (max1 == N)
						player1_win = true;
				} else if (board[i][j] == PLAYER2) {
					max1 = 0;
					max2++;
					if (max2 == N)
						player2_win = true;
				} else {
					max1 = 0;
					max2 = 0;
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

	/**
	 * checks the board vertically to find n in a row with up one space between
	 * checkers
	 * 
	 * @param n
	 *            the number in a row to search for
	 * @param vals
	 *            the array to populate with counts for num connected, and num
	 *            split
	 */
	public void checkVertically(int n, int[] vals) {
		// no gap check here, since there can be no vertical gaps
		// check each column, vertically
		int max1 = 0;
		int max2 = 0;
		for (int j = 0; j < this.width; j++) {
			max1 = 0;
			max2 = 0;
			for (int i = 0; i < this.height; i++) {
				if (board[i][j] == PLAYER1) {
					max1++;
					max2 = 0;
					if (max1 == n) {
						vals[0]++;
						max1 = 0;
					}
				} else if (board[i][j] == PLAYER2) {
					max1 = 0;
					max2++;
					if (max2 == n) {
						vals[1]++;
						max2 = 0;
					}
				} else {
					max1 = 0;
					max2 = 0;
				}
			}
		}
	}

	/**
	 * Check if one of the players gets N checkers in a row diagonally left to
	 * right
	 * 
	 * @return the value of winner. If winner=-1, nobody win and game continues;
	 *         If winner=0/TIE, it's a tie; If winner=1, player1 wins; If
	 *         winner=2, player2 wins.
	 */
	public int checkDiagonally1() {
		// check diagonally y=-x+k
		int max1 = 0;
		int max2 = 0;
		boolean player1_win = false;
		boolean player2_win = false;
		int upper_bound = height - 1 + width - 1 - (N - 1);

		for (int k = N - 1; k <= upper_bound; k++) {
			max1 = 0;
			max2 = 0;
			int x, y;
			if (k < width)
				x = k;
			else
				x = width - 1;
			y = -x + k;

			while (x >= 0 && y < height) {
				// System.out.println("k: "+k+", x: "+x+", y: "+y);
				if (board[height - 1 - y][x] == PLAYER1) {
					max1++;
					max2 = 0;
					if (max1 == N)
						player1_win = true;
				} else if (board[height - 1 - y][x] == PLAYER2) {
					max1 = 0;
					max2++;
					if (max2 == N)
						player2_win = true;
				} else {
					max1 = 0;
					max2 = 0;
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

	/**
	 * checks the board diagonally left to right to find n in a row with up one
	 * space between checkers
	 * 
	 * @param n
	 *            the number in a row to search for
	 * @param vals
	 *            the array to populate with counts for num connected, and num
	 *            split
	 */
	public void checkDiagonally1(int n, int[] vals) {
		// check diagonally y=-x+k
		int max1 = 0;
		int max2 = 0;
		int gap1 = 0;
		int gap2 = 0;
		int upper_bound = height - 1 + width - 1 - (n - 1);

		for (int k = n - 1; k <= upper_bound; k++) {
			max1 = 0;
			max2 = 0;
			// keeps track of number of gaps per player
			gap1 = 0;
			gap2 = 0;
			int x, y;
			if (k < width)
				x = k;
			else
				x = width - 1;
			y = -x + k;

			while (x >= 0 && y < height) {
				// System.out.println("k: "+k+", x: "+x+", y: "+y);
				if (board[height - 1 - y][x] == PLAYER1) {
					max1++;
					max2 = 0;
					if (max1 == n) {
						// test gaps to determine where to add value to
						if (gap1 == 0) {
							vals[0]++;
						} else {
							vals[2]++;
						}
						max1 = 0;
					}
				} else if (board[height - 1 - y][x] == PLAYER2) {
					max1 = 0;
					max2++;
					if (max2 == n) {
						// test gaps to determine where to add value to
						if (gap2 == 0) {
							vals[1]++;
						} else {
							vals[3]++;
						}
						max2 = 0;
					}
				} else {
					// check gaps before resetting variables
					if (++gap1 > 1) {
						max1 = 0;
					}
					if (++gap2 > 1) {
						max2 = 0;
					}
				}
				x--;
				y++;
			}

		}
	}

	/**
	 * Check if one of the players gets N checkers in a row diagonally right to
	 * left
	 * 
	 * @return the value of winner. If winner=-1, nobody win and game continues;
	 *         If winner=0/TIE, it's a tie; If winner=1, player1 wins; If
	 *         winner=2, player2 wins.
	 */

	public int checkDiagonally2() {
		// check diagonally y=x-k
		int max1 = 0;
		int max2 = 0;
		boolean player1_win = false;
		boolean player2_win = false;
		int upper_bound = width - 1 - (N - 1);
		int lower_bound = -(height - 1 - (N - 1));
		// System.out.println("lower: "+lower_bound+", upper_bound: "+upper_bound);
		for (int k = lower_bound; k <= upper_bound; k++) {
			max1 = 0;
			max2 = 0;
			int x, y;
			if (k >= 0)
				x = k;
			else
				x = 0;
			y = x - k;
			while (x >= 0 && x < width && y < height) {
				// System.out.println("k: "+k+", x: "+x+", y: "+y);
				if (board[height - 1 - y][x] == PLAYER1) {
					max1++;
					max2 = 0;
					if (max1 == N)
						player1_win = true;
				} else if (board[height - 1 - y][x] == PLAYER2) {
					max1 = 0;
					max2++;
					if (max2 == N)
						player2_win = true;
				} else {
					max1 = 0;
					max2 = 0;
				}
				x++;
				y++;
			}

		} // end for y=x-k

		if (player1_win && player2_win)
			return this.TIE;
		if (player1_win)
			return this.PLAYER1;
		if (player2_win)
			return this.PLAYER2;

		return this.NOCONNECTION;
	}

	/**
	 * checks the board diagonally right to left to find n in a row with up one
	 * space between checkers
	 * 
	 * @param n
	 *            the number in a row to search for
	 * @param vals
	 *            the array to populate with counts for num connected, and num
	 *            split
	 */
	public void checkDiagonally2(int n, int[] vals) {
		// check diagonally y=x-k
		int max1 = 0;
		int max2 = 0;
		// keeps track of number of gaps per player
		int gap1 = 0;
		int gap2 = 0;
		int upper_bound = width - 1 - (n - 1);
		int lower_bound = -(height - 1 - (n - 1));
		// System.out.println("lower: "+lower_bound+", upper_bound: "+upper_bound);
		for (int k = lower_bound; k <= upper_bound; k++) {
			max1 = 0;
			max2 = 0;
			gap1 = 0;
			gap2 = 0;
			int x, y;
			if (k >= 0)
				x = k;
			else
				x = 0;
			y = x - k;
			while (x >= 0 && x < width && y < height) {
				// System.out.println("k: "+k+", x: "+x+", y: "+y);
				if (board[height - 1 - y][x] == PLAYER1) {
					max1++;
					max2 = 0;
					if (max1 == n) {
						// test gaps to determine where to add value to
						if (gap1 == 0) {
							vals[0]++;
						} else {
							vals[2]++;
						}
						max1 = 0;
					}
				} else if (board[height - 1 - y][x] == PLAYER2) {
					max1 = 0;
					max2++;
					if (max2 == n) {
						// test gaps to determine where to add value to
						if (gap2 == 0) {
							vals[1]++;
						} else {
							vals[3]++;
						}
						max2 = 0;
					}
				} else {
					// check gaps before resetting variables
					if (++gap1 > 1) {
						max1 = 0;
					}
					if (++gap2 > 1) {
						max2 = 0;
					}
				}
				x++;
				y++;
			}

		} // end for y=x-k

	}

	/**
	 * Checks if the board is filled or not
	 * 
	 * @return true if the board is completely filled
	 */
	public boolean isFull() {
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++) {
				if (board[i][j] == this.emptyCell)
					return false;
			}
		return true;
	}

	/**
	 * Adds a checker of the given player into a specific spot
	 * 
	 * @param row
	 *            the row of the location
	 * @param col
	 *            the column of the location
	 * @param player
	 *            the player number
	 */
	public void setBoard(int row, int col, int player) {
		// makes sure it is a legal move
		if (row >= height || col >= width)
			throw new IllegalArgumentException(
					"The row or column number is out of bound!");
		// makes sure its a legal player
		if (player != this.PLAYER1 && player != this.PLAYER2)
			throw new IllegalArgumentException("Wrong player!");
		this.board[row][col] = (short) player;
	}

	/**
	 * A tester function used to populate the board
	 */
	private void testPopulate() {
		// fills test board in
		dropADiscFromTop(0, 1);
		dropADiscFromTop(0, 1);
		dropADiscFromTop(1, 1);
		dropADiscFromTop(1, 1);
		dropADiscFromTop(1, 2);
		dropADiscFromTop(1, 2);
		dropADiscFromTop(1, 2);
		dropADiscFromTop(2, 1);
		dropADiscFromTop(3, 2);
		dropADiscFromTop(3, 1);
		dropADiscFromTop(3, 2);
		dropADiscFromTop(3, 2);
		dropADiscFromTop(4, 2);
		dropADiscFromTop(4, 2);
		// dropADiscFromTop(4, 2);
		dropADiscFromTop(5, 1);
		dropADiscFromTop(5, 2);
		dropADiscFromTop(5, 1);
		dropADiscFromTop(5, 1);
		dropADiscFromTop(6, 2);

		printBoard2();
	}

	/**
	 * A tester function used to makes sure the eval function returned pertinent
	 * values
	 */
	private void testEval() {

		System.out.println("Eval of this board");
		System.out.println(Eval.eval(this));
	}

	/**
	 * Main function used solely for testing the board and evaluations
	 * 
	 * @param args
	 *            arguments that java requires for main functions
	 */
	public static void main(String[] args) {
		Board b = new Board(6, 7, 4);
		b.printBoard2();
		b.testPopulate();

		long startTime = System.nanoTime();
		b.testEval();
		long endTime = System.nanoTime();
		System.out.println("Took " + (endTime - startTime) + " ns");
		// b.test1();
		// b.test2();
		// b.test3();
		// b.test5();
	}

}
