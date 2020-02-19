// Engine.java - Author: Danial
// Handle the input/output

// import Swing components
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// implement the KeyListener interface
// need to override 3 methods
class Engine implements KeyListener
{
    // get these to check input
    // 0 = not pressed, 1 = pressed
    // 2 = press event handled
    int up = 0;
    int right = 0;
    int down = 0;
    int left = 0;
    int select = 0;
    
    int shootUp = 0;
    int shootRight = 0;
    int shootDown = 0;
    int shootLeft = 0;
    
    // used to set the initial size of JLabel
    String clear;
    
    // the main ui component itself
    JFrame frame;
    JPanel panel;
    JLabel txt;
    
    // constructor for Engine
    // initializes and create the game window
    public Engine()
    {
        clear  = "<html>";
        clear += "#######################<br/>";
        clear += "#######################<br/>";
        clear += "#######################<br/>";
        clear += "#######################<br/>";
        clear += "#######################<br/>";
        clear += "#######################<br/>";
        clear += "#######################<br/>";
        clear += "#######################<br/>";
        clear += "#######################<br/>";
        clear += "#######################<br/></html>";
        
        frame = new JFrame("Dungeon Explorer");
        panel = new JPanel();
        frame.addKeyListener(this);
        txt = new JLabel(clear);
        txt.setFont(new Font("consolas", Font.PLAIN, 32));
        txt.setForeground(new Color(0xff1818df));
        txt.setBackground(new Color(0xff000000));
        txt.setHorizontalAlignment(JLabel.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(320, 320);
        frame.setLocationRelativeTo(null);
        panel.setBackground(new Color(0xff8181ff));
        panel.add(txt);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.requestFocus();
    }
    
    public static void main(String[] args) throws Exception // not handling exceptions
    {
        // create engine instance
        Engine eng = new Engine();
        int mode = -1;
        
        // display menu and run appropriate method depending on mode
        while (mode != 2) // mode = 2 means exit
        {
            mode = new Menu().mainMenu(eng);
            
            if (mode == 0) new Game().start(eng); // run start method in Game class
            if (mode == 1) new Scores().showScores(eng); // run showScores method in Score class
        }
        
        System.exit(0);
    }
    
    // required overides
    @Override
    public void keyPressed(KeyEvent evt) // detect keypress
    {
        //System.out.println(evt.getKeyChar()); <-- for debugging
        if ((evt.getKeyCode() == KeyEvent.VK_UP) && up == 0)
            up = 1;
        if ((evt.getKeyCode() == KeyEvent.VK_RIGHT) && right == 0)
            right = 1;
        if ((evt.getKeyCode() == KeyEvent.VK_DOWN) && down == 0)
            down = 1;
        if ((evt.getKeyCode() == KeyEvent.VK_LEFT) && left == 0)
            left = 1;
        if ((evt.getKeyCode() == KeyEvent.VK_ENTER) && select == 0)
            select = 1;
        
        if ((evt.getKeyCode() == KeyEvent.VK_W) && shootUp == 0)
            shootUp = 1;
        if ((evt.getKeyCode() == KeyEvent.VK_D) && shootRight == 0)
            shootRight = 1;
        if ((evt.getKeyCode() == KeyEvent.VK_S) && shootDown == 0)
            shootDown = 1;
        if ((evt.getKeyCode() == KeyEvent.VK_A) && shootLeft == 0)
            shootLeft = 1;
    }

    @Override
    public void keyReleased(KeyEvent evt) // detect keyreleased
    {
        if (evt.getKeyCode() == KeyEvent.VK_UP)
            up = 0;
        if (evt.getKeyCode() == KeyEvent.VK_RIGHT)
            right = 0;
        if (evt.getKeyCode() == KeyEvent.VK_DOWN)
            down = 0;
        if (evt.getKeyCode() == KeyEvent.VK_LEFT)
            left = 0;
        if (evt.getKeyCode() == KeyEvent.VK_ENTER)
            select = 0;
        
        if (evt.getKeyCode() == KeyEvent.VK_W)
            shootUp = 0;
        if (evt.getKeyCode() == KeyEvent.VK_D)
            shootRight = 0;
        if (evt.getKeyCode() == KeyEvent.VK_S)
            shootDown = 0;
        if (evt.getKeyCode() == KeyEvent.VK_A)
            shootLeft = 0;
    }
  
    @Override
    public void keyTyped(KeyEvent evt) // no use in this program but required override
    {
        // Do nothing
    }
}