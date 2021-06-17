import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MonteCarloSearch {
    final double timeLimit = 15;

    /* attributes */
    Node root; // assuming root always represents opponent's move
    NodesGenerator ng;
    int count;
    int player; // player is not supposed to change, we assign it at the beginning

    /* constructors */
    MonteCarloSearch(Node root, int player){
        this.root = root;
        this.player = player;

        ng = new NodesGenerator(root);
    }

    MonteCarloSearch(){
        this.root = new Node();
        player = 2;
    }


    /* METHODS */

    // makes the final decision
    public Node move(double time){
        count = 0;
        Node current;

        System.out.println("**STARTING MCTS**");
        //hardcoded 1000 iterations
        do {
            count++;
            //Board b = new Board();
            //System.out.println("Iteration = " + i);

            current = select(root);
            //System.out.println("\nSELECTED: " + current);

            if (current.visits != 0) {

                current = expand(current);

            }

            simulate(current);

            propagate(current);


        } while(((System.currentTimeMillis()/1000.0) - time) < timeLimit);

        System.out.println("TIME TAKEN: " + ((System.currentTimeMillis()/1000.0) - time));
        System.out.println("Iterations done: " + count);

        return maxProb(root);
    }

    // selects path based on visits and uct
    public Node select(Node parent){
        Node current = parent;

        //while current has children, we select one of it's children with max UCT or randomly
        while(current.hasChildren()){
            current = maxUCT(current);
        }

        return current;
    }


    // utility method for selection to find node with biggest uct
    public Node maxUCT(Node parent){
        Node child;

        //System.out.println("SELECTING FROM CHILDREN OF " + parent);


        if(parent != null && parent.children.size() != 0) {
            //shuffle to make random first
            Collections.shuffle(parent.children);

            child = Collections.max(parent.children, (n1, n2) -> {
                if (n1.getUCT() > n2.getUCT())
                    return 1;
                else if (n1.getUCT() < n2.getUCT())
                    return -1;
                else //randomness didn't actually work before, so i just shuffle the list
                    return 0;
            });
            //System.out.println("MAX UCT: " + child.getUCT());
            //System.out.println("NODE WITH MAX UCT: " + child);

            return child;
        }

        return null;
    }

    public Node maxProb(Node parent){
        Node child;

        //System.out.println("SELECTING FROM CHILDREN OF " + parent);

        if(parent != null && parent.children.size() != 0) {
            //shuffle to make random first
            Collections.shuffle(parent.children);

            child = Collections.max(parent.children, (n1, n2) -> {
                if ( n1.getProbability() > n2.getProbability())
                    return 1;
                else if (n1.getProbability() < n2.getProbability())
                    return -1;
                else {
                    if(n1.value > n2.value)
                        return 1;
                    else if(n1.value < n2.value)
                        return -1;
                    else
                        return 0;
                }
            });
            //System.out.println("MAX PROBABILITY: " + (double) child.value / (double)child.visits);
            //System.out.println("NODE WITH MAX PROB: " + child);

            return child;
        }

        return null;
    }


    // expand unexplored node if possible (i.e. generate nodes)
    // in move() we assume node was already simulated at least once
    public Node expand(Node current) {
        ng = new NodesGenerator(current);

        // if node doesn't have children
        if(!current.hasChildren()) {
            current.children = ng.generate(current.player); // generate more children
        }

        if(!current.isLeaf) {
            // this is just to pick a random node
            current = maxUCT(current);
        }

        return current;
    }


    // simulate random roll-out for a chosen node
    public void simulate(Node current){
        Random r = new Random();

        // make copy of given node (shallow)
        Node temp = current;


        //simulate the game with random moves, until leaf node found
        while(!temp.isLeaf){
            NodesGenerator temp_ng = new NodesGenerator(temp);

            ArrayList<Node> possibleMoves = temp_ng.generate(temp.player);

            if(temp.isLeaf)
                break;
            else
                temp = possibleMoves.get(r.nextInt(possibleMoves.size()));
        }


        //if the leaf node belong to opponent player (their turn), this is win
        if(temp.player != player)
            current.addValue(1);

        current.addVisits();


    }

    // backpropagating the visits and values to the root
    public Node propagate(Node current){
        int val;

        if(!current.isLeaf)
            val = current.value;
        else
            val = current.player != player ? 1 : 0;

        while(current.parent != null){
            current = current.parent;

            current.addValue(val);
            current.addVisits();
        }

        //returns root just in case
        return current;
    }

    //we update the root so that monte carlo can start from there
    public Node setRoot(Node new_root){
        root = new_root;
        root.curr = root.getBoard();
        root.parent = null;
        root.value = 0;
        root.visits = 0;

        return root;
    }
}
