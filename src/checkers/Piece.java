package checkers;

import java.awt.Color;
import java.awt.Graphics;

public class Piece {
    private int x, y, radius;
    private Color color;
    public boolean king;

    public Piece(int x, int y, Color color) {
        radius = 30;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public void drawKing(Graphics g) {

        g.setColor(Color.BLUE);
        g.fillOval(x+8, y+8, radius/2 , radius/2);
    }
    public void draw(Graphics g){
        g.setColor(color);
        g.fillOval(x, y, radius, radius);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isKing() {
        return king;
    }

    public void setKing(boolean king) {
        this.king = king;
    }

    public boolean equals(Piece p){
        return this.getX()==p.getX() && this.getY()==p.getY();
    }


}

