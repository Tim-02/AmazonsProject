import java.util.ArrayList;

public class ActionGenerator {
    ArrayList<Action> actions = new ArrayList<>();

    // generates array of possible actions
    public ArrayList<Action> generate(Board current, int player){

        ArrayList<int[]> queens = new ArrayList<>();

        //todo: generate possible actions for the player
        for(int i = 0; i<Board.N; i++)
            for(int j = 0; j<Board.N; j++){
                if(current.get(j, i) == player){
                    queens.add(new int[]{j, i});
                }
            }

        int x = queens.get(0)[0];
        int y = queens.get(0)[1];

        for(int i = y-1; i<=y+1; i++) {
            for (int j = x - 1; j <=x + 1; j++) {
                if (i == y && j == x)
                    continue;
                if (current.get(j, i) != 0)
                    continue;
                //recurse
                //System.out.print("(" + i + ", " + j + ") ");
                switch(i-y){
                    case -1:
                        switch(j-x){
                            case -1:
                                //Recursively check (x-1, y-1)
                                System.out.println((x-1) + ", " + (y-1));
                                break;
                            case 0:
                                //Recursively check (x, y-1)
                                System.out.println((x) + ", " + (y-1));
                                break;
                            case 1:
                                //Recursively check (x+1, y-1)
                                System.out.println((x+1) + ", " + (y-1));
                                break;
                        }
                        break;
                    case 0:
                        switch(j-x){
                            case -1:
                                //Recursively check (x-1, y-1)
                                System.out.println((x-1) + ", " + (y));
                                break;
                            case 0:
                                //Queen position
                                System.out.println((x) + ", " + (y));
                                break;
                            case 1:
                                //Recursively check (x+1, y-1)
                                System.out.println((x+1) + ", " + (y));
                                break;
                        }
                        break;
                    case 1:
                        switch(j-x){
                            case -1:
                                //Recursively check (x-1, y+1)
                                System.out.println((x-1) + ", " + (y+1));
                                break;
                            case 0:
                                //Recursively check (x, y+1)
                                //!!!
                                System.out.println((x) + ", " + (y+1));

                                break;
                            case 1:
                                //Recursively check (x+1, y+1)
                                System.out.println((x+1) + ", " + (y+1));

                                break;
                        }
                        break;
                }
            }
            System.out.println();
        }


        return actions;
    }

    public Action recurse(int startX, int startY, int endX, int endY){
        int x_dir = endX - startX;
        int y_dir = endY - startY;
        Action act = new Action();


        return recurse(endX, endY, endX + x_dir, endY + y_dir);
    }

}
