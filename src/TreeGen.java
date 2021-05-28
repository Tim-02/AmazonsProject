import java.util.ArrayList;

public class TreeGen {
    //for that access
    ActionGenerator aGen = new ActionGenerator();
    ArrayList<Action> temp = new ArrayList();

    //keep reference to the root
    Node root;
    //hardcoded depth limit
    public int depth = 3;

    //constructors

    //if we start the tree from scratch
    TreeGen(){
        root = new Node();
    }

    //if we start tree from a node
    TreeGen(Node root){
        this.root = root;
    }


    //methods

    //called for root node
    public void generateTree(int player){

        temp = aGen.generate(root.curr, player);


        for(Action act : temp){
            //create a child node by giving parent and action
            Node n = new Node(root, act);
            root.children.add(n);

        }

        System.out.println("Possible moves: " + root.children.size());


    }

    //todo:
    // - add the depth functionality to perform limited number of levels (only one right now)
    // - optimize by generating nodes together with actions

    public void setRoot(Node new_root){
        if(new_root == null){
            System.out.println("Root of the tree is set to null");
        }
        this.root = new_root;
    }

}
