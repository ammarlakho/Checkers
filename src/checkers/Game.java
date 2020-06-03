package checkers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Game implements MouseListener {

    private PlayerWhite p1;
    private PlayerRed p2;
    
    public enum STATE {
        MENU, PLAY, RULES };
    public static STATE state = STATE.MENU;
    
    public Game() {
    }
    
    public Game(PlayerWhite p1, PlayerRed p2) {
//        Scanner in1 = new Scanner(System.in);
//        if(in1 == 0) {
            
//        }
        this.p1 = p1;
        this.p2 = p2;
        p1.setP2(p2);
        p2.setP1(p1);   
        
    }
    
    
    
    
    @Override
    public void mouseClicked(MouseEvent e) {
        int mx = e.getX() - 8;
        int my = e.getY() - 31;
        
        if(state == STATE.MENU) {
            if(mx>=175 && mx<=250) {
                if(my>=125 && my<=165) {
                    state = STATE.PLAY;
                }
                else if(my>=200  && my<= 240) { 
                    state = STATE.RULES;
                }
                else if(my>=275 && my<= 315) {
                    System.exit(0);
                }
            }
        }
        else if(state == STATE.RULES) {
            if(mx>=20 && mx<=80) {
                if(my>=20 && my<=50) {
                    state = STATE.MENU;
                }
            }
        }
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
}