package checkers;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Rules {
    
    public void draw(Graphics g) {
        if(Game.state == Game.STATE.RULES) {
            //background
            g.drawImage(getBackgroundImage(), 0, 0, null);
            
            //Header
            Font f0 = new Font("arial", Font.BOLD, 30); 
            g.setFont(f0);
            g.setColor(Color.WHITE); 
            g.drawString("RULES", 160, 50);
            
            //Back button
            Font f1 = new Font("arial", Font.BOLD, 20);
            g.setFont(f1);
            g.drawString("Back", 27, 42);
            g.drawRect(20, 20, 60, 30);
            
            //Rules
            Font f2 = new Font("georgia", Font.BOLD, 15);
            g.setFont(f2);
            g.drawString("1. All pieces initially move diagonally forward.", 10, 90);
            
            g.drawString("2. A piece becomes a king if it reaches the end of the", 10, 110);
            g.drawString("board. It can now move diagonally backwards as well.", 11, 125);
            
            g.drawString("3. To make a move(according to game agent or random ",10, 145);
            g.drawString("agent), click anywhere on the screen.", 11, 160);
            
            g.drawString("4. To move a pawn(human) you need to: ", 10, 180);
            g.drawString("(a) Click on the piece you wish to move.", 20, 195);
            g.drawString("(b) Choose the right or left arrow.", 20, 210);
            
            g.drawString("5. To move a king, after clicking on it, you need to", 10, 230);
            g.drawString("type the following on NUMPAD:", 11, 245);
            
            g.drawString("(a) 1 for bottom left", 15, 265);
            g.drawString("(b) 3 for bottom right", 15, 280);
            g.drawString("(c) 7 for top left", 15, 295);
            g.drawString("(d) 9 for top right", 15, 310);
            
            g.drawString("6. You capture an opponent's piece if you jump", 10, 330);
            g.drawString("across it. You also get an extra move for that.", 11, 345);
            
            
            g.drawString("7. A player wins when they capture all the opponent's ", 10, 365);
            g.drawString("pieces or the opponent has no possible moves.", 11, 380);
        }
    }
  
    public Image getBackgroundImage() {
        ImageIcon a = new ImageIcon(getClass().getResource("f.jpg"));
        return a.getImage();
    }

}
