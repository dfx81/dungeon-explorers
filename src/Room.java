// Room.java - Author : Danial
// Defines a dungeon room and divide a single dungeon room into
// subrooms using a BSP Tree structure

// use ArrayList for dynamic arrays since the number of rooms
// created varies
import java.util.ArrayList;

class Room
{
    // define some limitation of room and map size
    final int MAP_S = 32;
    final int MAX_S = 8;
    final int MIN_S = 5;
    
    // define position (x, y) and size (w, h) of the room
    int x;
    int y;
    int w;
    int h;
    
    // define door coordinate
    int doorx;
    int doory;
    
    // each room can have up to two subrooms
    Room[] child = new Room[2];
    
    // rooms track the smallest possible subroom in the tree
    // doors get every room for their door coordinates
    ArrayList<Room> doors = new ArrayList<Room>(0);
    ArrayList<Room> rooms = new ArrayList<Room>(0);
    
    // the divide method will divide the room into 2 subrooms or children
    void divide()
    {
        // check if it's possile to divide
        boolean canx = (w > MAX_S) ? true : false;
        boolean cany = (h > MAX_S) ? true : false;
        
        // repeat the divide if the subrooms created smaller than the MIN_S
        do
        {
            // if both x and y possible to divide, pick randomly
            if (canx && cany)
            {
                // divide x
                if (Math.random() > 0.5)
                {
                    // pick a spot to build a strip of wall based on formula:
                    //     (int)((RAND * (MAX - MIN)) + MIN)
                    int slice = (int)((Math.random() * ((x + w - MIN_S) - (x + (MAX_S - 2)))) + x + (MAX_S - 2));
                    
                    // initialize 2 new room and assign the new coordinates and size created.
                    // also randomly pick a spot to create door
                    child[0] = new Room();
                    child[0].x = x;
                    child[0].y = y;
                    child[0].w = slice - x;
                    child[0].h = h;
                    child[0].doorx = child[0].x + child[0].w;
                    child[0].doory = (int)((Math.random() * ((y + h) - (y + 1))) + y + 1);
                    
                    child[1] = new Room();
                    child[1].x = slice;
                    child[1].y = y;
                    child[1].w = x + w - slice;
                    child[1].h = h;
                    child[1].doorx = child[1].x;
                    child[1].doory = (int)((Math.random() * ((y + h) - (y + 1))) + y + 1);
                }
                
                // same as above but for y coordinate
                else
                {
                    int slice = (int)((Math.random() * ((y + h - MIN_S) - (y + (MAX_S - 2)))) + y + (MAX_S - 2));
                    
                    child[0] = new Room();
                    child[0].x = x;
                    child[0].y = y;
                    child[0].w = w;
                    child[0].h = slice - y;
                    child[0].doorx = (int)((Math.random() * ((x + w) - (x + 1))) + x + 1);
                    child[0].doory = child[0].y + child[0].h;
                    
                    child[1] = new Room();
                    child[1].x = x;
                    child[1].y = slice;
                    child[1].w = w;
                    child[1].h = y + h - slice;
                    child[1].doorx = (int)((Math.random() * ((x + w) - (x + 1))) + x + 1);
                    child[1].doory = child[1].y;
                }
            }
            
            // also the same but run if only x is possible to divide
            else if (canx)
            {
                int slice = (int)((Math.random() * ((x + w - MIN_S) - (x + (MAX_S - 2)))) + x + (MAX_S - 2));
                
                child[0] = new Room();
                child[0].x = x;
                child[0].y = y;
                child[0].w = slice - x;
                child[0].h = h;
                child[0].doorx = child[0].x + child[0].w;
                child[0].doory = (int)((Math.random() * ((y + h) - (y + 1))) + y + 1);
                
                child[1] = new Room();
                child[1].x = slice;
                child[1].y = y;
                child[1].w = x + w - slice;
                child[1].h = h;
                child[1].doorx = child[1].x;
                child[1].doory = (int)((Math.random() * ((y + h) - (y + 1))) + y + 1);
            }
            
            // same as above but for y
            else if (cany)
            {
                int slice = (int)((Math.random() * ((y + h - MIN_S) - (y + (MAX_S - 2)))) + y + (MAX_S - 2));
                
                child[0] = new Room();
                child[0].x = x;
                child[0].y = y;
                child[0].w = w;
                child[0].h = slice - y;
                child[0].doorx = (int)((Math.random() * ((x + w) - (x + 1))) + x + 1);
                child[0].doory = child[0].y + child[0].h;
                 
                child[1] = new Room();
                child[1].x = x;
                child[1].y = slice;
                child[1].w = w;
                child[1].h = y + h - slice;
                child[1].doorx = (int)((Math.random() * ((x + w) - (x + 1))) + x + 1);
                child[1].doory = child[1].y;
            }
            
            // repeat until legal room size created
        } while (child[0].w < MIN_S && child[1].w < MIN_S && child[0].h < MIN_S && child[1].h < MIN_S);
    }
}