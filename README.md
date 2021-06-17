# AmazonsProject
Game of Amazons project for COSC322

## Final classes used
- `Board`: contains one specific state of a board in a 10x10 array
- `Action`: contains one possible action using three coordinate points and allows to traverse from one board to another
- `Main`: runs the local version of algorithm against random AI
- `Node`: a state of the tree, containing information for the proper tree structure (parent, children, action) and information for MCS (value, visits, player)
- `NodesGenerator`: has a recursive method that returns array with all possible children of a given node
- `MonteCarloSearch`: contains everything related to the algorithm and decision making
