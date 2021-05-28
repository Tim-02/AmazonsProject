import java.util.ArrayList;

public class Node {
    Node parent;
    Board curr;
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
    public Node(Node parent, Action action) {
        this.parent = parent;
        this.action = action;
        curr = action.performReturn(parent.curr);
        children = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Node{" +
                "parent=" + parent +
                ", curr=" + curr +
                ", action=" + action +
                '}';
    }
}
