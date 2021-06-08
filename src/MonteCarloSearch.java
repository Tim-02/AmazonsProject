
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class MonteCarloSearch {
    /* attributes */
    Node root; // assuming root always represents opponent's move
    NodesGenerator ng;
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
    public Node move(){
        Node current = root;

        System.out.println("**STARTING MCTS**");

        //hardcoded 1000 iterations
        for(int i=0; i<5000; i++) {
            Board b = new Board();
            System.out.println("Iteration = " + i);

            current = select(root);
            System.out.println("\nSELECTED: " + current);

            current = expand(current);
            System.out.println("\nNODE EXPANDED TO : " + current);

            simulate(current);
            System.out.println("\nSIMULATED: " + current.value + "/" + current.visits);

            System.out.println("\nBACKPROPAGATE TO THE ROOT");
            System.out.println("VISITED: " + propagate(current).visits);




            current.action.perform(b);
            b.printBetterBoard();
        }


        return maxProb(root);
    }

    // selects path based on visits and uct
    public Node select(Node parent){
        Node current = parent;

        //while current has children, we select one of it's children building a path
        while(current.hasChildren()){
            if (current.visits < current.children.size())
                return current;
            current = maxUCT(current);
        }

        return current;
    }


    // utility method for selection to find node with biggest uct
    public Node maxUCT(Node parent){
        Node child;
        Random r = new Random();

        System.out.println("SELECTING FROM CHILDREN OF " + parent);

        if(parent != null && parent.children.size() != 0) {
            child = Collections.max(parent.children, (n1, n2) -> {
                if (n1.getUCT() > n2.getUCT())
                    return 1;
                else if (n1.getUCT() < n2.getUCT())
                    return -1;
                else if(r.nextBoolean())
                    return 1;
                else
                    return -1;
            });
            System.out.println("MAX UCT: " + child.getUCT());
            System.out.println("NODE WITH MAX UCT: " + child);

            return child;
        }

        return null;
    }

    public Node maxProb(Node parent){
        Node child;
        Random r = new Random();

        System.out.println("SELECTING FROM CHILDREN OF " + parent);

        if(parent != null && parent.children.size() != 0) {
            child = Collections.max(parent.children, (n1, n2) -> {
                if ((double)n1.value/ (double)n1.visits > (double) n2.value / (double) n2.visits)
                    return 1;
                else if ((double)n1.value/ (double)n1.visits < (double) n2.value / (double) n2.visits)
                    return -1;
                else if(r.nextBoolean())
                    return 1;
                else
                    return -1;
            });
            System.out.println("MAX PROBABILITY: " + (double) child.value / (double)child.visits);
            System.out.println("NODE WITH MAX UCT: " + child);

            return child;
        }

        return null;
    }


    // expand unexplored node if possible (i.e. generate nodes)
    public Node expand(Node current) {
        ng = new NodesGenerator(current);

        // if node doesn't have children
        if(!current.hasChildren()) {
            current.children = ng.generate(current.player); // generate more children
        }

        if(!current.isLeaf) {
            Random r = new Random();
            Node random = current.children.get(r.nextInt(current.children.size()));

            while (random.visits != 0) {
                random = current.children.get(r.nextInt(current.children.size()));
            }

            return random;
        }

        else
            return null;
    }


    // simulate random roll-out for a chosen node
    public void simulate(Node current){
        Random r = new Random();

        // make copy of given node
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

        //if the leaf node belong to our player, this is win
        if(temp.player == player)
            current.addValue(1);
        else
            current.addValue(-1);

        current.addVisits();
    }

    // backpropagating the visits and values to the root
    public Node propagate(Node current){
        int value = current.value;

        while(current.parent != null){
            current = current.parent;

            current.addValue(value);
            current.addVisits();
        }

        //returns root just in case
        return current;
    }
}
