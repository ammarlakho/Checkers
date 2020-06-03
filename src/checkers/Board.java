package checkers;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Board extends JPanel {
    
    private Box[][] board;
    private PlayerWhite p1;
    private PlayerRed p2;
    
    
    public Board(PlayerWhite p1, PlayerRed p2) {
        board = new Box[8][8];
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                board[i][j] = new Box(50*i, 50*j);
            }
        }
        this.p1 = p1;
        this.p2 = p2;
           
    }
    
    public void getJumps() {
        
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(Game.state == Game.STATE.PLAY) {
            for(int i = 0; i < board.length; i++) {
                for(int j = 0; j < board.length; j++) {
                    if(i%2 == j%2)
                        board[i][j].draw(g, Color.GRAY);
                    else {
                        board[i][j].draw(g, Color.BLACK);
                    }
                }

            }

           p1.drawAll(g);
           p2.drawAll(g);

        }
        else if(Game.state == Game.STATE.MENU) {
            Menu menu = new Menu();;
            menu.draw(g);
        }
        else if(Game.state == Game.STATE.RULES) {
            Rules rules = new Rules();
            rules.draw(g);
        }
        repaint();
        
    }

}
