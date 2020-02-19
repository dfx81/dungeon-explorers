// Menu.java - Author: Danial
// A simple menu using the Engine

// requires JLabelto edit display
import javax.swing.JLabel;

class Menu
{
    // mainMenu method detects keypress from Engine and updates the
    // display.
    // it returns the selected mode as int to be handled by the Engine
    int mainMenu(Engine eng)
    {
        boolean selected = false;
        int hover = 0;
        String screen;
        
        // keep changing hover state while user didn't press select key
        while (!selected)
        {
            // there's 3 hover states
            // 0 = hover on play
            // 1 = hover on scores
            // 2 = hover on exit
            
            // only up & down key used to navigate.
            // the hover states can loop back to top or
            // bottom of the hoverable list
            if (eng.up == 1)
            {
                eng.up = 2;
                hover = (hover == 0) ? 2 : hover - 1;
            }
            
            else if (eng.down == 1)
            {
                eng.down = 2;
                hover = (hover == 2) ? 0 : hover + 1;
            }
            
            // check if select key pressed
            else if (eng.select == 1)
            {
                eng.select = 2;
                selected = true;
            }
            
            // update the screen String
            screen  = "<html>";
            screen += "DUNGEON EXPLORER<br/><br/>";
            screen += "Collect coins($)<br/>";
            screen += "Avoid Ghost(#)<br/>";
            screen += "Exit Dungeon(&gt;)<br/><br/>";
            
            switch (hover)
            {
                case 0:
                    screen += "@ Play Game<br/>";
                    screen += "- Scores<br/>";
                    screen += "- Exit<br/>";
                    break;
                case 1:
                    screen += "- Play Game<br/>";
                    screen += "@ Scores<br/>";
                    screen += "- Exit<br/>";
                    break;
                case 2:
                    screen += "- Play Game<br/>";
                    screen += "- Scores<br/>";
                    screen += "@ Exit<br/>";
                    break;
                default:
                    screen += "<bold>ERROR</bold><br/>";
            }
            
            screen += "</html>";
            
            // set screen string as updated display
            eng.txt.setText(screen);
        }
        
        // return the current hovered state
        // determines the mode selected
        return hover;
    }
}