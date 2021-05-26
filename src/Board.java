/*
Primary board class that contains board matrix 10x10
Coordinates are counted from right-up corner
0 - empty cell
1 - white queen
2 - black queen
3 - block or arrow
*/

import java.util.ArrayList;

public class Board implements Cloneable {
    public static final int N = 10;

    private int[][] board;
    private ArrayList<int[]> white_queens;
    private ArrayList<int[]> black_queens;

    public Board(){
        white_queens = new ArrayList<>();
        black_queens = new ArrayList<>();
        resetBoard();

        //initialize the board
        init();
    }

    public Board(int[][] source){
        resetBoard();

        setState(source);
    }


    public void init(){
        //black queens
        setTo(3, 0, 2);
        setTo(6, 0, 2);
        setTo(0, 3, 2);
        setTo(9, 3, 2);
        //white queens
        setTo(0, 6, 1);
        setTo(9, 6, 1);
        setTo(3, 9, 1);
        setTo(6, 9, 1);
    }

    // get cell value at (x, y)
    public int get(int x, int y){
        if(isOutOfBounds(x,y))
            return -1;
        else
            return board[x][y];
    }

    public int get(int[] coor){
        return get(coor[0], coor[1]);
    }

    // set cell at (x, y) to given value
    public boolean setTo(int x, int y, int value){
        if(isOutOfBounds(x,y))
            return false;
        else if(isUnknownValue(value))
            return false;

        else{
            board[x][y] = value;
            return true;
        }
    }

    public boolean setTo(int[] coor, int value){
        return setTo(coor[0], coor[1], value);
    }

    // resets board array (needed for cloning)
    public void resetBoard(){
        board = new int[10][];

        for(int i=0; i<N; i++)
            board[i] = new int[10];
    }

    // set current board same as given array
    public boolean setState(int[][] new_board){
        if(new_board==null)
            return false;

        for(int i=0; i<N; i++)
            for(int j=0; j<N; j++)
                board[i][j]=new_board[i][j];

        return true;
    }

    // print board to console
    public void printBoard(){
        for(int i=0; i<N; i++) {
            for (int j = 0; j < N; j++) {
                //j corresponds to x, i corresponds to y
                System.out.print(board[j][i] + " ");
            }
            System.out.println();
        }
    }

    public void printBetterBoard(){
        System.out.println("  | 0 1 2 3 4 5 6 7 8 9");
        System.out.println("--+---------------------");

        for(int i=0; i<N; i++) {
            System.out.printf("%d%s", i, " | ");
            for (int j = 0; j < N; j++) {
                //j corresponds to x, i corresponds to y
                System.out.print(board[j][i] + " ");
            }
            System.out.println();
        }
    }

    //quick coordinate boundaries check
    public boolean isOutOfBounds(int x, int y){
        if(x<0 || x>=N || y<0 || y>=N) {
            //This is called a lot so the message is commented
            //System.out.println("Coordinates out of bounds");
            return true;
        }
        return false;
    }
    // quick value check
    public boolean isUnknownValue(int val){
        if(!(val == 0 || val == 1 || val == 2 || val == 3)) {
            System.out.println("Value out of bounds");
            return true;
        }
        return false;
    }


    //makes a deep copy of Board
    public Object clone() throws CloneNotSupportedException {
        Board b2 = (Board) super.clone();
        b2.setState(this.board);

        return b2;
    }
}
