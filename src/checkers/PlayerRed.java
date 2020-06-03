package checkers;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class PlayerRed implements MouseListener, KeyListener {
    private final Color c;
    private Piece movepiece;
    public ArrayList<Piece> pieces;
    private Piece infront, myinfront, infront2, myinfront2;
    private int col , kingrow;
    private PlayerWhite p1;
    
    private Move latestMove;
    private int depth;
    private type type;
    public enum type {
        human,
        random,
        smart
    }
    
    
    public PlayerRed(type type, int depth) {
        super();
        c = Color.RED;
        pieces = new ArrayList<>();
        generatePieces();
        this.type = type;
        this.depth = depth;
        //Initialized so that latestMove != null for the first move. Will be overwritten after that.
        latestMove = new Move(0,0,0,0);
    }
    
    public void setP1(PlayerWhite p1) {
        this.p1 = p1;
    }
    
    
    public void generatePieces() {
        for(int i=0; i<8; i++){
            if(i%2!=0) {
                pieces.add(new Piece(10+50*i, 10+50*6, c));
             
           }
            else{
                pieces.add(new Piece(10+50*i, 10+50*5, c));
                pieces.add(new Piece(10+50*i,10+50*7, c));
            }
        }    
    }
    
    
    public void drawAll(Graphics g) {
        for(int i=0; i<pieces.size(); i++) {
            if(pieces.get(i).isKing()) {
                
                pieces.get(i).draw(g);
                pieces.get(i).drawKing(g);
            }
            else {
                pieces.get(i).draw(g);
            } 
        }
        
        if(p1.pieces.isEmpty()) {
            drawWin(g);
        }
        if(latestMove == null) {
            PlayerWhite.drawWin(g);
        }
    }
    
    public static void drawWin(Graphics g) {
        g.setColor(Color.BLUE);
        Font f0 = new Font("arial", Font.BOLD, 40);
        g.setFont(f0);
        g.drawString("GAME OVER!", 65, 190);
        g.setFont(f0);
        g.setColor(Color.BLUE);
        String s = "Red wins";
        g.drawString(s, 90, 240);
        
    }
        
    public boolean checkMove() {
        boolean flag = true;
        
            for(int i=0; i<pieces.size(); i++) { //My piecce ahead
                if(pieces.get(i).equals(myinfront)) {
                    flag = false;
                }
            }
            for(int i=0; i<p1.pieces.size(); i++) { //Opponent's piece ahead
                if(p1.pieces.get(i).equals(infront)) {
                    flag = false;
                }
            }
            if((col==1 && movepiece.getX()==360) || (col==-1 && movepiece.getX()==10)) { //Borders
                flag = false;
            }
            if(kingrow==1 && movepiece.getY()==360 || kingrow==-1 && movepiece.getY()==10) {
                flag = false;
            }
            
        return flag;
    }
    
    public boolean checkJump() {
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        boolean flag4 = false;
        
        for(int i=0; i<pieces.size();i++) {
            if(pieces.get(i).equals(myinfront2)) {
                flag1 = true;
            }
        }
        for(int i=0; i<p1.pieces.size(); i++) {
            if(p1.pieces.get(i).equals(infront2)) {
                flag2 = true; 
            }
            if((p1.pieces.get(i).equals(infront))) { 
               flag3 = true; 
            }
        }
        if(infront2.getX()>360 || infront2.getX()<10 || infront2.getY()>360 || infront2.getY()<10) {
            flag4 = true;
        }
        
        return (!flag1 && !flag2 && flag3 && !flag4);
        }
        
    
    public void makeMove(int col){
        if(col ==1 || col == -1) {
            infront = new Piece(movepiece.getX()+50*col,movepiece.getY()-50, Color.WHITE);
            infront2 = new Piece(movepiece.getX()+2*50*col,movepiece.getY()-2*50, Color.WHITE);
            myinfront = new Piece(movepiece.getX()+50*col,movepiece.getY()-50, c);
            myinfront2 = new Piece(movepiece.getX()+2*50*col,movepiece.getY()-2*50, c);
            if(checkJump()) {
                makeJump(col);
            }
            else {
                if(checkMove()) {
                    for(int i=0; i<pieces.size(); i++) {
                        if(pieces.get(i).equals(movepiece)) {
                            pieces.remove(i);
                            pieces.add(myinfront);
                        }
                    }
                    p1.turn++;
                }
           }
            checkKing();
        }
    }
        
    public void makeJump(int col){
        for(int i=0; i<pieces.size(); i++) {
            if(pieces.get(i).equals(movepiece)) {
                for(int j=0; j<p1.pieces.size(); j++) {
                    if(p1.pieces.get(j).equals(infront)) {
                        pieces.remove(i);
                        pieces.add(myinfront2);     
                        p1.pieces.remove(j);
                    }
                }
            }
        }
        checkKing();        
    }
    
    public void checkKing() {
        for(int i=0; i<pieces.size(); i++) {
            if(!pieces.get(i).isKing()) {
                if(pieces.get(i).getY()==10) {
                    pieces.get(i).setKing(true); 
                }
            }
        }
    }
     
    public void moveKing(int col, int kingrow) {
        if((col == 1 || col == -1) && (kingrow == 1 || kingrow == -1)) {  
            myinfront = new Piece(movepiece.getX()+50*col, movepiece.getY()+50*kingrow, c);
            myinfront.setKing(true);
            infront = new Piece(movepiece.getX()+50*col, movepiece.getY()+50*kingrow, Color.RED);
            myinfront2 = new Piece(movepiece.getX()+2*50*col, movepiece.getY()+2*50*kingrow, c);
            myinfront2.setKing(true);
            infront2 = new Piece(movepiece.getX()+2*50*col, movepiece.getY()+2*50*kingrow, Color.WHITE);
            if(checkJump()) {
                makeKingJump(col, kingrow);
            }
            else {
                if(checkMove()) {
                    for(int i=0; i<pieces.size(); i++) {
                        if(pieces.get(i).equals(movepiece)) {
                            pieces.remove(i);
                            pieces.add(myinfront);
                        }
                    }
                    p1.turn++;
                }

            }
        }
      }
        
    public void makeKingJump(int col, int kingrow){
        for(int i=0; i<pieces.size(); i++) {
            if(pieces.get(i).equals(movepiece)) {
                for(int j=0; j<p1.pieces.size(); j++) {
                    if(p1.pieces.get(j).equals(infront)) {
                        pieces.remove(i);
                        pieces.add(myinfront2);     
                        p1.pieces.remove(j);
                    }
                }
            }
        }        
    }
    
    /**
     *If player type is human, this method lets the player choose the piece it wants to move.
     *If player type is smart, this method makes the best possible move according to the ai.
     *If player type is random, this method makes a random move out of all the possible moves.
    **/
    @Override
    public void mouseClicked(MouseEvent e) {
        if(Game.state == Game.STATE.PLAY) {
            int mx = e.getX() - 8;
            int my = e.getY() - 31;
            State state = new State(p1, this);
            ArrayList<Move> moves = state.getAllLegalMoves(State.turn.RED);

            //No possible moves you have lost
            if(moves.isEmpty()) 
                latestMove = null;
            
            else {
                if(p1.turn%2 == 1) {
                    if(this.type == type.human) {
                        movepiece = new Piece(10+50*(mx/50), 10+50*(my/50), c);
                    }

                    else {
                        if(this.type == type.smart) {
                            AI_Move ai_agent = new AI_Move(State.turn.RED); 
                            latestMove = ai_agent.getAIMove(state, depth);
                        }

                        if(this.type == type.random) {
                            int random = (int)(Math.random()*moves.size());
                            latestMove = moves.get(random);
                        }
                            
                        int crow = latestMove.currRow;
                        int ccol = latestMove.currCol;
                        int mrow = latestMove.movRow;
                        int mcol = latestMove.movCol;
                        col = mcol - ccol;
                        kingrow = mrow - crow;


                        movepiece = new Piece(10+50*ccol, 10+50*crow, c);
                        for(int i=0; i<pieces.size(); i++) {

                            if(pieces.get(i).equals(movepiece)) {
                                if(col > 0 ) col = 1;
                                else col = -1;
                                if (kingrow > 0) kingrow = 1;
                                else kingrow = -1;

                                if(pieces.get(i).isKing()) {
                                    moveKing(col, kingrow);

                                }
                                else {
                                    makeMove(col);
                                }
                            }
                        }

                        col = 0;
                        kingrow = 0;
                        movepiece = null;

                    }
                } 
            }
        }     
    }
           
    //If it's a human player, the following method lets the player go right/left depending on keyboard input.
    @Override
    public void keyPressed(KeyEvent e) {
        if(this.type == type.human) {
            if(Game.state == Game.STATE.PLAY && movepiece != null) {
                if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    col = 1;
                }
                if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                    col = -1;

                } 
                if(e.getKeyCode() == KeyEvent.VK_NUMPAD9) {
                    kingrow = -1;
                    col = 1;
                }
                if(e.getKeyCode() == KeyEvent.VK_NUMPAD7) {
                    kingrow = -1;
                    col = -1;
                }
                if(e.getKeyCode() == KeyEvent.VK_NUMPAD3) {
                    kingrow = 1;
                    col = 1;
                }
                if(e.getKeyCode() == KeyEvent.VK_NUMPAD1) {
                    kingrow = 1;
                    col = -1;
                }
                
                for(int i=0; i<pieces.size(); i++) {
                    if(pieces.get(i).equals(movepiece)) {
                        if(pieces.get(i).isKing()) {
                            moveKing(col, kingrow);

                        }
                        else {
                            makeMove(col);
                        }
                    }
                }
                
            }
            col = 0;
            kingrow = 0;
            movepiece = null;
        }
    }

        
    @Override
    public void keyReleased(KeyEvent e) {
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    

}
