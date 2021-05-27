public class TreeGen {
    //for that access
    ActionGenerator aGen = new ActionGenerator();

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
    public void allMoves(Board future, int player){
        //check for each child of the node by calling the function again
        if (future == null){
            new TreeGen(value);
        }


        //todo:
        // -compare current depth to max depth(currently hard coded)
        // -recurse into next child and log it into a list
        // -list has to be traversed by the AI




        depth --;
    }
}
