import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Random r = new Random();

        //create a tree gen with only a root node
        TreeGen tg = new TreeGen();
        //local version of the board
        Board b = new Board();

        int player = 2; //black

        b.printBetterBoard();

        for(int i=0; i<50; i++) {
            System.out.println("PLAYER:" + player);

            //generate children nodes (only one depth)
            tg.generateTreeOptimized(player);

            //take random node from the children and perform its action
            Node random_node = tg.root.children.get(r.nextInt(tg.root.children.size()));
            System.out.println("THEY PICKED: " + random_node.action);
            random_node.action.perform(b);

            //now chosen node is the new root node
            tg.setRoot(random_node);


            b.printBetterBoard();
            player = player == 2 ? 1 : 2;
        }
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