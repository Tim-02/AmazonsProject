import java.util.ArrayList;

public class TreeGen {
    //for that access
    ActionGenerator aGen = new ActionGenerator();
    ArrayList<Action> temp = new ArrayList();

    //for the nodes
    int value;
    TreeGen node;
    public int depth = 3;

    //constructors

    TreeGen(){}

    TreeGen(int value){
        this.value = value;
        node = null;
    }

    //methods

    //need position of node, depth and what player)
    //using depth as a hard counter for now
    public void allMoves(Board future, int player, Node parent){
        //check for each child of the node by calling the function again
        if (future == null){
            new TreeGen(value);
        }

        //if(parent == null)
          //TO DO, fix parent node situation, make them link up correctly

        temp = aGen.generate(future, player);

        for(Action act : temp){
            try { Node n = new Node(act.performReturn(future));
                parent.children.add(n);
            }catch(Exception e){ System.out.println("Error in allMoves, cloning board");}


        }


        //todo:
        // -compare current depth to max depth(currently hard coded)
        // -recurse into next child and log it into a list
        // -list has to be traversed by the AI




        depth --;
    }
}
