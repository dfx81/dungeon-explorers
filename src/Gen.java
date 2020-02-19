// Gen.java - Author: Danial
// Randomly generate a dungeon layout using
// BSP Tree structure

// requires dynamically sized array
import java.util.ArrayList;

class Gen
{
    // define the map and room size
    final int MAP_S = 32;
    final int MAX_S = 8;
    
    // generate the map. returns a char array
    char[] generate()
    {
        // defines specific char to specific tiles
        final char WALL = (char) 0x2588;
        final char BROKEN_1 = (char) 0x2593;
        final char BROKEN_2 = (char) 0x2592;
        final char FLOOR = (char) 0x2591;
        final char VOID = ' ';
        
        // create the map array
        char[] map = new char[MAP_S * MAP_S];
        
        // start with a single room that fill the whole dungeon
        Room r = new Room();
        r.x = 0;
        r.y = 0;
        r.w = MAP_S - 1;
        r.h = MAP_S - 1;
        
        //System.out.print("\r21%"); <-- debugging
        
        // start dividing
        r.divide();
        
        // repeat for each child, add the created rooms
        // to the doors arraylist.
        // this could be rewritten as a recursive function
        // but I don't want it to randomly crash due to
        // stack overflow
        
        // if the room cannot be divided, add it into the
        // rooms arraylist
        for (Room i1 : r.child)
        {
            if (i1.w > MAX_S || i1.h > MAX_S)
            {
                //System.out.print("\r22%"); <-- debugging
                i1.divide();
                r.doors.add(i1);
                for (Room i2 : i1.child)
                {
                    if (i2.w > MAX_S || i2.h > MAX_S)
                    {
                        //System.out.print("\r23%"); <-- debugging
                        i2.divide();
                        r.doors.add(i2);
                        for (Room i3 : i2.child)
                        {
                            if (i3.w > MAX_S || i3.h > MAX_S)
                            {
                                //System.out.print("\r24%"); <-- debugging
                                i3.divide();
                                r.doors.add(i3);
                                for (Room i4 : i3.child)
                                {
                                    if (i4.w > MAX_S || i4.h > MAX_S)
                                    {
                                        //System.out.print("\r25%"); <-- debugging
                                        i4.divide();
                                        r.doors.add(i4);
                                        for (Room i5 : i4.child)
                                        {
                                            if (i5.w > MAX_S || i5.h > MAX_S)
                                            {
                                                //System.out.print("\r26%"); <-- debugging
                                                i5.divide();
                                                r.doors.add(i5);
                                                for (Room c : i5.child)
                                                {
                                                    r.rooms.add(c);
                                                    r.doors.add(c);
                                                }
                                            }
                    
                                            else
                                            {
                                                r.rooms.add(i5);
                                                r.doors.add(i5);
                                            }
                                        }
                                    }
                                    
                                    else
                                    {
                                        r.rooms.add(i4);
                                        r.doors.add(i4);
                                    }
                                }
                            }
                    
                            else
                            {
                                r.rooms.add(i3);
                                r.doors.add(i3);
                            }
                        }
                    }
                    
                    else
                    {
                        r.rooms.add(i2);
                        r.doors.add(i2);
                    }
                }
            }
            
            else
            {
                r.rooms.add(i1);
                r.doors.add(i1);
            }
        }
        
        // debugging
        //System.out.println(r.doors.size());
        //System.out.print("\r27%");
        
        // start populating the map with tiles
        for (int y = 0; y != MAP_S; y++)
        {
            for (int x = 0; x != MAP_S; x++)
            {
                // keep track if we want to build a wall or not
                boolean wall = false;
                
                // iterate through all rooms and check for walls
                // we check either we're on a room's boundaries
                for (int c = 0; c != r.rooms.size(); c++)
                {
                    if ((x == r.rooms.get(c).x || x == r.rooms.get(c).x + r.rooms.get(c).w) && y >= r.rooms.get(c).y && y <= r.rooms.get(c).y + r.rooms.get(c).h)
                    {
                        wall = true;
                    }
                    
                    if ((y == r.rooms.get(c).y || y == r.rooms.get(c).y + r.rooms.get(c).h) && x >= r.rooms.get(c).x && x <= r.rooms.get(c).x + r.rooms.get(c).w)
                    {
                        wall = true;
                    }
                }
                
                // generate a random wall type tile to be put on that spot
                // but only if it's not the map boundaries
                double gen = 0;
                if (x != 0 && x != MAP_S - 1 && y != 0 && y != MAP_S - 1) gen = Math.random();
                
                if (wall && gen >= 0 && gen < 0.95) map[x + y * MAP_S] = WALL; // build a basic wall
                else if (wall && gen >= 0.95 && gen < 0.985) map[x + y * MAP_S] = BROKEN_1; // build slightly broken wall
                else if (wall && gen >= 0.985) map[x + y * MAP_S] = BROKEN_2; // build heavily broken wall
                else map[x + y * MAP_S] = FLOOR; // build a floor if the wall boolean is false
            }
        }
        
        //System.out.print("\r28%"); <-- debugging
        
        // Same as above, but now we're any wall tiles with same
        // coordinate as a door with floor
        for (int y = 0; y != MAP_S; y++)
        {
            for (int x = 0; x != MAP_S; x++)
            {
                // keeps track if we want to build a door or not
                // basically a flag
                boolean door = false;
                
                for (int c = 0; c != r.doors.size(); c++)
                {
                    // check for door
                    if (x == r.doors.get(c).doorx && y == r.doors.get(c).doory)
                    {
                        door = true;
                        break;
                    }
                }
                
                // build the door if found
                if (door) map[x + y * MAP_S] = FLOOR;
            }
        }
        
        //System.out.print("\r30%"); <-- debugging
        
        // return the map created to be used
        return map;
    }
}