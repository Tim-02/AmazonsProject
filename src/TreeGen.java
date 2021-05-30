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

        long startTime = System.nanoTime();
        temp = aGen.generate(root.curr, player);
        long endTime = System.nanoTime();

        System.out.println("Time spent generating actions: " + (float)(endTime-startTime)/1000000 + " ms");

        startTime = System.nanoTime();
        for(Action act : temp){
            //create a child node by giving parent and action
            Node n = new Node(root, act);
            root.children.add(n);

        }

        System.out.println("Possible moves: " + root.children.size());
        endTime = System.nanoTime();
        System.out.println("Time spent generating nodes: " + (float)(endTime-startTime)/1000000 + " ms");

    }

    public void generateTreeOptimized(int player){

        long startTime = System.nanoTime();
        NodesGenerator ng = new NodesGenerator(root);
        root.children = ng.generate(player);
        long endTime = System.nanoTime();

        System.out.println("Possible moves: " + root.children.size());
        System.out.println("Time spent generating nodes: " + (float)(endTime-startTime)/1000000 + " ms");

    }

    //todo:
    // - add the depth functionality to perform limited number of levels (only one right now)

    public void setRoot(Node new_root){
        if(new_root == null){
            System.out.println("Root of the tree is set to null");
        }
        this.root = new_root;
    }

}
