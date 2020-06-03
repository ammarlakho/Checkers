
package checkers;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Menu {

    public void draw(Graphics g) {
        //background
        g.drawImage(getBackgroundImage(), 0, 0, null);

        //Header
        Font f0 = new Font("arial", Font.BOLD, 30);
        g.setFont(f0);
        g.setColor(Color.WHITE);
        g.drawString("CHECKERS", 130, 60);

        //Buttons
        Font f1 = new Font("arial", Font.BOLD, 20);
        g.setFont(f1);
        g.drawRect(175, 125, 75, 40);
        g.drawString("Play", 175+15, 125+28);

        g.drawRect(175, 200, 75, 40);
        g.drawString("Rules", 175+12, 200+28);

        g.drawRect(175, 275, 75, 40);
        g.drawString("Quit", 175+15, 275+28);

        //Footer
        Font f2 = new Font("arial", Font.BOLD, 16);
        g.setFont(f2);
        g.drawString("Developers: ", 300, 350);
        g.drawString("Haris Zafar", 300, 370);
        g.drawString("Ammar Lakho", 300, 390);

    }

    public Image getBackgroundImage() {
        ImageIcon a = new ImageIcon(getClass().getResource("f.jpg"));
        return a.getImage();
    }
}
