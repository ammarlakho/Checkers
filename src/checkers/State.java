package checkers;

import java.util.ArrayList;

//The class that deals with operations on one possible state in a checkers game.

public class State {
    
    private PlayerWhite p1;
    private PlayerRed p2;
    private int[][] config; //Config is an 8x8 2D array which shows where all the pieces are. 
    public enum turn {
        RED,
        WHITE
    }
    
    
    public State(PlayerWhite p1, PlayerRed p2){
        this.p1 = p1;
        this.p2 = p2;
        this.config = new int[8][8];
        setConfig();
//        jumped = false;
    }
    
    public State(int[][] config) {
        this.config = config;
        
    }
    
    /*  Pawn of P1(white)  is represented by 1.
        King of P1(white) is represented by 11.
        Pawn of P2(red) is represented by 2.
        Pawn of P2(red) is represented by 22.
        Empty is represented by 0. 
    */
    public int[][] getConfig() {
        return config;
    }

    
    

    //Updates the config by moving the piece and takes care of killing a piece too
    public void movePiece(Move move) {
        int temp = config[move.currRow][move.currCol];
        config[move.currRow][move.currCol] = 0;
        config[move.movRow][move.movCol] = temp;
        if(move.jump) {
            int deadrow = (move.currRow + move.movCol)/2;
            int deadcol = (move.currCol + move.movCol)/2;
            config[deadrow][deadcol] = 0;
        }
    }
    
    
    //Returns all the possible moves for cuurent player(pawns+kings)
    public ArrayList<Move> getAllLegalMoves(turn my_turn) {
        
        ArrayList<Move> moves = new ArrayList<>();
        if(my_turn == State.turn.WHITE) {
            int count = 0;
            for(int i=0; i<8; i++) {
                for(int j=0; j<8; j++) {
                    if(config[i][j] == 1) {
                        moves.addAll(pawnMoves(1, i, j));
                        moves.addAll(allJumps(i,j));
                        count++;
                    }
                    //King
                    if(config[i][j] == 11) {
                        moves.addAll(kingsMoves(i, j));
                        moves.addAll(allJumps(i,j));
                        count++;
                    }
                    if (count == 12)
                        return moves;
                }         
            }
            
        }
        
        if(my_turn == State.turn.RED) {
            int count = 0;
            for(int i=0; i<8; i++) {
                for(int j=0; j<8; j++) {
                    if(config[i][j] == 2) {
                        moves.addAll(pawnMoves(2, i, j));
                        moves.addAll(allJumps(i,j));
                        count++;
                    }
                    // King
                    if(config[i][j] == 22) {
                        moves.addAll(kingsMoves(i, j));
                        moves.addAll(allJumps(i,j));
                        count++;
                    }
                    if (count == 12)
                        return moves;
                }
            }
        }
        return moves;
        
    }
    
    
    /**
     * @param my_turn tells whose turn it is.
     * @return score for the player whose turn it is.
     */
    public int getScore(turn my_turn) {
        
        turn opp_turn = turn.RED;
        if(my_turn == turn.RED)
            opp_turn = turn.WHITE;
        
        int sWhite = 0;  
        int sRed = 0;
        

        for(int i=0; i<8; i++) {
            for(int j=0; j<8; j++) {
                if(config[i][j] == 1)  {
                    if(i < 4) sWhite += 5;
                    else sWhite += 7;
                }
                if(config[i][j] == 11) {
                    sWhite += 10;
                }
                if(config[i][j] == 2)  {
                    if(i < 4) sRed += 7;
                    else sRed += 5;
                }
                if(config[i][j] == 22) {
                    sRed += 10;
                }
            }
        }
        
        int s = 0;
        
        if(winner(my_turn)) return 1000;
        if(winner(opp_turn)) return -1000;
        
        if(my_turn == turn.WHITE) {
            s = sWhite - sRed;
        }
        else {
            s = sRed - sWhite;
        }
            
        return s;
    }
   
