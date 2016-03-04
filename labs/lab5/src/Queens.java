//solves the 8 queens problem
public class Queens {
    public static void main(String[] args) {
        int row = (int)Integer.parseInt(args[0]); //sets number of rows in board
        int[] board = new int [row]; //makes of board of rowXrow size

        //uncomment this block for single solution
        /*for (int i = 0; i < row; i++) {
            if (i == row) {
                printSolutionAndQuit(board);
            }
            else {
                enumerate(board, i+1);
            }
        }*/
        //uncomment line below for all solutions
        enumerate(board, 0);
    } //main

    public static boolean placeQueen(int[] board, int r) { //tries placing a queen in a legal position
        for (int i = 0; i < r; i++) { //checking each possible failing case
            if (board[i] == board[r])             return false;   // check is queen is in same column
            if ((board[i] - board[r]) == (r - i)) return false;   // check is queen is on same diagonal path
            if ((board[r] - board[i]) == (r - i)) return false;   // check if queen is on same diagonal path
        } //for
        return true;
    } //placequeen

    public static void printSolutionAndQuit(int[] board) { //prints solution
        int row = board.length;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < row; j++) {
                if (board[i] == j) {
                    StdOut.print("Q "); //prints Q to mark the spot of a queen
                } //if
                else {
                    StdOut.print("* "); //prints a * to represent an empty square
                } //else
            } //for
            StdOut.println();
        } //for
        StdOut.println();
    } //printsolutionandquit

    public static void enumerate(int[] board, int r) {
        int row = board.length;
        if (r == row) { //print solution is pointer is on last row
            printSolutionAndQuit(board);
        } //if
        else {
            for (int i = 0; i < row; i++) {
                board[r] = i;
                if (placeQueen(board, r)) {
                    enumerate(board, r+1);
                } //if
            } //for
        } //else
    } //enumerate
} //Queens Class

