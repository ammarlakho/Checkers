package checkers;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Node {
    private State state;
    private Move move;
    private int score;
    private ArrayList<Node> children;

    /**
     * Creates a new node with state as the head, a move, a score, and children
     * @param state the state of the node 
     * @param move the move that led to this state
     * @param score the score of the state of the node
     * @param children the children of the node
     */
    public Node(State state, Move move, int score, Node ... children) {
        this.state = state;
        this.children = new ArrayList<>(Arrays.asList(children));
        this.score = score;
        this.move = move;
    }
    
    public Node() {  
    }

    
    public State getState() {return state;}

   
    public Move getMove() {
        return move;
    }

    public int getScore() {
        return score;
    }

    public List<Node> getChildren() {
        return children;
    }

    public int getNumChildren() {
        return children.size();
    }

    public void setScore(int newVal) {
        score = newVal;
    }

    public Node getChild(int index) {
        return children.get(index);
    }

    public void addChild(Node child) {
        children.add(child);
    }

    public void addChildren(Node ... children) {
        for (Node child : children) {
            addChild(child);
        }
    }
    
    /**
     * @return a string version of a node and all of its children using BFS.
     */
    public String toString() {
        String string = "";
        ArrayDeque<Node> queue = new ArrayDeque<>();
        queue.add(this);
        int j=0;
        while(!queue.isEmpty()) {
            Node popped = queue.pop();
            string += j + ": \n";
            
            string += popped.getMove() + "\n";
            string += popped.state.toString();
            j++;
            for(int i=0; i<popped.children.size(); i++) {
                queue.add(popped.children.get(i));
            }
        }
        
        return string;
    }
}