    //Returns all the moves for one pawn.
    private ArrayList<Move> pawnMoves(int player, int i, int j) {
        ArrayList<Move> moves = new ArrayList<>(); 
        
        if(player == 1) {
            if(i < 7) {
                if(j != 0) { 
                    if (config[i+1][j-1] == 0)
                        moves.add(new Move(i, j, i+1, j-1));
                }

                if(j != 7) { 
                    if (config[i+1][j+1] == 0)
                        moves.add(new Move(i, j, i+1, j+1));
                }
            }
        }
        if(player == 2) {
            if(i > 0) {
                if(j != 0) {
                if (config[i-1][j-1] == 0)
                    moves.add(new Move(i, j, i-1, j-1));
                }
               if (j != 7) {
                    if (config[i-1][j+1] == 0)
                        moves.add(new Move(i, j, i-1, j+1));
                }
            }
        }
        return moves;
    }
    
    
    //Returns all the moves of one king(same for both players)
    private ArrayList<Move> kingsMoves(int i, int j) {
        ArrayList<Move> moves = new ArrayList<>(); 
        if(i != 0 && j != 0) { 
            if (config[i-1][j-1] == 0)
                moves.add(new Move(i, j, i-1, j-1));
            }
        if(i != 0 && j != 7) {
            if (config[i-1][j+1] == 0)
                moves.add(new Move(i, j, i-1, j+1));
        }
        if(i != 7 && j != 0) { //Border check
            if (config[i+1][j-1] == 0)
                moves.add(new Move(i, j, i+1, j-1));
        }
        if(i != 7 && j != 7) { //Border check
            if (config[i+1][j+1] == 0)
                moves.add(new Move(i, j, i+1, j+1));
        }
        return moves;
    }
    
    
    //sets the initial config according to where p1 and p2's pieces are.
    private void setConfig(){ 
        ArrayList<Piece> white = p1.pieces;
        ArrayList<Piece> red = p2.pieces;
       
        for(int i=0; i<p1.pieces.size(); i++) {
            int c = (int) ((white.get(i).getX()/400.0)*8);
            int r = (int) ((white.get(i).getY()/400.0)*8);
            if(white.get(i).isKing())
                config[r][c] = 11;
            else
                config[r][c] = 1;
        }
        
        for(int i=0; i<p2.pieces.size(); i++) {
            int c = (int) ((red.get(i).getX()/400.0)*8);
            int r = (int) ((red.get(i).getY()/400.0)*8);
            if(red.get(i).isKing())
                config[r][c] = 22;
            else
                config[r][c] = 2;
        }
    }
    
    
    private ArrayList<Move> allJumps(int i, int j) {
        ArrayList<Move> jumps = new ArrayList<>();
        //Jump for any p1 piece
        if(config[i][j] == 1 || config[i][j] == 11) {
            if(i < 5){
                if(j > 1) {
                    if((config[i+1][j-1] == 2 || config[i+1][j-1] == 22) && config[i+2][j-2] == 0) 
                        jumps.add(new Move(i, j, i+2, j-2));
                    
                }
                if(j < 6){
                    if((config[i+1][j+1] == 2 || config[i+1][j+1] == 22) && config[i+2][j+2] == 0) 
                        jumps.add(new Move(i, j, i+2, j+2));    
                }
                
            }
        }
        //2 extra King jumps
        if(config[i][j] == 11) {
            if(i > 1) {
                if(j > 1) {
                    if((config[i-1][j-1] == 2 || config[i-1][j-1] == 22) && config[i-2][j-2] == 0) 
                        jumps.add(new Move(i, j, i-2, j-2));
                    
                }
                if(j < 6){
                    if((config[i-1][j+1] == 2 || config[i-1][j+1] == 22) && config[i-2][j+2] == 0) 
                        jumps.add(new Move(i, j, i-2, j+2));    
                }
            }
        }
        
        
        //Jumps for any p2 piece
        if(config[i][j] == 2 || config[i][j] == 22) {
            if(i > 1) {
                if(j > 1) {
                    if((config[i-1][j-1] == 1 || config[i-1][j-1] == 11) && config[i-2][j-2] == 0) 
                        jumps.add(new Move(i, j, i-2, j-2));
                    
                }
                if(j < 6){
                    if((config[i-1][j+1] == 1 || config[i-1][j+1] == 11) && config[i-2][j+2] == 0) 
                        jumps.add(new Move(i, j, i-2, j+2));    
                }
            }
        }
        
        // 2 extra king jumps
        if(config[i][j] == 22) {
            if(i < 5){
                if(j > 1) {
                    if((config[i+1][j-1] == 1 || config[i+1][j-1] == 11) && config[i+2][j-2] == 0) 
                        jumps.add(new Move(i, j, i+2, j-2));
                    
                }
                if(j < 6){
                    if((config[i+1][j+1] == 1 || config[i+1][j+1] == 11) && config[i+2][j+2] == 0) 
                        jumps.add(new Move(i, j, i+2, j+2));    
                }   
            }
        }
        for(int k=0; k<jumps.size(); k++) {
            jumps.get(k).jump = true;  
        }
        return jumps;
    }

    public PlayerWhite getP1() {
        return p1;
    }

    public PlayerRed getP2() {
        return p2;
    }

    public String toString() {
        String s = "";
        for(int i=0; i<config.length; i++) {
            for(int j=0; j<config[i].length; j++) {
                s += config[i][j] + " " ;
            }
            s += "\n";
        }
        s+= "\n";
        return s;
    }
    
    
    public boolean winner(turn my_turn) {
        turn opp_turn = turn.RED;
        if(my_turn == turn.RED)
            opp_turn = turn.WHITE;
        
        int oppCount = 0;
        for(int i=0; i<8; i++) {
            for(int j=0; j<8; j++) {
                if(config[i][j] == 1 || config[i][j] == 11)  {
                    if(my_turn == turn.RED) 
                        oppCount++;
                }
                if(config[i][j] == 2  || config[i][j] == 22)  {
                    if(my_turn == turn.WHITE)
                        oppCount++;
                }
            }
        }
        
        return (oppCount == 0 || this.getAllLegalMoves(opp_turn).isEmpty());      
    }
    
    
    
        
}
