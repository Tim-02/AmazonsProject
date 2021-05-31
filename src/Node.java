import java.util.ArrayList;

public class Node {
    Node parent;
    Board curr; //new idea: keep board only for root node
    Action action; // action that led to this state
    ArrayList<Node> children;

    // todo: give value to each node

    // ** 2 cases of constructors **

    // root node
    public Node(){
        parent = null;
        action = null;
        curr = new Board();
        children = new ArrayList<>();
    }

    // child node (only need a parent and action to calculate)
    // note that we don't create board by default
    public Node(Node parent, Action action) {
        this.parent = parent;
        this.action = action;
        children = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Node{" +
                "parent=" + parent +
                ", curr=" + getBoard() +
                ", action=" + action +
                '}';
    }

    //instead we make a board only when needed
    public Board getBoard() {
        if(parent == null)
            return curr;
        else
            return action.performReturn(parent.getBoard());
    }
}
