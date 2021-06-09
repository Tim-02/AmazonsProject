import java.util.ArrayList;

public class Node {
    /* attributes */
    Node parent;
    Board curr; //new idea: keep board only for root node
    Action action; // action that led to this state
    ArrayList<Node> children;
    boolean isLeaf; //true if finishes the game only set after visited


    /* MCS attributes */
    int player; // to be clear that is the player that is expected to make a move at this node
    int value; // Cumulative of eventual wins and losses for this node
    int visits; // Cumulative number of visits in the branch




    // ** 2 cases of constructors **

    // root node
    public Node(){
        parent = null;
        action = null;
        isLeaf = false;

        curr = new Board();
        children = new ArrayList<>();

        player = 2; // not sure if correct?
        visits = 0;
        value = 0;
    }

    // child node (only need a parent and action to calculate)
    // note that we don't create board by default
    public Node(Node parent, Action action) {
        isLeaf = false;

        this.parent = parent;
        this.action = action;
        children = new ArrayList<>();

        player = (parent.player == 2 ? 1 : 2);
        visits = 0;
        value = 0;
    }

    @Override
    public String toString() {
        if(parent==null)
            return "Node{" +
                    " isRoot=true" +
                    ", isLeaf=" + isLeaf +
                    ", player=" + player +
                    ", value=" + value +
                    ", visits=" + visits +
                    ", UCT=" + getUCT() +
                    "}";
        else
            return "Node{" +
                    " isRoot=false" +
                    ", action=" + action +
                    ", isLeaf=" + isLeaf +
                    ", player=" + player +
                    ", value=" + value +
                    ", visits=" + visits +
                    ", UCT=" + getUCT() +
                    "}";
    }

    //instead we make a board only when needed
    public Board getBoard() {
        if(parent == null)
            return curr;
        else
            return action.performReturn(parent.getBoard());
    }

    public double getUCT(){

        if(this.visits == 0)
            return Integer.MAX_VALUE;
        else {
            double exploit = (double) this.value / (double) this.visits;
            double explore = Math.sqrt( 2 * Math.log(parent.visits) / this.visits);
            return exploit + explore;
        }

    }

    public double getProbability(){

        if(this.visits == 0) // if no visits made here, we assume random chance
            return 0.5;
        else
            return (double) this.value / (double) this.visits;
    }

    public void addVisits(){
        visits++;
    }

    public void addValue(int value){
        if(parent!=null)
            this.value += value;
        else
            this.value = 0;
    }

    // if false, can be a leaf node or just not expanded yet
    public boolean hasChildren() {
        if(children != null)
            return children.size() != 0;
        else
            return false;
    }

}
