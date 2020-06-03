package checkers;
import java.util.ArrayList;

//The class that deals with creating a game tree and applying minimax on that tree to pick the best move possible.

public class AI_Move {
    
    private State.turn aiTurn;
    private State.turn oppTurn;
    private int t;
    private Node descisionTree;
    private Move lastMove;
    
    public AI_Move(State.turn turn) {
        this. t = 0; //Used in deciding whose turn it is in makeDecisionTree method
        this.aiTurn = turn;
        if (turn == State.turn.RED) {
            oppTurn = State.turn.WHITE;
        } else {
            oppTurn = State.turn.RED;  
        } 
    }

    /**
     * Picks its move based on all possible moves
     * @param State the current state of the State
     * @param x is the depth
     * @return the move picked by the ai
     */
    public Move getAIMove(State State, int x) {
        descisionTree = makeDecisionTree(State, x, this.t, null);
        lastMove = pickMove(descisionTree);
        return lastMove;
    }

    
    /**
     * Recursively generates a tree for (depth) no. of moves of the game
     * @param state the initial State that the tree will be based on
     * @param t handles switching turns
     */
    private Node makeDecisionTree(State state, int depth, int t, Move m) {
        Node root;
            root = new Node(state, m, score(state));
        
        if(depth != 0) {
            ArrayList<Move> moves;
            // Initially we always keep t 0 so first move will be the for the one we choose as AI. And then it will keep changing.
            if(t%2==0) {
                moves = state.getAllLegalMoves(aiTurn);
            }
            else {
                moves = state.getAllLegalMoves(oppTurn);
            }
            
            for(int i=0; i<moves.size(); i++) {
                Move currentMove = moves.get(i);
                State childState = copyState(state);
                childState.movePiece(currentMove);

                Node child = new Node(childState, currentMove, score(childState));
                if(currentMove.isJump()) //If you make a jump, you get an extra turn
                    child = makeDecisionTree(childState, depth-1, t, currentMove);
                else //else opponent's turn
                    child = makeDecisionTree(childState, depth-1, t+1, currentMove);
                
                root.addChild(child);
            }
        }
        return root;
    }        

    
    /**
     * Recursively applies minimax algorithm on the game tree.
     * @param node is the game tree.
     * @param maxiPlayer handles switching turns
     */
    private Node minimax(Node node, boolean maxiPlayer){
        if(node.getChildren().isEmpty()){
            return node;
        }
        else{
            Node point = new Node();
            if(maxiPlayer){
                int max = -99999999;
                for(Node sChild : node.getChildren()){
                    if(sChild.getScore()>max){
                        point=minimax(sChild,!maxiPlayer);
                        if(sChild.getScore()<point.getScore()){
                            max=point.getScore();
                        }
                        else{
                            max=sChild.getScore();
                            point=sChild;
                        }
                    }

                }
            }
            else{
                int min = 99999999;
                for(Node sChild : node.getChildren()){
                    if(sChild.getScore()<min){
                        point=minimax(sChild,!maxiPlayer);
                        if(sChild.getScore()>point.getScore()){
                            min=point.getScore();
                        }
                        else{
                            min=sChild.getScore();
                            point=sChild;
                        }
                    }

                }
            }
            return point;
        }
    }
    

    
    private Move pickMove(Node T) {
        T=minimax(T,true);
        return T.getMove();
    }


    private int score(State state) {
        return state.getScore(aiTurn);
    }

    /**
     * Creates a new State with the same information as the given State
     * @param State the State that will be copied
     * @return a copy of the given State
     */
    private State copyState(State state) {
        int[][] og = state.getConfig();
        int[][] copy = new int[og.length][og[0].length];
        for(int i=0; i<og.length; i++) {
            for(int j=0; j<og[i].length; j++) {
                copy[i][j] = og[i][j];
            }
        }
        return new State(copy);

    }
    
    
    
    
    
}
