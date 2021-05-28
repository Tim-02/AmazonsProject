import java.util.ArrayList;

public class Node {
    Node parent = new Node();
    Board curr = new Board();
    ArrayList<Node> children = new ArrayList<>();

    public Node(){
    }

    public Node(Board b){
        curr = b;
    }



}
