import java.util.ArrayList;

public class TreeGen {

    //keep reference to the root
    Node root;
    //total recursion count
    static long size;

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

    //uses older ActionGenerator
    public void generateTree(int player){
        ActionGenerator aGen = new ActionGenerator();


        long startTime = System.nanoTime();
        ArrayList<Action> temp = aGen.generate(root.getBoard(), player);
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

    //
    public void generateTreeOptimized(int player, int depth){
        size = 1;

        long startTime = System.nanoTime();
        NodesGenerator ng = new NodesGenerator(root);
        root.children = ng.generate(player);
        long endTime = System.nanoTime();

        size+=root.children.size();

        if(depth > 1) {
            for (Node n : root.children) {
                //recursive call for tree generation
                generateTreeOptimized(player == 2 ? 1 : 2, n, depth-1);
            }

        }

        System.out.println("Possible moves: " + root.children.size());
        System.out.println("Time spent generating nodes: " + (float)(endTime-startTime)/1000000 + " ms");

    }

    //recursive call for deeper trees
    public void generateTreeOptimized(int player, Node parent, int depth){
        NodesGenerator ng = new NodesGenerator(parent);
        parent.children = ng.generate(player);
        size+=parent.children.size();

        System.out.println("Total tree: " + size);

        if(depth > 1) {
            for (Node n : parent.children) {
                //recursive call
                generateTreeOptimized(player == 2 ? 1 : 2, n, depth - 1);
                size++;
            }
        }
    }

    public void setRoot(Node new_root){
        if(new_root == null){
            System.out.println("Root of the tree is set to null");
        }
        this.root = new_root;
        this.root.curr = this.root.getBoard();
        this.root.parent = null;
    }

}
