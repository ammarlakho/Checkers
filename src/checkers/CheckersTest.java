package checkers;

import javax.swing.JFrame;

public class CheckersTest {
    
    public static void main(String[] args) {
        /*
        Three types of player: human, smart(AI), random
        To create a player you need to specify its type as described below 
        and give a number as the max depth for the minimax algo. 
        In case of human/random player this number doesnt matter. 
        */
        PlayerWhite p1 = new PlayerWhite(PlayerWhite.type.human, 4);
        PlayerRed p2 = new PlayerRed(PlayerRed.type.smart, 5);
        Game game = new Game(p1, p2);
        
        JFrame frame = new JFrame("Checkers");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 450);
        frame.setLocationRelativeTo(null);
        frame.setFocusable(true);
        frame.addMouseListener(p1);
        frame.addMouseListener(p2);
        frame.addMouseListener(game);
        frame.addKeyListener(p1);
        frame.addKeyListener(p2);
        frame.add(new Board(p1, p2));
        
    }
    
}
