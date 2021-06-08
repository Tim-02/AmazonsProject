import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

//at the moment we generate new tree at every turn
//depth gradually increasing (manually for now)

class Main {
    public static void main(String[] args) {
        int player = 2; //black
        Node root = new Node();


        Board b = new Board();

        b.printBetterBoard();



        MonteCarloSearch mcs = new MonteCarloSearch(root, player);
        System.out.println("PLAYER " + player + ": ");

        //calculate the best move
        Node current = mcs.move();
        System.out.println("PICKED ACTION: " + current.action);
        current.action.perform(b);

        System.out.println("VISITS TO NODE: " + current.visits + "; VALUE: " + current.value + "; UCT: " + current.getUCT());

        root = current;
        root.curr = root.getBoard();
        root.parent = null;


        b.printBetterBoard();








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