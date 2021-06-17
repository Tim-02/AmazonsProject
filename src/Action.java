import java.util.Arrays;

/*
Class that makes it possible to transition between states
*/
public class Action implements Cloneable{
    //location of picked queen given by (x,y)
    int[] queen;
    //position to put queen in given by (x,y)
    int[] pos;
    //position where to shoot an arrow
    int[] arrow;

    public Action(){
        queen = new int[2];
        pos = new int[2];
        arrow = new int[2];
    }

    public Action(int[] queen, int[] pos, int[] arrow){
        if( !( isOutOfBounds(queen[0], queen[1])
                || isOutOfBounds(pos[0], pos[1])
                || isOutOfBounds(arrow[0], arrow[1]))) {

            this.queen = queen;
            this.pos = pos;
            this.arrow = arrow;

        }
    }

    public Action(int queenX, int queenY, int posX, int posY, int arrowX, int arrowY){
        queen = new int[2];
        pos = new int[2];
        arrow = new int[2];

        if( !( isOutOfBounds(queenX, queenY)
                || isOutOfBounds(posX, posY)
                || isOutOfBounds(arrowX, arrowY))) {

            queen[0] = queenX; queen[1] = queenY;
            pos[0] = posX;     pos[1] = posY;
            arrow[0] = arrowX; arrow[1] = arrowY;

        }
    }

    public boolean setQueen(int x, int y){
        if(isOutOfBounds(x,y))
            return false;
        queen[0] = x;
        queen[1] = y;
        return true;
    }

    public boolean setPos(int x, int y){
        if(isOutOfBounds(x,y))
            return false;
        pos[0] = x;
        pos[1] = y;
        return true;
    }

    public boolean setArrow(int x, int y){
        if(isOutOfBounds(x,y))
            return false;
        arrow[0] = x;
        arrow[1] = y;
        return true;
    }

    public void perform(Board current){
        int qColor = current.get(queen);
        current.setTo(queen, 0);
        current.setTo(pos, qColor);
        current.setTo(arrow, 3);
    }

    public Board performReturn(Board current) {
        try {
            Board ret = (Board) current.clone();
            int qColor = ret.get(queen);
            ret.setTo(queen, 0);
            ret.setTo(pos, qColor);
            ret.setTo(arrow, 3);

            return ret;
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return current;
    }

    //quick coordinate boundaries check
    public boolean isOutOfBounds(int x, int y){
        if(x<0 || x>= Board.N || y<0 || y>= Board.N) {
            System.out.println("Coordinates out of bounds");
            return true;
        }
        return false;
    }
    // quick value check
    public boolean isUnknownValue(int val){
        if(!(val == 0 || val == 1 || val == 2 || val == 3)) {
            System.out.println("Value out of bounds");
            return true;
        }
        return false;
    }

    public Object clone() throws CloneNotSupportedException {
        Action a2 = (Action) super.clone();
        a2.pos = new int[2];
        a2.queen = new int[2];
        a2.arrow = new int[2];

        a2.queen[0] = this.queen[0];    a2.queen[1] = this.queen[1];
        a2.pos[0] = this.pos[0];        a2.pos[1] = this.pos[1];
        a2.arrow[0] = this.arrow[0];    a2.arrow[1] = this.arrow[1];

        return a2;
    }

    @Override
    public String toString() {
        return "Action{" +
                "queen=" + Arrays.toString(queen) +
                ", pos=" + Arrays.toString(pos) +
                ", arrow=" + Arrays.toString(arrow) +
                '}';
    }

    @Override
    public boolean equals(Object act) {
        if (this == act) return true;
        if (act == null || getClass() != act.getClass()) return false;
        Action action = (Action) act;
        return Arrays.equals(queen, action.queen) && Arrays.equals(pos, action.pos) && Arrays.equals(arrow, action.arrow);
    }
}
