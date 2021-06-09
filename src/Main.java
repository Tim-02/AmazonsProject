import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

//at the moment we generate new tree at every turn
//depth gradually increasing (manually for now)

class Main {
    public static void main(String[] args) {
        int player = 2; //black
        Node root = new Node(); // updated every turn
        MonteCarloSearch mcs = new MonteCarloSearch(root, player);


        Node current; // to keep track of current node
        Board b = new Board();

        b.printBetterBoard();


        while(!root.isLeaf) {
            if(player == 2) {
                //update mcs root
                mcs.setRoot(root);

                System.out.println("PLAYER " + player + ": ");


                //calculate the best move
                current = mcs.move();
                System.out.println("PICKED ACTION: " + current.action);
                current.action.perform(b);

                b.printBetterBoard();

                System.out.println("VISITS TO NODE: " + current.visits + "; VALUE: " + current.value + "; PROBABILITY: " + current.getProbability());


                // resetting the root manually for opponent
                root = current;
                root.curr = root.getBoard();
                root.parent = null;

            }
            else if(player == 1) {
                System.out.println("PLAYER:" + player);
                TreeGen tg = new TreeGen(root);
                Random r = new Random();

                //generate children nodes (only one depth)
                tg.generateTreeOptimized(player, 1);

                //take random node from the children and perform its action
                Node random_node = tg.root.children.get(r.nextInt(tg.root.children.size()));
                System.out.println("THEY PICKED: " + random_node.action);
                random_node.action.perform(b);


                b.printBetterBoard();

                root = random_node;
                root.curr = root.getBoard();
                root.parent = null;
            }


            player = player==2 ? 1 : 2;
        }


        System.out.println("GAME OVER: PLAYER " + (3-player) + " WON!");





    }





    //moved random AI to separate method
    public static void randomMove(Board b, int player){
        ActionGenerator ag = new ActionGenerator();
        Random r = new Random();

        //generate all possible moves
        ArrayList<Action> actions = ag.generate(b, player);

        //take random action from the list and perform it
        Action random_act = actions.get(r.nextInt(actions.size()));
        System.out.println("THEY PICKED: " + random_act);
        random_act.perform(b);
    }

    // i put user input in separate method in case we need it
    public static void userMove(Board b){
        Scanner in = new Scanner(System.in);

        System.out.println("Enter your queen location:");
        int qX = in.nextInt() - 1;
        int qY = in.nextInt() - 1;

        System.out.println("Enter where you wish to move:");
        int pX = in.nextInt() - 1;
        int pY = in.nextInt() - 1;

        System.out.println("Enter where to shoot an arrow:");
        int aX = in.nextInt() - 1;
        int aY = in.nextInt() - 1;

        //make new action object
        Action act = new Action(qX, qY, pX, pY, aX, aY);
        //perform the action on our board
        act.perform(b);
    }
}