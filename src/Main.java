import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Board b = new Board();
        ActionGenerator ag = new ActionGenerator();
        Random r = new Random();

        int player = 2; //black

        b.printBetterBoard();

        //changed while to for for now
        for(int i=0; i<50; i++) {
            System.out.println("PLAYER:" + player);

            //generate all possible moves
            //todo: so far, limitations:
            // - only first queen found
            // - no arrows yet (0,0 as default)
            ArrayList<Action> actions = ag.generate(b, player);


            //take random action from the list and perform it
            Action random_act = actions.get(r.nextInt(actions.size()));
            System.out.println("THEY PICKED: " + random_act);
            random_act.perform(b);


            b.printBetterBoard();
            player = player == 2 ? 1 : 2;
        }
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