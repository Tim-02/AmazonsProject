public class Board implements Cloneable {
    public static final int N = 10;

    private int[][] board;

    public Board(){
        resetBoard();

        //initialize the board
        init();
    }

    public Board(int[][] source){
        resetBoard();

        setBoard(source);
    }


    public void init(){
        //black queens
        setTo(3, 0, 1);
        setTo(6, 0, 1);
        setTo(0, 3, 1);
        setTo(9, 3, 1);
        //white queens
        setTo(0, 6, 2);
        setTo(9, 6, 2);
        setTo(3, 9, 2);
        setTo(6, 9, 2);
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
    public boolean setBoard(int[][] new_board){
        if(new_board==null)
            return false;

        for(int i=0; i<N; i++)
            System.arraycopy(new_board[i], 0, board[i], 0, N);

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

        for(int i=N-1; i>=0; i--) {
            System.out.printf("%d%s", i, " | ");
            for (int j = 0; j < N; j++) {
                //j corresponds to x, i corresponds to y
                switch(board[j][i]) {
                    case 0:
                        System.out.print(board[j][i] + " ");
                        break;
                    case 1:
                        System.out.print("\033[32m" + board[j][i] + "\033[0m ");
                        break;
                    case 2:
                        System.out.print("\033[34m" + board[j][i] + "\033[0m ");
                        break;
                    case 3:
                        System.out.print("\033[31m" + board[j][i] + "\033[0m ");
                }
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
        b2.resetBoard();
        b2.setBoard(this.board);

        return b2;
    }
}
