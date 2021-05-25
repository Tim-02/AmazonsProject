import java.util.ArrayList;

public class ActionGenerator {
    private int[] current_queen = new int[2];
    ArrayList<Action> actions;
    ArrayList<int[]> queens;

    // generates array of possible actions
    public ArrayList<Action> generate(Board current, int player){
        //we have to update lists for every generation
        actions = new ArrayList<>();
        queens = new ArrayList<>();


        for(int i = 0; i<Board.N; i++)
            for(int j = 0; j<Board.N; j++){
                if(current.get(j, i) == player){
                    queens.add(new int[]{j, i});
                }
            }


        int x = queens.get(0)[0];
        int y = queens.get(0)[1];
        current_queen[0] = x;
        current_queen[1] = y;

        //debug
        System.out.println("Current queen at: (" + current_queen[0] + ", " + current_queen[1] + ")");

        for(int i = y-1; i<=y+1; i++) {
            for (int j = x - 1; j <=x + 1; j++) {
                if (i == y && j == x)
                    continue;
                if (current.get(j, i) != 0)
                    continue;
                else
                    //recurse in each direction
                    checkDir(current, x, y, j, i);
            }
        }

        return actions;
    }

    public void checkDir(Board current, int startX, int startY, int endX, int endY){
        if(current.get(endX, endY) == 0) {
            //find vector of movement
            int x_dir = endX - startX;
            int y_dir = endY - startY;

            //create new action for each move
            Action act = new Action();
            act.setQueen(current_queen[0], current_queen[1]);
            act.setPos(endX, endY);
            actions.add(act);

            checkDir(current, endX, endY, endX+x_dir, endY+y_dir);
        }
    }

}
