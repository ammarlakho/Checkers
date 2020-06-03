package checkers;

import java.awt.Color;
import java.awt.Graphics;

public class Box {
    private int x, y, length;
    
    public Box(int x, int y) {
        this.x = x;
        this.y = y;
        length = 50;
    }
    
    public void draw(Graphics g, Color c) {
        g.setColor(c);
        g.fillRect(x, y, length, length);
    }
}
