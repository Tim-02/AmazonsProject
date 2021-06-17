import java.util.ArrayList;

//note: new instance needed for every generation
public class ActionGenerator {
    public ArrayList<Action> actions;
    public ArrayList<int[]> queens;

    //global variable for current queen and position to use in recursion
    private int[] current_queen = new int[2];
    private int[] current_pos = new int[2];

    //generates array of possible actions
    public ArrayList<Action> generate(Board current, int player){
        //we have to update lists for every generation
        actions = new ArrayList<>();
        queens = new ArrayList<>();


        //scan board for queens
        for(int i = 0; i< Board.N; i++)
            for(int j = 0; j< Board.N; j++){
                if(current.get(j, i) == player){
                    queens.add(new int[]{j, i});
                }
            }

        //find actions for each queen
        for(int[] queen : queens){
            int x = queen[0];
            int y = queen[1];
            current_queen[0] = x;
            current_queen[1] = y;

            //debug
            System.out.println("Current queen at: (" + current_queen[0] + ", " + current_queen[1] + ")");

            for (int i = y - 1; i <= y + 1; i++) {
                for (int j = x - 1; j <= x + 1; j++) {
                    if (i == y && j == x)
                        continue;
                    if (current.get(j, i) != 0)
                        continue;
                    else
                        //recurse in each direction
                        checkDir(current, x, y, j, i);
                }
            }
        }

        return actions;
    }

    public void checkDir(Board current, int startX, int startY, int endX, int endY){
        if(current.get(endX, endY) == 0) {
            //find vector of movement
            int x_dir = endX - startX;
            int y_dir = endY - startY;

            //save position for current iteration
            current_pos[0] = endX; current_pos[1] = endY;
            shootArrow(current, endX, endY);

            checkDir(current, endX, endY, endX+x_dir, endY+y_dir);
        }
    }

    public void shootArrow(Board current, int startX, int startY) {
        int x = startX;
        int y = startY;

        for (int i = y - 1; i <= y + 1; i++) {
            for (int j = x - 1; j <= x + 1; j++) {
                if (i == y && j == x)
                    continue;
                if (current.get(j, i) != 0)
                    continue;
                else
                    //recurse in each direction
                    checkArrowDir(current, x, y, j, i);
            }
        }
    }


    public void checkArrowDir(Board current, int startX, int startY, int endX, int endY){
        if(current.get(endX, endY) == 0) {
            //find vector of movement
            int x_dir = endX - startX;
            int y_dir = endY - startY;

            //save position for current iteration
            Action act = new Action(current_queen[0], current_queen[1],
                    current_pos[0], current_pos[1],
                    endX, endY);

            actions.add(act);

            checkArrowDir(current, endX, endY, endX+x_dir, endY+y_dir);
        }
    }
}
