import java.util.ArrayList;

// An optimized class that combines the Action generator and nodes creation
public class NodesGenerator {
    public Node parent;
    // added board as attribute just to access it easily
    public Board current;
    public ArrayList<Node> nodes;
    public ArrayList<int[]> queens;

    public NodesGenerator(Node parent){
        this.parent = parent;
        current = parent.getBoard();
    }

    //global variable for current queen and position to use in recursion
    private int[] current_queen = new int[2];
    private int[] current_pos = new int[2];

    //generates array of possible actions
    public ArrayList<Node> generate(int player){
        //we have to update lists for every generation
        nodes = new ArrayList<>();
        queens = new ArrayList<>();


        //scan board for queens
        for(int i = 0; i<Board.N; i++)
            for(int j = 0; j<Board.N; j++){
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
            //System.out.println("Current queen at: (" + current_queen[0] + ", " + current_queen[1] + ")");

            for (int i = y - 1; i <= y + 1; i++) {
                for (int j = x - 1; j <= x + 1; j++) {
                    if (i == y && j == x)
                        continue;
                    if (current.get(j, i) != 0)
                        continue;
                    else {
                        //recurse in each direction
                        checkDir(x, y, j, i);
                    }
                }
            }
        }

        if(nodes.size() == 0){ //if no children nodes found then no possible moves
            System.out.println("Found a leaf node.");
            parent.isLeaf = true;
        }

        return nodes;
    }

    public void checkDir(int startX, int startY, int endX, int endY){
        if(current.get(endX, endY) == 0) {
            //find vector of movement
            int x_dir = endX - startX;
            int y_dir = endY - startY;

            //save position for current iteration
            current_pos[0] = endX; current_pos[1] = endY;
            shootArrow(endX, endY);

            checkDir(endX, endY, endX+x_dir, endY+y_dir);
        }
    }

    public void shootArrow(int startX, int startY) {
        int x = startX;
        int y = startY;

        for (int i = y - 1; i <= y + 1; i++) {
            for (int j = x - 1; j <= x + 1; j++) {
                if (i == y && j == x)
                    continue;
                else if (current.get(j, i) != 0) {
                    if(current_queen[0] == j && current_queen[1] == i) //case when we shoot an arrow where wwe were
                        checkArrowDir(x, y, j, i);
                    else
                        continue;
                }
                else {
                    //recurse in each direction
                    checkArrowDir(x, y, j, i);
                }
            }
        }
    }


    public void checkArrowDir(int startX, int startY, int endX, int endY){
        if(current.get(endX, endY) == 0 || (endX == current_queen[0] && endY == current_queen[1])) {
            //find vector of movement
            int x_dir = endX - startX;
            int y_dir = endY - startY;

            //create an action that leads to node
            Action act = new Action(current_queen[0], current_queen[1],
                    current_pos[0], current_pos[1],
                    endX, endY);

            //create the node
            Node n = new Node(parent, act);

            nodes.add(n);

            checkArrowDir(endX, endY, endX+x_dir, endY+y_dir);
        }
    }
}
