// Scores.java - Author: Afiruddin
// Handle game scores
// Modified by: Danial

// requires File to create a reference to a single file,
// Scanner to read from File, PrintWriter to
// Write to File, and JLabel to update display
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import javax.swing.JLabel;
//import java.util.Arrays; <-- for easier sort

class Scores
{
    // only store top 5 scores
    int[] score = new int[6];
    
    // display scores on screen
    void showScores(Engine eng) throws Exception // don't handle exceptions
    {
        // create new File object that refers to Save.scores
        File scores = new File("Save.scores");
        
        // Scanner reads from File instead of System.in
        Scanner in = new Scanner(scores);
        
        for (int i = 0; i != 5; i++)
        {
            score[i] = in.nextInt(); // store scores in array
        }
        
        in.close(); // stop reading
        
        // 
        String screen = "<html>Scores<br/><br/>";
        
        for (int i = 0; i != 5; i++)
            screen += score[i] + "G<br/>";
        
        screen += "<br/>ENTER = Back</html>";
        
        while (eng.select != 1)
        {
            eng.txt.setText(screen);
        }
        
        eng.select = 2;
        
        return;
    }
    
    void editScores(int newScore) throws Exception
    {
        File scores = new File("Save.scores"); // read from the Save.scores file
        Scanner in = new Scanner(scores); // read from file
        
        
        for (int i = 0; i != 5; i++)
        {
            score[i] = in.nextInt(); // save score from aaray
        }
        
        in.close(); // stop reading
         
        PrintWriter out = new PrintWriter(scores);
        score[5] = newScore;
        
        while (score[0] > score[1] || score[1] > score[2] || score[2] > score[3] || score[3] > score[4] || score[4] > score[5] || score[5] < score[0]) // make sure it will be sorted correctly
        {
            for (int i = 0; i < score.length; i++)
            {
                if (i != 5)
                    if (score[i] > score[i + 1])
                    {
                        int temp = score[i + 1]; // temporary variable 
                        score [i + 1] = score [i]; // new score will be updated
                        score [i] = temp; // will swap the old score from the previous one
                    }   
            }
        }
        
        for (int i = score.length - 1; i != 0; i--)
        {
            out.println(score[i]);
            //System.out.println(score[i]); <-- debugging
        }
        
        out.flush();
        out.close();
    }
}
                