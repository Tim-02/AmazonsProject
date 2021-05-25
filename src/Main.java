import java.util.Scanner;

class Main {
    public static void main(String[] args){
        Board b = new Board();
        ActionGenerator ag = new ActionGenerator();
        Action act;
        Scanner in = new Scanner(System.in);
        int player = 2; //black

        b.printBetterBoard();

        while(true){
            ag.generate(b, player);
            System.out.println("Enter your queen location:");
            int qX = in.nextInt() -1;
            int qY = in.nextInt() -1;

            System.out.println("Enter where you wish to move:");
            int pX = in.nextInt() -1;
            int pY = in.nextInt() -1;

            System.out.println("Enter where to shoot an arrow:");
            int aX = in.nextInt() -1;
            int aY = in.nextInt() -1;

            //make new action object
            act = new Action(qX, qY, pX, pY, aX, aY);
            //perform the action on our board
            act.perform(b);

            player = player == 2 ? 1 : 2;
            b.printBetterBoard();
        }
    }
}