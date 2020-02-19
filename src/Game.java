// Game.java - Author: Danial, Afirudin, Shamil
// Main Game coding by Shamil. Ported to the Engine by Danial
// The game code itself

// requires JLabel to edit the text display
import javax.swing.JLabel;

class Game
{
    // start the game. requires Engine to get input
    // and output
    void start(Engine eng)
    {
        // define all tiles
        final char WALL = (char) 0x2588;
        final char FLOOR = (char) 0x2591;
        final char BROKEN_1 = (char) 0x2593;
        final char BROKEN_2 = (char) 0x2592;
        final char PLAYER = '@';
        final char BLOCK = (char) 0x2580;
        final String VOID = "&nbsp;"; // non-breaking space in html
        final char ENEMY = '#';
        final String START = "&lt;"; // less than in html
        final String GOAL = "&gt;"; // greater than in html
        final char COIN = '$';
        final char BULLET_V = '|';
        final char BULLET_H = '-';
        
        // declare/initialize some constants
        final int MAP_W = 32;
        final int MAP_H = 32;

        final int START_X;
        final int START_Y;
      
        final int GOAL_X;
        final int GOAL_Y;
        
        // create target for the enemy
        int targetX = 0;
        int targetY = 0;
        
        // ghostTimer marks the hunting period
        double ghostTimer = 10;
        double targetTimer = ghostTimer;
        boolean hunt = true;
        
        // default enemy speed set to 1 tile/second
        double enemySpeed = 1.00;
        int score = 0;
      
        //System.out.print("\r20%"); <-- debugging
        
        // initialize the map with a map from the Gen's generate method
        char[] map = new Gen().generate();

        int playerX = 0;
        int playerY = 0;
        
        boolean shoot = false;

        // randomly pick a legal starting spot for player
        do
        {
            playerX = (int) (1 + Math.random() * (MAP_W - 1));
            playerY = (int) (1 + Math.random() * (MAP_H - 1));
        }
        while (map[playerX + playerY * MAP_W] != FLOOR);
    
        START_X = playerX;
        START_Y = playerY;

        //System.out.print("\r40%"); <-- debugging

        int gx, gy;
        boolean pass = false;

        // randomly pick a random legal spot for the goal
        do
        {
            gx = (int) (1 + Math.random() * (MAP_W - 1));
            gy = (int) (1 + Math.random() * (MAP_H - 1));
        
            if (map[gx + gy * MAP_W] == FLOOR && (gx >= playerX + MAP_W / 4 || gx < playerX - MAP_W / 4) && (gy >= playerY + MAP_H / 4 || gy < playerY - MAP_H / 4))
                pass = true;
        }
        while (!pass);
      
        GOAL_X = gx;
        GOAL_Y = gy;

        //System.out.print("\r60%"); <-- debugging
        
        int coinX = 0;
        int coinY = 0;
        pass = false;
        
        // randomly pick a random legal spot for the coin
        // Author: Afirudin
        do
        {
            coinX = (int) (1 + Math.random() * (MAP_W - 1));
            coinY = (int) (1 + Math.random() * (MAP_H - 1));
            
            if (map[coinX + coinY * MAP_W] == FLOOR && (coinX >= playerX + MAP_W / 4 || coinX < playerX + MAP_W / 4) && (coinY >= playerY + MAP_H / 4 || coinY < playerY - MAP_H / 4) && (coinX != GOAL_X && coinY != GOAL_Y))
                pass = true;
        }
        while (!pass);
        
        //System.out.print("\r80%"); <-- debugging
      
        int enemyX = 0;
        int enemyY = 0;

        pass = false;

        // randomly pick a random legal spot for the enemy starting coordinate    
        do
        {
            enemyX = (int) (1 + Math.random() * (MAP_W - 1));
            enemyY = (int) (1 + Math.random() * (MAP_H - 1));
        
            if (map[enemyX + enemyY * MAP_W] == FLOOR && (enemyX >= playerX + 10 || enemyX < playerX - 10) && (enemyY >= playerY + 10 || enemyY < playerY - 10)) pass = true;
        }
        while (!pass);

        //System.out.print("\r100%"); <-- debugging

        boolean running = true;

        String screen = "";
        
        // initialize timeLast for timing purposes
        double timeLast = System.nanoTime() / Math.pow(10, 9), timeNow = 0, timeElapsed = 0;
        
        // game loop
        while (running)
        {
            // checks how much time passed
            timeNow = System.nanoTime() / Math.pow(10, 9);
            timeElapsed += timeNow - timeLast;
            
            // countdown until hunting mode is over
            targetTimer -= timeNow - timeLast;
            
            // stop hunting if hunting period ends
            if (targetTimer <= 0)
            {
                hunt = false;
            }
            
            // target player if hunting or move to the exit
            if (hunt)
            {
                targetX = playerX;
                targetY = playerY;
            }
            else
            {
                targetX = GOAL_X;
                targetY = GOAL_Y;
            }
            
            // for timing purposes
            timeLast = timeNow;
            
            // check input. move the player if necessary
            // Author: Shamil
            
            // break walls by changing the tile collided with to another tile.
            // up to three different walls exists
            // Author: Afirudin
            if (eng.up == 1 && (playerY != 1))
            {
                if (map[playerX + (playerY - 1) * MAP_W] == FLOOR)
                {
                    playerY--;
                    eng.up = 2;
                }
                else if (map[playerX + (playerY - 1) * MAP_W] != BLOCK)
                {
                    if (map[playerX + (playerY - 1) * MAP_W] == WALL) map[playerX + (playerY - 1) * MAP_W] = BROKEN_1;
                    else if (map[playerX + (playerY - 1) * MAP_W] == BROKEN_1) map[playerX + (playerY - 1) * MAP_W] = BROKEN_2;
                    else map[playerX + (playerY - 1) * MAP_W] = FLOOR;
                    
                    eng.up = 2;
                }
            }
            else if (eng.up == 1) eng.up = 2;
            
            if (eng.right == 1 && (playerX != MAP_W - 2))
            {
                if (map[(playerX + 1) + playerY * MAP_W] == FLOOR)
                {
                    playerX++;
                    eng.right = 2;
                }
                else if (map[(playerX + 1) + playerY * MAP_W] != BLOCK)
                {
                    if (map[(playerX + 1) + playerY * MAP_W] == WALL) map[(playerX + 1) + playerY * MAP_W] = BROKEN_1;
                    else if (map[(playerX + 1) + playerY * MAP_W] == BROKEN_1) map[(playerX + 1) + playerY * MAP_W] = BROKEN_2;
                    else map[(playerX + 1) + playerY * MAP_W] = FLOOR;
                    
                    eng.right = 2;
                }
            }
            else if (eng.right == 1) eng.right = 2;
            
            if (eng.down == 1 && (playerY != MAP_H - 2))
            {
                if (map[playerX + (playerY + 1) * MAP_W] == FLOOR)
                {
                    playerY++;
                    eng.down = 2;
                }
                else if (map[playerX + (playerY + 1) * MAP_W] != BLOCK)
                {
                    if (map[playerX + (playerY + 1) * MAP_W] == WALL) map[playerX + (playerY + 1) * MAP_W] = BROKEN_1;
                    else if (map[playerX + (playerY + 1) * MAP_W] == BROKEN_1) map[playerX + (playerY + 1) * MAP_W] = BROKEN_2;
                    else map[playerX + (playerY + 1) * MAP_W] = FLOOR; 
                    
                    eng.down = 2;
                }
            }
            else if (eng.down == 1) eng.down = 2;
            
            if (eng.left == 1 && (playerX != 1))
            {
                if (map[(playerX - 1) + playerY * MAP_W] == FLOOR)
                {
                    playerX--;
                    eng.left = 2;
                }
                else if (map[(playerX - 1) + playerY * MAP_W] != BLOCK)
                {
                    if (map[(playerX - 1) + playerY * MAP_W] == WALL) map[(playerX - 1) + playerY * MAP_W] = BROKEN_1;
                    else if (map[(playerX - 1) + playerY * MAP_W] == BROKEN_1) map[(playerX - 1) + playerY * MAP_W] = BROKEN_2;
                    else map[(playerX - 1) + playerY * MAP_W] = FLOOR;
                    
                    eng.left = 2;
                }
            }
            else if (eng.left == 1) eng.left = 2;
            
            boolean hit = true;
            
            // check if the player shoots. also check if
            // it hits the enemy. increase the delay
            // between enemy movement up to 2 seconds
            // check for all 4 directions
            // > created by Shamil
            // > modified by Danial
            if (eng.shootUp == 1 && score >= 2)
            {
                if (enemyX == playerX && enemyY >= playerY - 4)
                {
                    for (int y = playerY; y != enemyY; y--)
                    {
                        if (map[playerX + y * MAP_W] != FLOOR) hit = false;
                    }
                    
                    if (hit) timeElapsed -= (timeElapsed < -2) ?  0 : 1;
                }
                
                shoot = true;
                eng.shootUp = 2;
            }
            
            if (eng.shootRight == 1 && score >= 2)
            {
                if (enemyY == playerY && enemyX < playerX + 9)
                {
                    for (int x = playerX; x != enemyX; x++)
                    {
                        if (map[x + playerY * MAP_W] != FLOOR) hit = false;
                    }
                    
                    if (hit) timeElapsed -= (timeElapsed < -2) ?  0 : 1;
                }
                
                shoot = true;
                eng.shootRight = 2;
            }
            
            if (eng.shootDown == 1 && score >= 2)
            {
                if (enemyX == playerX && enemyY < playerY + 5)
                {
                    for (int y = playerY; y != enemyY; y++)
                    {
                        if (map[playerX + y * MAP_W] != FLOOR) hit = false;
                    }
                    
                    if (hit) timeElapsed -= (timeElapsed < -2) ?  0 : 1;
                }
                
                shoot = true;
                eng.shootDown = 2;
            }
            
            if (eng.shootLeft == 1 && score >= 2)
            {
                if (enemyY == playerY && enemyX < playerX + 9)
                {
                    for (int x = playerX; x != enemyX; x--)
                    {
                        if (map[x + playerY * MAP_W] != FLOOR) hit = false;
                    }
                    
                    if (hit) timeElapsed -= (timeElapsed < -2) ?  0 : 1;
                }
                
                shoot = true;
                eng.shootLeft = 2;
            }
            
            // deduct the score regardless
            if (shoot) score -= 2;
            
            // if the enemy move delay is over, move towards the current target
            if (timeElapsed >= enemySpeed)
            {
                if (enemyY > targetY)
                        enemyY--;
                if (enemyX < targetX)
                      enemyX++;
                if (enemyY < targetY)
                      enemyY++;
                if (enemyX > targetX)
                      enemyX--;
                      
                // delay movement again
                timeElapsed -= enemySpeed;
                
                // if not hunting & reached exit, teleport nearby the player
                if (enemyX == targetX && enemyY == targetY && !hunt)
                {
                    // increase hunt period and start hunting
                    targetTimer = ghostTimer++;
                    hunt = true;
                    pass = false;
                    
                    // teleport nearby player
                    do
                    {
                        enemyX = (int) (1 + Math.random() * (MAP_W - 1));
                        enemyY = (int) (1 + Math.random() * (MAP_H - 1));
                        
                        if (map[enemyX + enemyY * MAP_W] == FLOOR && ((enemyX >= playerX + 10 && enemyX < playerX + 15) || (enemyX < playerX - 10 && enemyX >= playerX - 15)) && ((enemyY >= playerY + 10 && enemyY < playerY + 15) || (enemyY < playerY - 10 && enemyY >= playerY - 15))) pass = true;
                    }
                    while (!pass);
                }
            }
            
            // game over states
            
            // get bonus 50% points if exit without being caught
            // Author: Afirudin
            if (playerX == GOAL_X && playerY == GOAL_Y)
            {
                running = false;
                score += Math.floor(score / 2);
            }
            
            // get caught by enemy, points deducted
            if (enemyX == playerX && enemyY == playerY)
            {
                running = false;
                score -= Math.floor(score / 2);
            }
            
            // if player touch the coin, increase score and
            // teleport it somewhere else
            // Author: Afirudin
            if (coinX == playerX && coinY == playerY)
            {
                score += 10;
                
                // increase enemy speed to make it harder
                enemySpeed -= 0.05;
                
                // limit enemy speed to 4 tiles/second
                if (enemySpeed < 0.25) enemySpeed = 0.25;
                
                // teleport coin to random legal spot
                pass = false;
                do
                {
                    coinX = (int) (1 + Math.random() * (MAP_W - 1));
                    coinY = (int) (1 + Math.random() * (MAP_H - 1));
            
                    if (map[coinX + coinY * MAP_W] == FLOOR && (coinX >= playerX + MAP_W / 4 || coinX < playerX + MAP_W / 4) && (coinY >= playerY + MAP_H / 4 || coinY < playerY - MAP_H / 4) && (coinX != GOAL_X && coinY != GOAL_Y))
                        pass = true;
                }
                while (!pass);
            }
            
            // start modifying the screen string.
            // written in html to allow newlines in
            // JLabel
            screen = "<html>Score: " + score + "G<br/>";
            
            // when not shooting. same code as before
            if (!shoot)
            {
                // we limit the camera range of the player
                // height = 9 tiles, width = 17 tiles
                for (int y = playerY - 4; y != playerY + 5; y++)
                {
                    for (int x = playerX - 8; x != playerX + 9; x++)
                    {
                        int i = x + y * MAP_W;
                        
                        // display the map tiles in range of camera
                        if (i >= 0 && i < map.length && x >= 0 && x < MAP_W && y >= 0 && y < MAP_H)
                        {
                            if (i == (playerX + playerY * MAP_W)) screen += PLAYER;
                            else if (i == (enemyX + enemyY * MAP_W)) screen += ENEMY;
                            else if (i == (coinX + coinY * MAP_W)) screen += COIN;
                            else if (i == (START_X + START_Y * MAP_W)) screen += START;
                            else if (i == (GOAL_X + GOAL_Y * MAP_W)) screen += GOAL;
                            else screen += map[i];
                        }
                        
                        // in case of out of bounds
                        else screen += VOID;
                    }
                    
                    // line break in html
                    screen += "<br/>";
                }
            }
            
            // render shooting code. same as above with slight change
            else       
            {
                for (int y = playerY - 4; y != playerY + 5; y++)
                {
                    for (int x = playerX - 8; x != playerX + 9; x++)
                    {
                        int i = x + y * MAP_W;
                        if (i >= 0 && i < map.length && x >= 0 && x < MAP_W && y >= 0 && y < MAP_H)
                        {
                            // only difference are prioritizing the bullets over the other map tiles
                            if (i == (playerX + playerY * MAP_W)) screen += PLAYER;
                            else if (i == (enemyX + enemyY * MAP_W)) screen += ENEMY;
                            else if (eng.shootUp == 2 && x == playerX && y < playerY) screen += BULLET_V;
                            else if (eng.shootRight == 2 && y == playerY && x > playerX) screen += BULLET_H;
                            else if (eng.shootDown == 2 && x == playerX && y > playerY) screen += BULLET_V;
                            else if (eng.shootLeft == 2 && y == playerY && x < playerX) screen += BULLET_H;
                            else if (i == (coinX + coinY * MAP_W)) screen += COIN;
                            else if (i == (START_X + START_Y * MAP_W)) screen += START;
                            else if (i == (GOAL_X + GOAL_Y * MAP_W)) screen += GOAL;
                            else screen += map[i];
                        }
                        else screen += VOID;
                    }

                    screen += "<br/>";
                }
            }
  
            screen += "</html>";
            
            // set screen string as updated display
            eng.txt.setText(screen);
            
            // pause game for 0.1 seconds to display the bullet animation
            if (shoot)
            {
                // handle the exceptions
                try
                {
                    Thread.sleep(100);
                }
                catch (InterruptedException e)
                {
                    System.out.println("Something wrong happened...");
                    e.printStackTrace();
                    System.exit(-1);
                }
                
                shoot = false;
            }
        }
        
        // edit the scores at the end of the game
        // handle exceptions
        try
        {
            new Scores().editScores(score);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        //display end screen
        screen  = "<html><br/><br/><br/>";
        screen += "GAME OVER<br/>";
        screen += "Your Score: " + score + "G<br/><br/>";
        screen += "ENTER = Back";
        
        // wait until user press the select key
        while (eng.select != 1)
        {
            eng.txt.setText(screen);
        }
        
        eng.select = 2;
        
        // back to main menu
        return;
    }
}